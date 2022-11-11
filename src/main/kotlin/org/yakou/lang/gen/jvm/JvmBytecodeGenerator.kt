package org.yakou.lang.gen.jvm

import org.objectweb.asm.*
import org.objectweb.asm.Type
import org.objectweb.asm.signature.SignatureVisitor
import org.objectweb.asm.signature.SignatureWriter
import org.yakou.lang.ast.*
import org.yakou.lang.bind.*
import org.yakou.lang.compilation.CompilationSession
import org.yakou.lang.compilation.CompilationUnit
import java.io.File

class JvmBytecodeGenerator(private val compilationSession: CompilationSession) {
    private val table: Table = compilationSession.table

    private val classWriters: HashMap<TypeInfo.Class, ClassWriter> = hashMapOf()
    private val primaryConstructorWriters: HashMap<TypeInfo.Class, MethodVisitor> = hashMapOf()
    private val staticBlockWriters: HashMap<TypeInfo.Class, MethodVisitor> = hashMapOf()

    private var currentLineLabel: Label? = null

    fun gen(compilationUnit: CompilationUnit) {
        genYkFile(compilationUnit.ykFile!!)
    }

    fun finalize() {
        for (methodWriter in staticBlockWriters.values) {
            methodWriter.visitInsn(Opcodes.RETURN)
            methodWriter.visitMaxs(-1, -1)
            methodWriter.visitEnd()
        }

        for (methodWriter in primaryConstructorWriters.values) {
            methodWriter.visitInsn(Opcodes.RETURN)
            methodWriter.visitMaxs(-1, -1)
            methodWriter.visitEnd()
        }

        // Delete output folder first
        compilationSession.preference.outputFolder.walkBottomUp().forEach(File::delete)

        for ((classTypeInfo, classWriter) in classWriters) {
            val bytecode = classWriter.toByteArray()
            val classFile = File(
                compilationSession.preference.outputFolder,
                "${classTypeInfo.standardTypePath.replace("::", "/")}.class"
            )

            if (!classFile.parentFile.exists()) {
                classFile.parentFile.mkdirs()
            }

            if (!classFile.exists()) {
                classFile.createNewFile()
            }

            classFile.writeBytes(bytecode)
        }
    }

    private fun genYkFile(ykFile: YkFile) {
        for (item in ykFile.items)
            genItem(item)
    }

    private fun genItem(item: Item) {
        when (item) {
            is Package -> {
                if (item.items != null) {
                    for (innerItem in item.items)
                        genItem(innerItem)
                }
            }

            is Const -> genConst(item)
            is StaticField -> genStaticField(item)
            is Class -> genClass(item)
            is Func -> genFunction(item)
            is Impl -> genImpl(item)
        }
    }

    private fun genConst(const: Const) {
        val classWriter = getClassWriter(const.fieldInstance.ownerTypeInfo)
        val expression = const.expression

        // Declare static field based on value
        if (expression is Expression.LiteralExpression) {
            // Value is inlinable, thus we use ConstantValue attribute in this case
            classWriter.visitField(
                const.fieldInstance.access,
                const.fieldInstance.name,
                const.fieldInstance.descriptor,
                null,
                when (expression) {
                    is Expression.BoolLiteral -> expression.value
                    is Expression.NumberLiteral -> castNumber(
                        expression.value,
                        (expression.finalType as TypeInfo.Primitive).type
                    )
                }
            ).visitEnd()
        }
    }

    private fun genStaticField(staticField: StaticField) {
        val classWriter = getClassWriter(staticField.fieldInstance.ownerTypeInfo)
        val expression = staticField.expression

        if (!staticField.fieldInstance.mutable && expression is Expression.LiteralExpression) {
            // Value is inlinable, thus we use ConstantValue attribute in this case
            classWriter.visitField(
                staticField.fieldInstance.access,
                staticField.fieldInstance.name,
                staticField.fieldInstance.descriptor,
                null,
                when (expression) {
                    is Expression.BoolLiteral -> expression.value
                    is Expression.NumberLiteral -> castNumber(
                        expression.value,
                        (expression.finalType as TypeInfo.Primitive).type
                    )
                }
            ).visitEnd()
            return
        }

        val fieldVisitor = classWriter.visitField(
            staticField.fieldInstance.access,
            staticField.fieldInstance.name,
            staticField.fieldInstance.descriptor,
            null,
            null
        )

        fieldVisitor.visitEnd()

        // Assign value to constant field in <clinit> method
        val methodVisitor = getStaticBlockWriter(staticField.fieldInstance.ownerTypeInfo)

        genExpression(methodVisitor, staticField.expression)
        methodVisitor.visitFieldInsn(
            Opcodes.PUTSTATIC,
            staticField.fieldInstance.ownerTypeInfo.internalName,
            staticField.fieldInstance.name,
            staticField.fieldInstance.descriptor
        )
    }

