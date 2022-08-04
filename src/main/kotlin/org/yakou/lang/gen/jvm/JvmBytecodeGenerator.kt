package org.yakou.lang.gen.jvm

import org.objectweb.asm.*
import org.objectweb.asm.Type
import org.yakou.lang.ast.*
import org.yakou.lang.bind.*
import org.yakou.lang.compilation.CompilationSession
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.gen.ChainType
import java.io.File

class JvmBytecodeGenerator(private val compilationSession: CompilationSession) {
    private val table: Table = compilationSession.table

    private val classWriters: HashMap<TypeInfo.Class, ClassWriter> = hashMapOf()
    private val staticBlockWriters: HashMap<TypeInfo.Class, MethodVisitor> = hashMapOf()

    private var currentChainType: ChainType = ChainType.NONE
    private var conditionChain: MutableList<Label> = mutableListOf()
    private var currentLineLabel: Label? = null

    fun gen(compilationUnit: CompilationUnit) {
        genYkFile(compilationUnit.ykFile!!)
    }

    fun finalize() {
        for ((_, methodVisitor) in staticBlockWriters) {
            methodVisitor.visitInsn(Opcodes.RETURN)
            methodVisitor.visitMaxs(-1, -1)
            methodVisitor.visitEnd()
        }

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
            is Item.Package -> {
                if (item.items != null)
                    for (innerItem in item.items)
                        genItem(innerItem)
            }

            is Item.Const -> genConst(item)
            is Item.StaticField -> genStaticField(item)
            is Item.Class -> genClass(item)
            is Item.Function -> genFunction(item)
        }
    }

    private fun genConst(const: Item.Const) {
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

    private fun genStaticField(staticField: Item.StaticField) {
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
            staticField.fieldInstance.qualifiedOwnerPath,
            staticField.fieldInstance.name,
            staticField.fieldInstance.descriptor
        )
    }

    private fun genClass(clazz: Item.Class) {
        if (clazz.classItems != null)
            for (classItem in clazz.classItems)
                genClassItem(classItem)
    }

    private fun genClassItem(classItem: ClassItem) {
        when (classItem) {
            is ClassItem.Field -> genField(classItem)
        }
    }

    private fun genField(field: ClassItem.Field) {
        val classWriter = getClassWriter(field.fieldInstance.ownerTypeInfo)
        val fieldWriter = classWriter.visitField(
            field.fieldInstance.access,
            field.fieldInstance.name,
            field.fieldInstance.descriptor,
            null,
            null
        )

        fieldWriter.visitEnd()
    }

    private fun genFunction(function: Item.Function) {
        val classWriter = getClassWriter(function.functionInstance.ownerTypeInfo)
        val methodVisitor = classWriter.visitMethod(
            function.functionInstance.access,
            function.functionInstance.name,
            function.functionInstance.descriptor,
            null,
            null
        )

        methodVisitor.visitCode()

        if (function.body != null)
            genFunctionBody(methodVisitor, function.body)

        // Attempt to automatically generate return statement if:
        // 1. Last statement is not return statement
        // 2. Function returns unit
        if (function.functionInstance.returnTypeInfo == TypeInfo.Primitive.UNIT_TYPE_INFO &&
            function.body is FunctionBody.BlockExpression &&
            function.body.statements.lastOrNull() !is Statement.Return
        ) {
            methodVisitor.visitInsn(Opcodes.RETURN)
        }

        methodVisitor.visitMaxs(-1, -1)
        methodVisitor.visitEnd()
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
            is Statement.VariableDeclaration -> genVariableDeclaration(methodVisitor, statement)
            is Statement.Return -> genReturn(methodVisitor, statement)
            is Statement.ExpressionStatement -> genExpression(methodVisitor, statement.expression)
        }
    }

    private fun genVariableDeclaration(
        methodVisitor: MethodVisitor,
        variableDeclaration: Statement.VariableDeclaration
    ) {
        if (variableDeclaration.variableInstance.propagatable && variableDeclaration.variableInstance.referencedCount == 0)
            return

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

    private fun genReturn(methodVisitor: MethodVisitor, `return`: Statement.Return) {
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

        if (conditionChain.isNotEmpty() && currentChainType != ChainType.NONE)
            currentChainType = ChainType.NONE
    }

    private fun genBinaryExpression(methodVisitor: MethodVisitor, binaryExpression: Expression.BinaryExpression) {
        genExpression(methodVisitor, binaryExpression.leftExpression)
        genExpression(methodVisitor, binaryExpression.rightExpression)

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
                if (finalType is TypeInfo.Primitive) {
                    methodVisitor.visitInsn(binaryExpression.operation.getFunctorOpcode(finalType))
                } else {
                    throw IllegalStateException("Unable to emit `${binaryExpression.operation}` opcode for non-primitive type.")
                }
            }

            Expression.BinaryExpression.BinaryOperation.LogicalAnd -> {
                when (currentChainType) {
                    ChainType.OR -> {
                        val label = Label()
                        methodVisitor.visitLdcInsn(0)
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, label)
                        conditionChain.forEach(methodVisitor::visitLabel)
                        conditionChain.clear()
                        methodVisitor.visitLdcInsn(1)
                        methodVisitor.visitLabel(label)
                        currentChainType = ChainType.AND
                    }

                    ChainType.AND -> {}
                    ChainType.NONE -> {
                        currentChainType = ChainType.AND
                    }
                }

                val shortCircuitLabel = Label()

                conditionChain += shortCircuitLabel

                methodVisitor.visitJumpInsn(Opcodes.IFEQ, shortCircuitLabel)
            }

            Expression.BinaryExpression.BinaryOperation.LogicalOr -> {
                when (currentChainType) {
                    ChainType.AND -> {
                        val label = Label()
                        methodVisitor.visitLdcInsn(1)
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, label)
                        conditionChain.forEach(methodVisitor::visitLabel)
                        conditionChain.clear()
                        methodVisitor.visitLdcInsn(0)
                        methodVisitor.visitLabel(label)
                        currentChainType = ChainType.OR
                    }

                    ChainType.OR -> {}
                    ChainType.NONE -> {
                        currentChainType = ChainType.OR
                    }
                }

                val shortCircuitLabel = Label()

                conditionChain += shortCircuitLabel

                methodVisitor.visitJumpInsn(Opcodes.IFNE, shortCircuitLabel)
            }

            Expression.BinaryExpression.BinaryOperation.Equal -> {
                genStructuralEqualityCheck(methodVisitor, leftType, rightType, false)
            }

            Expression.BinaryExpression.BinaryOperation.NotEqual -> {
                genStructuralEqualityCheck(methodVisitor, leftType, rightType, true)
            }

            Expression.BinaryExpression.BinaryOperation.ExactEqual -> {
                genReferentialEquality(methodVisitor, leftType, rightType, false)
            }

            Expression.BinaryExpression.BinaryOperation.ExactNotEqual -> {
                genReferentialEquality(methodVisitor, leftType, rightType, true)
            }
        }
    }

    private fun genIdentifier(methodVisitor: MethodVisitor, identifier: Expression.Identifier) {
        when (val symbolInstance = identifier.symbolInstance) {
            is Variable -> {
                methodVisitor.visitVarInsn(symbolInstance.typeInfo.loadOpcode, symbolInstance.index)
            }

            is ClassMember.Field -> {
                if (symbolInstance.isStatic) {
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
                methodVisitor.visitInsn(Opcodes.SWAP)                   // stack: - i2 - i1
                methodVisitor.visitInsn(Opcodes.DUP_X1)                 // stack: - i1 - i2 - i1
                methodVisitor.visitJumpInsn(Opcodes.IFNULL, nullLabel1) // stack: - i1 - i2
                methodVisitor.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    eqMethod.ownerTypeInfo.internalName,
                    "equals",
                    eqMethod.descriptor,
                    false
                ) // stack: - Z
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)     // stack: - Z
                methodVisitor.visitLabel(nullLabel1)                    // stack: - i1 - i2
                methodVisitor.visitInsn(Opcodes.POP)                    // stack: - i2
                methodVisitor.visitJumpInsn(Opcodes.IFNULL, nullLabel2) // stack: -
                methodVisitor.visitInsn(Opcodes.ICONST_1)               // stack: - Z
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)     // stack: - Z
                methodVisitor.visitLabel(nullLabel2)                    // stack: -
                methodVisitor.visitInsn(Opcodes.ICONST_0)               // stack: - Z
                methodVisitor.visitLabel(endLabel)                      // stack: - Z
            }

            leftType is TypeInfo.Array && rightType is TypeInfo.Array -> {
                genObjectReferentialEquality(methodVisitor)
            }

            leftType is TypeInfo.Primitive && rightType is TypeInfo.Primitive -> {
                genPrimitiveEquality(methodVisitor, leftType)
            }
        }

        if (invert) {
            genInvert(methodVisitor)
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
                genObjectReferentialEquality(methodVisitor)
            }

            leftType is TypeInfo.Primitive && rightType is TypeInfo.Primitive -> {
                genPrimitiveEquality(methodVisitor, leftType)
            }
        }

        if (invert) {
            genInvert(methodVisitor)
        }
    }

    private fun genObjectReferentialEquality(methodVisitor: MethodVisitor) {
        val falseLabel = Label()
        val endLabel = Label()
        methodVisitor.visitJumpInsn(Opcodes.IF_ACMPNE, falseLabel)  // stack: -
        methodVisitor.visitInsn(Opcodes.ICONST_1)                   // stack: - Z
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)         // stack: - Z
        methodVisitor.visitLabel(falseLabel)                        // stack: -
        methodVisitor.visitInsn(Opcodes.ICONST_0)                   // stack: - Z
        methodVisitor.visitLabel(endLabel)                          // stack: - Z
    }

    private fun genPrimitiveEquality(methodVisitor: MethodVisitor, leftType: TypeInfo.Primitive) {
        // stack: - i1 - i2
        when (leftType.type) {
            PrimitiveType.Bool,
            PrimitiveType.Char,
            PrimitiveType.I8,
            PrimitiveType.I16,
            PrimitiveType.I32 -> methodVisitor.visitInsn(leftType.eqOpcode)

            PrimitiveType.I64,
            PrimitiveType.F32,
            PrimitiveType.F64 -> {
                val falseLabel = Label()
                val endLabel = Label()
                methodVisitor.visitInsn(leftType.eqOpcode) // Opcodes.LCMP, Opcodes.FCMPG or Opcodes.DCMPG
                methodVisitor.visitJumpInsn(Opcodes.IFNE, falseLabel)   // stack: -
                methodVisitor.visitInsn(Opcodes.ICONST_1)               // stack: - Z
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)     // stack: - Z
                methodVisitor.visitLabel(falseLabel)                    // stack: -
                methodVisitor.visitInsn(Opcodes.ICONST_0)               // stack: - Z
                methodVisitor.visitLabel(endLabel)                      // stack: - Z
            }

            else -> {} // Unreachable
        }
        // stack: - Z
    }

    private fun genInvert(methodVisitor: MethodVisitor) {
        val falseLabel = Label()
        val endLabel = Label()
        methodVisitor.visitJumpInsn(Opcodes.IFNE, falseLabel)
        methodVisitor.visitInsn(Opcodes.ICONST_1)
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
        methodVisitor.visitLabel(falseLabel)
        methodVisitor.visitInsn(Opcodes.ICONST_0)
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

    private fun getClassWriter(clazz: TypeInfo.Class): ClassWriter =
        classWriters[clazz] ?: run {
            val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)

            classWriters[clazz] = classWriter

            classWriter.visit(
                Opcodes.V17,
                clazz.access + Opcodes.ACC_SUPER,
                clazz.internalName,
                null, // TODO: Implement generic
                clazz.superClassType?.internalName.orEmpty(),
                clazz.interfaceTypes.map(TypeInfo.Class::internalName).toTypedArray()
            )

            classWriter
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