    private fun genClass(clazz: Class) {
        getClassWriter(clazz.classTypeInfo)

        if (clazz.primaryConstructor != null) {
            genPrimaryConstructor(clazz.primaryConstructor, clazz.superClassConstructorCall)
        } else {
            val methodVisitor = getPrimaryConstructorWriter(clazz.classTypeInfo)

            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                TypeInfo.Class.OBJECT_TYPE_INFO.internalName,
                "<init>",
                "()V",
                false
            )
        }

        if (clazz.classItems != null) {
            for (classItem in clazz.classItems)
                genClassItem(classItem)
        }
    }

    private fun genPrimaryConstructor(
        primaryConstructor: PrimaryConstructor,
        superClassConstructorCall: Class.SuperClassConstructorCall?
    ) {
        val classWriter = getClassWriter(primaryConstructor.constructorInstance.ownerTypeInfo)
        val methodVisitor = classWriter.visitMethod(
            primaryConstructor.constructorInstance.access,
            "<init>",
            primaryConstructor.constructorInstance.descriptor,
            genGenericFunctionSignature(
                listOf(),
                primaryConstructor.constructorInstance.parameterTypeInfos,
                TypeInfo.Primitive.UNIT_TYPE_INFO
            ),
            null
        )

        methodVisitor.visitCode()

        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)

        if (superClassConstructorCall != null) {
            for (argument in superClassConstructorCall.arguments) {
                genExpression(methodVisitor, argument)
            }
        }

        methodVisitor.visitMethodInsn(
            Opcodes.INVOKESPECIAL,
            superClassConstructorCall?.superClassTypeInfo?.internalName ?: TypeInfo.Class.OBJECT_TYPE_INFO.internalName,
            "<init>",
            superClassConstructorCall?.constructorInstance?.descriptor ?: "()V",
            false
        )

        for ((i, parameter) in primaryConstructor.parameters.withIndex()) {
            if (!parameter.modifiers.isEmpty()) {
                genConstructorParameter(classWriter, methodVisitor, parameter, i + 1)
            }
        }

        primaryConstructorWriters[primaryConstructor.constructorInstance.ownerTypeInfo] = methodVisitor
    }

    private fun genConstructorParameter(
        classWriter: ClassWriter,
        methodVisitor: MethodVisitor,
        constructorParameter: PrimaryConstructor.ConstructorParameter,
        offset: Int
    ) {
        classWriter.visitField(
            constructorParameter.fieldInstance!!.access,
            constructorParameter.fieldInstance!!.name,
            constructorParameter.fieldInstance!!.descriptor,
            genGenericFieldSignature(constructorParameter.typeInfo),
            null
        ).visitEnd()

        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
        methodVisitor.visitVarInsn(constructorParameter.fieldInstance!!.typeInfo.loadOpcode, offset)
        methodVisitor.visitFieldInsn(
            Opcodes.PUTFIELD,
            constructorParameter.fieldInstance!!.ownerTypeInfo.internalName,
            constructorParameter.fieldInstance!!.name,
            constructorParameter.fieldInstance!!.descriptor
        )
    }

    private fun genClassItem(classItem: ClassItem) {
        when (classItem) {
            is Field -> genField(classItem)
        }
    }

    private fun genField(field: Field) {
        val classWriter = getClassWriter(field.fieldInstance.ownerTypeInfo)

        classWriter.visitField(
            field.fieldInstance.access,
            field.fieldInstance.name,
            field.fieldInstance.descriptor,
            genGenericFieldSignature(field.typeInfo),
            null
        ).visitEnd()

        if (field.expression != null) {
            val methodVisitor = getPrimaryConstructorWriter(field.fieldInstance.ownerTypeInfo)

            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)

            genExpression(methodVisitor, field.expression!!)

            methodVisitor.visitFieldInsn(
                Opcodes.PUTFIELD,
                field.fieldInstance.ownerTypeInfo.internalName,
                field.fieldInstance.name,
                field.fieldInstance.descriptor
            )
        }
    }

    private fun genFunction(function: Func) {
        val classWriter = getClassWriter(function.functionInstance.ownerTypeInfo)
        val methodVisitor = classWriter.visitMethod(
            function.functionInstance.access,
            function.functionInstance.name,
            function.functionInstance.descriptor,
            genGenericFunctionSignature(
                function.functionInstance.genericParameters,
                function.functionInstance.parameterTypeInfos,
                function.functionInstance.returnTypeInfo
            ),
            null
        )

        methodVisitor.visitCode()

        if (function.body != null) {
            genFunctionBody(methodVisitor, function.body)
        }

        // Attempt to automatically generate return statement if:
        // 1. Last statement is not return statement
        // 2. Function returns unit
        if (function.functionInstance.returnTypeInfo == TypeInfo.Primitive.UNIT_TYPE_INFO &&
            function.body is FunctionBody.BlockExpression &&
            function.body.statements.lastOrNull() !is Return
        ) {
            methodVisitor.visitInsn(Opcodes.RETURN)
        }

        methodVisitor.visitMaxs(-1, -1)
        methodVisitor.visitEnd()
    }

    private fun genImpl(impl: Impl) {
        if (impl.implItems != null) {
            for (item in impl.implItems) {
                genImplItem(item)
            }
        }
    }

    private fun genImplItem(item: ImplItem) {
        when (item) {
            is Class -> genClass(item)
            is Func -> genFunction(item)
            is Impl -> genImpl(item)
            is StaticField -> genStaticField(item)
            is Const -> genConst(item)
        }
    }

    private fun genFunctionBody(methodVisitor: MethodVisitor, functionBody: FunctionBody) {
        when (functionBody) {
            is FunctionBody.BlockExpression -> {
                for (statement in functionBody.statements) {
                    genStatement(methodVisitor, statement)
                }
            }

            is FunctionBody.SingleExpression -> {
                genExpression(methodVisitor, functionBody.expression)

                methodVisitor.visitInsn(functionBody.expression.finalType.returnOpcode)
            }
        }
    }

    private fun genStatement(methodVisitor: MethodVisitor, statement: Statement) {
        currentLineLabel = Label()

        methodVisitor.visitLineNumber(statement.span.startPosition.line, currentLineLabel)

        when (statement) {
            is VariableDeclaration -> genVariableDeclaration(methodVisitor, statement)
            is Return -> genReturn(methodVisitor, statement)
            is For -> genFor(methodVisitor, statement)
            is Block -> genBlock(methodVisitor, statement)
            is ExpressionStatement -> genExpression(methodVisitor, statement.expression)
        }
    }

    private fun genVariableDeclaration(
        methodVisitor: MethodVisitor,
        variableDeclaration: VariableDeclaration
    ) {
        if (variableDeclaration.variableInstance.propagatable && variableDeclaration.variableInstance.referencedCount == 0) {
            return
        }

        genExpression(methodVisitor, variableDeclaration.expression)

        if (!variableDeclaration.ignore) {
            methodVisitor.visitVarInsn(
                variableDeclaration.expression.finalType.storeOpcode,
                variableDeclaration.variableInstance.index
            )
        } else {
            // Discard return value if need
            when (val type = variableDeclaration.expression.finalType) {
                is TypeInfo.Primitive -> {
                    if (type.type != PrimitiveType.Unit) {
                        when (type.type) {
                            PrimitiveType.I64, PrimitiveType.F64 -> {
                                methodVisitor.visitInsn(Opcodes.POP2)
                            }

                            else -> {
                                methodVisitor.visitInsn(Opcodes.POP)
                            }
                        }
                    }
                }

                else -> {
                    methodVisitor.visitInsn(Opcodes.POP)
                }
            }
        }
    }

    private fun genFor(methodVisitor: MethodVisitor, `for`: For) {
        val startLabel = Label()
        val endLabel = Label()

        methodVisitor.visitLabel(startLabel)

        if (`for`.conditionExpression !is Expression.Empty) {
            genExpression(methodVisitor, `for`.conditionExpression)
        } else {
            methodVisitor.visitLdcInsn(1)
        }

        methodVisitor.visitJumpInsn(Opcodes.IFEQ, endLabel)

        genBlock(methodVisitor, `for`.block)

        methodVisitor.visitJumpInsn(Opcodes.GOTO, startLabel)
        methodVisitor.visitLabel(endLabel)
    }

    private fun genBlock(methodVisitor: MethodVisitor, block: Block) {
        for (statement in block.statements) {
            genStatement(methodVisitor, statement)
        }
    }

    private fun genReturn(methodVisitor: MethodVisitor, `return`: Return) {
        genExpression(methodVisitor, `return`.expression)

        methodVisitor.visitInsn(`return`.expression.finalType.returnOpcode)
    }

    private fun genExpression(methodVisitor: MethodVisitor, expression: Expression) {
        when (expression) {
            is Expression.BinaryExpression -> genBinaryExpression(methodVisitor, expression)
            is Expression.Identifier -> genIdentifier(methodVisitor, expression)
            is Expression.As -> genAs(methodVisitor, expression)
            is Expression.Parenthesized -> genExpression(methodVisitor, expression.expression)
            is Expression.BoolLiteral -> genBoolLiteral(methodVisitor, expression)
            is Expression.NumberLiteral -> genNumberLiteral(methodVisitor, expression)
            is Expression.Empty -> {}
            Expression.Undefined -> TODO("UNREACHABLE")
        }
    }

    private fun genBinaryExpression(methodVisitor: MethodVisitor, binaryExpression: Expression.BinaryExpression) {
        val leftType = binaryExpression.leftExpression.finalType
        val rightType = binaryExpression.rightExpression.finalType
        val finalType = binaryExpression.finalType

        when (binaryExpression.operation) {
            Expression.BinaryExpression.BinaryOperation.Addition,
            Expression.BinaryExpression.BinaryOperation.Subtraction,
            Expression.BinaryExpression.BinaryOperation.Multiplication,
            Expression.BinaryExpression.BinaryOperation.Division,
            Expression.BinaryExpression.BinaryOperation.Modulo,
            Expression.BinaryExpression.BinaryOperation.UnsignedRightShift,
            Expression.BinaryExpression.BinaryOperation.RightShift,
            Expression.BinaryExpression.BinaryOperation.LeftShift -> {
                genExpression(methodVisitor, binaryExpression.leftExpression)
                genExpression(methodVisitor, binaryExpression.rightExpression)

                if (finalType is TypeInfo.Primitive) {
                    methodVisitor.visitInsn(binaryExpression.operation.getFunctorOpcode(finalType))
                } else {
                    throw IllegalStateException("Unable to emit `${binaryExpression.operation}` opcode for non-primitive type.")
                }
            }

            Expression.BinaryExpression.BinaryOperation.LogicalAnd -> {
                val falseLabel = Label()
                val endLabel = Label()

                genExpression(methodVisitor, binaryExpression.leftExpression)

                methodVisitor.visitJumpInsn(Opcodes.IFEQ, falseLabel)

                genExpression(methodVisitor, binaryExpression.rightExpression)

                methodVisitor.visitJumpInsn(Opcodes.IFEQ, falseLabel)
                methodVisitor.visitInsn(Opcodes.ICONST_1)
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
                methodVisitor.visitLabel(falseLabel)
                methodVisitor.visitInsn(Opcodes.ICONST_0)
                methodVisitor.visitLabel(endLabel)
            }

            Expression.BinaryExpression.BinaryOperation.LogicalOr -> {
                val trueLabel = Label()
                val endLabel = Label()

                genExpression(methodVisitor, binaryExpression.leftExpression)

                methodVisitor.visitJumpInsn(Opcodes.IFNE, trueLabel)

                genExpression(methodVisitor, binaryExpression.rightExpression)

                methodVisitor.visitJumpInsn(Opcodes.IFNE, trueLabel)
                methodVisitor.visitInsn(Opcodes.ICONST_0)
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
                methodVisitor.visitLabel(trueLabel)
                methodVisitor.visitInsn(Opcodes.ICONST_1)
                methodVisitor.visitLabel(endLabel)
            }

            Expression.BinaryExpression.BinaryOperation.Equal -> {
                genExpression(methodVisitor, binaryExpression.leftExpression)
                genExpression(methodVisitor, binaryExpression.rightExpression)

                genStructuralEqualityCheck(methodVisitor, leftType, rightType, false)
            }

            Expression.BinaryExpression.BinaryOperation.NotEqual -> {
                genExpression(methodVisitor, binaryExpression.leftExpression)
                genExpression(methodVisitor, binaryExpression.rightExpression)

                genStructuralEqualityCheck(methodVisitor, leftType, rightType, true)
            }

            Expression.BinaryExpression.BinaryOperation.ExactEqual -> {
                genExpression(methodVisitor, binaryExpression.leftExpression)
                genExpression(methodVisitor, binaryExpression.rightExpression)

                genReferentialEquality(methodVisitor, leftType, rightType, false)
            }

            Expression.BinaryExpression.BinaryOperation.ExactNotEqual -> {
                genExpression(methodVisitor, binaryExpression.leftExpression)
                genExpression(methodVisitor, binaryExpression.rightExpression)

                genReferentialEquality(methodVisitor, leftType, rightType, true)
            }

            Expression.BinaryExpression.BinaryOperation.Greater,
            Expression.BinaryExpression.BinaryOperation.GreaterEqual,
            Expression.BinaryExpression.BinaryOperation.Lesser,
            Expression.BinaryExpression.BinaryOperation.LesserEqual -> {
                genExpression(methodVisitor, binaryExpression.leftExpression)
                genExpression(methodVisitor, binaryExpression.rightExpression)

                val leftPrimitiveType = leftType as TypeInfo.Primitive

                if (leftPrimitiveType.type.isIntType()) {
                    genIntegerComparison(methodVisitor, binaryExpression.operation.getFunctorOpcode(leftPrimitiveType))
                } else {
                    genComparison(methodVisitor, binaryExpression.operation, binaryExpression.operation.getFunctorOpcode(leftPrimitiveType))
                }
            }
        }
    }

    private fun genIdentifier(methodVisitor: MethodVisitor, identifier: Expression.Identifier) {
        when (val symbolInstance = identifier.symbolInstance) {
            is Variable -> {
                methodVisitor.visitVarInsn(symbolInstance.typeInfo.loadOpcode, symbolInstance.index)
            }

            is ClassMember.Field -> {
                if (symbolInstance.static) {
                    methodVisitor.visitFieldInsn(
                        Opcodes.GETSTATIC,
                        symbolInstance.ownerTypeInfo.internalName,
                        symbolInstance.name,
                        symbolInstance.descriptor
                    )
                } else {
                    methodVisitor.visitFieldInsn(
                        Opcodes.GETFIELD,
                        symbolInstance.ownerTypeInfo.internalName,
                        symbolInstance.name,
                        symbolInstance.descriptor
                    )
                }
            }

            else -> TODO("UNREACHABLE")
        }
    }

    private fun genStructuralEqualityCheck(
        methodVisitor: MethodVisitor,
        leftType: TypeInfo,
        rightType: TypeInfo,
        invert: Boolean
    ) {
        // lhs -> i1
        // rhs -> i2
        // stack: - i1 - i2

        when {
            leftType is TypeInfo.Class && rightType is TypeInfo.Class -> {
                // Structural Equality Check Strategy:
                // ```kt
                // o1?.equals(o2) ?: (o2 === null)
                // ```
                // or
                // ```java
                // o1 != null ? o1.equals(o2) : o2 == null;
                // ```

                val eqMethod = table.findImplementedEqualMethod(leftType)

                val nullLabel1 = Label()
                val nullLabel2 = Label()
                val endLabel = Label()
                methodVisitor.visitInsn(Opcodes.SWAP) // stack: - i2 - i1
                methodVisitor.visitInsn(Opcodes.DUP_X1) // stack: - i1 - i2 - i1
                methodVisitor.visitJumpInsn(Opcodes.IFNULL, nullLabel1) // stack: - i1 - i2
                methodVisitor.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    eqMethod.ownerTypeInfo.internalName,
                    "equals",
                    "(Ljava/lang/Object;)Z",
                    false
                ) // stack: - Z
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel) // stack: - Z
                methodVisitor.visitLabel(nullLabel1) // stack: - i1 - i2
                methodVisitor.visitInsn(Opcodes.SWAP) // stack: - i2 - i1
                methodVisitor.visitInsn(Opcodes.POP) // stack: - i2
                methodVisitor.visitJumpInsn(Opcodes.IFNULL, nullLabel2) // stack: -
                methodVisitor.visitInsn(Opcodes.ICONST_1) // stack: - Z
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel) // stack: - Z
                methodVisitor.visitLabel(nullLabel2) // stack: -
                methodVisitor.visitInsn(Opcodes.ICONST_0) // stack: - Z
                methodVisitor.visitLabel(endLabel) // stack: - Z
            }

            leftType is TypeInfo.Array && rightType is TypeInfo.Array -> {
                genObjectReferentialEquality(methodVisitor, invert)
            }

            leftType is TypeInfo.Primitive && rightType is TypeInfo.Primitive -> {
                genPrimitiveEquality(methodVisitor, leftType, invert)
            }
        }
    }

    private fun genReferentialEquality(
        methodVisitor: MethodVisitor,
        leftType: TypeInfo,
        rightType: TypeInfo,
        invert: Boolean
    ) {
        // lhs -> i1
        // rhs -> i2
        // stack: - i1 - i2

        when {
            (leftType is TypeInfo.Class && rightType is TypeInfo.Class) ||
                (leftType is TypeInfo.Array && rightType is TypeInfo.Array) -> {
                genObjectReferentialEquality(methodVisitor, invert)
            }

            leftType is TypeInfo.Primitive && rightType is TypeInfo.Primitive -> {
                genPrimitiveEquality(methodVisitor, leftType, invert)
            }
        }
    }

    private fun genObjectReferentialEquality(methodVisitor: MethodVisitor, invert: Boolean) {
        val falseLabel = Label()
        val endLabel = Label()
        methodVisitor.visitJumpInsn(if (invert) Opcodes.IF_ACMPEQ else Opcodes.IF_ACMPNE, falseLabel) // stack: -
        methodVisitor.visitInsn(Opcodes.ICONST_1) // stack: - Z
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel) // stack: - Z
        methodVisitor.visitLabel(falseLabel) // stack: -
        methodVisitor.visitInsn(Opcodes.ICONST_0) // stack: - Z
        methodVisitor.visitLabel(endLabel) // stack: - Z
    }

    private fun genPrimitiveEquality(methodVisitor: MethodVisitor, leftType: TypeInfo.Primitive, invert: Boolean) {
        // stack: - i1 - i2
        when (leftType.type) {
            PrimitiveType.Bool,
            PrimitiveType.Char,
            PrimitiveType.I8,
            PrimitiveType.I16,
            PrimitiveType.I32 -> {
                val trueLabel = Label()
                val endLabel = Label()
                methodVisitor.visitJumpInsn(if (invert) leftType.neOpcode else leftType.eqOpcode, trueLabel)
                methodVisitor.visitInsn(Opcodes.ICONST_0)
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
                methodVisitor.visitLabel(trueLabel)
                methodVisitor.visitInsn(Opcodes.ICONST_1)
                methodVisitor.visitLabel(endLabel)
            }

            PrimitiveType.I64,
            PrimitiveType.F32,
            PrimitiveType.F64 -> {
                val falseLabel = Label()
                val endLabel = Label()
                methodVisitor.visitInsn(leftType.eqOpcode) // Opcodes.LCMP, Opcodes.FCMPG or Opcodes.DCMPG
                methodVisitor.visitJumpInsn(Opcodes.IFNE, falseLabel) // stack: -
                methodVisitor.visitInsn(Opcodes.ICONST_1) // stack: - Z
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel) // stack: - Z
                methodVisitor.visitLabel(falseLabel) // stack: -
                methodVisitor.visitInsn(Opcodes.ICONST_0) // stack: - Z
                methodVisitor.visitLabel(endLabel) // stack: - Z
            }

            else -> {} // Unreachable
        }
        // stack: - Z
    }

    private fun genIntegerComparison(methodVisitor: MethodVisitor, opcode: Int) {
        val trueLabel = Label()
        val endLabel = Label()

        methodVisitor.visitJumpInsn(opcode, trueLabel)
        methodVisitor.visitInsn(Opcodes.ICONST_0)
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
        methodVisitor.visitLabel(trueLabel)
        methodVisitor.visitInsn(Opcodes.ICONST_1)
        methodVisitor.visitLabel(endLabel)
    }

    private fun genComparison(methodVisitor: MethodVisitor, operation: Expression.BinaryExpression.BinaryOperation, opcode: Int) {
        val trueLabel = Label()
        val endLabel = Label()
        val jmpOpcode = when (operation) {
            Expression.BinaryExpression.BinaryOperation.Greater -> Opcodes.IFGT
            Expression.BinaryExpression.BinaryOperation.GreaterEqual -> Opcodes.IFGE
            Expression.BinaryExpression.BinaryOperation.Lesser -> Opcodes.IFLT
            Expression.BinaryExpression.BinaryOperation.LesserEqual -> Opcodes.IFLE
            else -> -1
        }

        methodVisitor.visitInsn(opcode)
        methodVisitor.visitJumpInsn(jmpOpcode, trueLabel)
        methodVisitor.visitInsn(Opcodes.ICONST_0)
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
        methodVisitor.visitLabel(trueLabel)
        methodVisitor.visitInsn(Opcodes.ICONST_1)
        methodVisitor.visitLabel(endLabel)
    }

    private fun genAs(methodVisitor: MethodVisitor, `as`: Expression.As) {
        genExpression(methodVisitor, `as`.expression)

        val originalType = `as`.originalType
        val finalType = `as`.finalType

        if (originalType is TypeInfo.Primitive && finalType is TypeInfo.Class) {
            // Box
            val wrappedClazz = originalType.type.wrappedJvmClazz

            methodVisitor.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(wrappedClazz),
                "valueOf",
                "(${originalType.descriptor})${wrappedClazz.descriptorString()}",
                false
            )
        } else if (originalType is TypeInfo.Class && finalType is TypeInfo.Primitive) {
            // Unbox
            val wrappedClazz = finalType.type.wrappedJvmClazz

            methodVisitor.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                Type.getInternalName(wrappedClazz),
                "${finalType.type.originalName}Value",
                "()${finalType.descriptor}",
                false
            )
        } else if (originalType is TypeInfo.Primitive && finalType is TypeInfo.Primitive) {
            methodVisitor.visitInsn(getCastOpcode(originalType.type, finalType.type))
        } else if (originalType is TypeInfo.Class && finalType is TypeInfo.Class) {
            methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, finalType.internalName)
        }
    }

    private fun genBoolLiteral(methodVisitor: MethodVisitor, boolLiteral: Expression.BoolLiteral) {
        methodVisitor.visitLdcInsn(if (boolLiteral.value) 1 else 0)
    }

    private fun genNumberLiteral(methodVisitor: MethodVisitor, numberLiteral: Expression.NumberLiteral) {
        methodVisitor.visitLdcInsn(
            castNumber(
                numberLiteral.value,
                (numberLiteral.finalType as TypeInfo.Primitive).type
            )
        )
    }

    private fun genGenericClassSignature(
        genericConstraints: List<TypeInfo.GenericConstraint>,
        superTypeInfo: TypeInfo.Class,
        interfaceTypeInfos: List<TypeInfo.Class>
    ): String {
        val signatureWriter = SignatureWriter()

        for (genericConstraint in genericConstraints) {
            genGenericBoundSignature(signatureWriter, genericConstraint)
        }

        signatureWriter.visitSuperclass().visitClassType(superTypeInfo.internalName)

        for (interfaceTypeInfo in interfaceTypeInfos) {
            signatureWriter.visitInterface().visitClassType(interfaceTypeInfo.internalName)
        }

        signatureWriter.visitEnd()

        return signatureWriter.toString()
    }

    private fun genGenericFieldSignature(
        genericConstraint: TypeInfo
    ): String? =
        if (genericConstraint is TypeInfo.GenericConstraint &&
            genericConstraint.varianceType == TypeInfo.GenericConstraint.VarianceType.INVARIANCE
        ) {
            "T${genericConstraint.genericParameterName};"
        } else {
            null
        }

    private fun genGenericBoundSignature(
        signatureWriter: SignatureWriter,
        genericConstraint: TypeInfo.GenericConstraint
    ) {
        if (genericConstraint.varianceType == TypeInfo.GenericConstraint.VarianceType.INVARIANCE) {
            signatureWriter.visitFormalTypeParameter(genericConstraint.genericParameterName)

            if (genericConstraint.bounds.isNotEmpty() && genericConstraint.boundType != TypeInfo.GenericConstraint.BoundType.LOWER) {
                // Only upper bound needs to declare
                when (val firstBound = genericConstraint.bounds[0]) {
                    is TypeInfo.GenericConstraint -> {
                        signatureWriter.visitClassBound().visitTypeVariable(firstBound.genericParameterName)
                    }

                    is TypeInfo.Class -> {
                        if (!firstBound.isInterface) {
                            val signatureVisitor = signatureWriter.visitClassBound()
                            signatureVisitor.visitClassType(firstBound.internalName)
                            signatureVisitor.visitEnd()
                        }

                        if (genericConstraint.bounds.size > 1) {
                            val signatureVisitor = signatureWriter.visitInterfaceBound()

                            for (bound in genericConstraint.bounds.drop(1)) {
                                signatureWriter.visitClassType(bound.internalName)
                            }

                            signatureVisitor.visitEnd()
                        }
                    }
                }
            } else {
                val signatureVisitor = signatureWriter.visitClassBound()
                signatureVisitor.visitClassType(TypeInfo.Class.OBJECT_TYPE_INFO.internalName)
                signatureVisitor.visitEnd()
            }
        }
    }

    private fun genGenericFunctionSignature(
        genericConstraints: List<TypeInfo.GenericConstraint>,
        parameterTypeInfos: List<TypeInfo>,
        returnTypeInfo: TypeInfo
    ): String {
        val signatureWriter = SignatureWriter()

        genGenericFunctionSignature(signatureWriter, genericConstraints, parameterTypeInfos, returnTypeInfo)

        signatureWriter.visitEnd()

        return signatureWriter.toString()
    }

    private fun genGenericFunctionSignature(
        signatureWriter: SignatureWriter,
        genericConstraints: List<TypeInfo.GenericConstraint>,
        parameterTypeInfos: List<TypeInfo>,
        returnTypeInfo: TypeInfo
    ) {
        for (genericConstraint in genericConstraints) {
            genGenericBoundSignature(signatureWriter, genericConstraint)
        }

        val parameterSignatureVisitor = signatureWriter.visitParameterType()

        for (typeInfo in parameterTypeInfos) {
            genGenericSignature(parameterSignatureVisitor, typeInfo)
        }

        val returnTypeSignatureVisitor = signatureWriter.visitReturnType()

        genGenericSignature(returnTypeSignatureVisitor, returnTypeInfo, true)
    }

    private fun genGenericSignature(signatureVisitor: SignatureVisitor, typeInfo: TypeInfo, noVisitEnd: Boolean = false) {
        when (typeInfo) {
            is TypeInfo.Primitive -> genGenericSignature(signatureVisitor, typeInfo)
            is TypeInfo.Class -> {
                genGenericSignature(signatureVisitor, typeInfo)

                if (!noVisitEnd) {
                    signatureVisitor.visitEnd()
                }
            }

            is TypeInfo.Array -> genGenericSignature(signatureVisitor, typeInfo)
            is TypeInfo.GenericConstraint -> genGenericSignature(signatureVisitor, typeInfo)
        }
    }

    private fun genGenericSignature(signatureVisitor: SignatureVisitor, primitiveType: TypeInfo.Primitive) {
        signatureVisitor.visitBaseType(primitiveType.descriptor[0]) // Force cast
    }

    private fun genGenericSignature(signatureVisitor: SignatureVisitor, classType: TypeInfo.Class) {
        signatureVisitor.visitClassType(classType.internalName)
    }

    private fun genGenericSignature(signatureVisitor: SignatureVisitor, arrayType: TypeInfo.Array) {
        val arraySignatureVisitor = signatureVisitor.visitArrayType()

        genGenericSignature(arraySignatureVisitor, arrayType.innerType)

        arraySignatureVisitor.visitEnd()
    }

    private fun genGenericSignature(
        signatureVisitor: SignatureVisitor,
        genericConstraint: TypeInfo.GenericConstraint
    ) {
        if (genericConstraint.varianceType == TypeInfo.GenericConstraint.VarianceType.INVARIANCE) {
            signatureVisitor.visitTypeVariable(genericConstraint.genericParameterName)
        } else {
            signatureVisitor.visitClassType(TypeInfo.Class.OBJECT_TYPE_INFO.internalName)
            signatureVisitor.visitEnd()
        }
    }

    private fun getClassWriter(clazz: TypeInfo.Class): ClassWriter =
        classWriters[clazz] ?: run {
            val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)

            classWriters[clazz] = classWriter

            classWriter.visit(
                Opcodes.V17,
                clazz.access + Opcodes.ACC_SUPER,
                clazz.internalName,
                genGenericClassSignature(
                    clazz.genericParameters,
                    clazz.superClassType ?: TypeInfo.Class.OBJECT_TYPE_INFO,
                    clazz.interfaceTypes
                ),
                clazz.superClassType?.internalName.orEmpty(),
                clazz.interfaceTypes.map(TypeInfo.Class::internalName).toTypedArray()
            )

            classWriter
        }

    private fun getStaticBlockWriter(clazz: TypeInfo.Class): MethodVisitor =
        staticBlockWriters[clazz] ?: run {
            val classWriter = getClassWriter(clazz)
            val methodVisitor = classWriter.visitMethod(
                Opcodes.ACC_STATIC,
                "<clinit>",
                "()V",
                null,
                null
            )

            staticBlockWriters[clazz] = methodVisitor

            methodVisitor.visitCode()

            methodVisitor
        }

    private fun getPrimaryConstructorWriter(clazz: TypeInfo.Class): MethodVisitor =
        primaryConstructorWriters[clazz] ?: run {
            val classWriter = getClassWriter(clazz)
            val methodVisitor = classWriter.visitMethod(
                Opcodes.ACC_PUBLIC,
                "<init>",
                "()V",
                null,
                null
            )

            primaryConstructorWriters[clazz] = methodVisitor

            methodVisitor.visitCode()

            methodVisitor
        }

    private fun getCastOpcode(from: PrimitiveType, to: PrimitiveType): Int = when (from) {
        PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> {
            when (to) {
                PrimitiveType.I64 -> Opcodes.I2L
                PrimitiveType.F32 -> Opcodes.I2F
                PrimitiveType.F64 -> Opcodes.I2D
                else -> -1
            }
        }

        PrimitiveType.I64 -> {
            when (to) {
                PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.L2I
                PrimitiveType.F32 -> Opcodes.L2F
                PrimitiveType.F64 -> Opcodes.L2D
                else -> -1
            }
        }

        PrimitiveType.F32 -> {
            when (to) {
                PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.F2I
                PrimitiveType.I64 -> Opcodes.F2L
                PrimitiveType.F64 -> Opcodes.F2D
                else -> -1
            }
        }

        PrimitiveType.F64 -> {
            when (to) {
                PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.D2I
                PrimitiveType.I64 -> Opcodes.D2L
                PrimitiveType.F32 -> Opcodes.D2F
                else -> -1
            }
        }

        else -> -1
    }

    private fun castNumber(originalNumber: Number, actualType: PrimitiveType): Number {
        return when (actualType) {
            PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> originalNumber.toInt()
            PrimitiveType.I64 -> originalNumber.toLong()
            PrimitiveType.F32 -> originalNumber.toFloat()
            PrimitiveType.F64 -> originalNumber.toDouble()
            else -> TODO("UNREACHABLE")
        }
    }
}
