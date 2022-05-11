package org.casc.lang.emitter

import org.casc.lang.asm.CommonClassWriter
import org.casc.lang.ast.*
import org.casc.lang.ast.Function
import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.table.*
import org.casc.lang.table.Type
import org.objectweb.asm.*
import java.lang.invoke.CallSite
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.io.File as JFile

class Emitter(private val preference: AbstractPreference, private val declarationOnly: Boolean) {
    fun emit(file: File) =
        emitFile(file)

    private fun emitFile(file: File): ByteArray {
        val bytecode = emitTypeInstance(file.typeInstance, file.fileName)
        val outFile = JFile(preference.outputDir, "/${file.typeInstance.typeReference.className}.class")

        if (!preference.noEmit) {
            preference.outputDir.mkdirs()

            if (outFile.exists()) {
                outFile.delete()
            }

            outFile.writeBytes(bytecode)
        }

        return bytecode
    }

    private fun emitTypeInstance(typeInstance: TypeInstance, sourceFile: String): ByteArray {
        val classWriter =
            CommonClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS, preference.classLoader)

        classWriter.visit(
            Opcodes.V1_8,
            typeInstance.flag,
            typeInstance.reference.internalName(),
            null,
            typeInstance.parentClassReference.internalName(),
            null
        )

        classWriter.visitSource(sourceFile, null)

        if (!declarationOnly) {
            when (typeInstance) {
                is ClassInstance -> emitClass(classWriter, typeInstance)
                is TraitInstance -> emitTrait(classWriter, typeInstance)
            }
        }

        classWriter.visitEnd()

        return classWriter.toByteArray()
    }

    private fun emitClass(classWriter: ClassWriter, clazz: ClassInstance) {
        clazz.fields.forEach {
            emitField(classWriter, it)
        }

        clazz.impl?.let { emitImpl(classWriter, it) }
    }

    private fun emitTrait(classWriter: ClassWriter, trait: TraitInstance) {
        trait.fields.forEach {
            emitField(classWriter, it)
        }

        trait.impl?.let { emitImpl(classWriter, it) }
    }

    private fun emitImpl(classWriter: ClassWriter, impl: Impl) {
        val companionBlock = impl.companionBlock

        if (companionBlock != null && companionBlock.statements.isNotEmpty()) {
            val methodVisitor = classWriter.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null)

            methodVisitor.visitCode()

            companionBlock.statements.forEach {
                emitStatement(methodVisitor, it)
            }

            methodVisitor.visitInsn(Opcodes.RETURN)
            methodVisitor.visitMaxs(-1, -1)
            methodVisitor.visitEnd()
        }

        impl.functions.forEach {
            emitFunction(classWriter, it)
        }

        impl.constructors.forEach {
            emitConstructor(classWriter, it)
        }
    }

    private fun emitField(classWriter: ClassWriter, field: Field) {
        val classField = field.asClassField()

        classWriter.visitField(field.flag, classField.name, field.descriptor, null, null)
    }

    private fun emitConstructor(classWriter: ClassWriter, constructor: Constructor) {
        val methodVisitor = classWriter.visitMethod(
            constructor.flag,
            "<init>",
            constructor.descriptor,
            null,
            null
        )

        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)

        constructor.parentConstructorArguments.forEach {
            if (it != null) emitExpression(methodVisitor, it)
        }

        methodVisitor.visitMethodInsn(
            Opcodes.INVOKESPECIAL,
            if (constructor.superKeyword != null) constructor.parentType!!.internalName
            else if (constructor.selfKeyword != null) constructor.ownerType!!.internalName
            else "java/lang/Object",
            "<init>",
            constructor.parentConstructorSignature!!.descriptor,
            false
        )

        constructor.statements.forEach {
            emitStatement(methodVisitor, it)
        }

        methodVisitor.visitInsn(Opcodes.RETURN)
        methodVisitor.visitCode()
        methodVisitor.visitMaxs(-1, -1)
        methodVisitor.visitEnd()
    }

    private fun emitFunction(classWriter: ClassWriter, function: Function) {
        val methodVisitor = classWriter.visitMethod(
            function.flag,
            function.name!!.literal,
            function.descriptor,
            null,
            null
        )

        if (function.statements != null) {
            methodVisitor.visitCode()

            function.statements.forEach {
                emitStatement(methodVisitor, it)
            }

            if (function.returnType == PrimitiveType.Unit)
                methodVisitor.visitInsn(Opcodes.RETURN)

            methodVisitor.visitMaxs(-1, -1)
            methodVisitor.visitEnd()
        }
    }

    private fun emitStatement(methodVisitor: MethodVisitor, statement: Statement) {
        if (statement.pos != null) {
            val label = Label()

            methodVisitor.visitLabel(label)
            methodVisitor.visitLineNumber(statement.pos?.lineNumber!!, label)
        }

        when (statement) {
            is VariableDeclaration -> {
                if (statement.expressions.size == 1) {
                    // one-to-many
                    val expression = statement.expressions[0]!!
                    emitExpression(methodVisitor, expression)

                    for ((i, index) in statement.indexes.withIndex()) {
                        if (i != statement.indexes.lastIndex) methodVisitor.visitInsn(getDupOpcode(expression.type)!!)

                        methodVisitor.visitVarInsn(expression.type!!.storeOpcode, index)
                    }
                } else {
                    // many-to-many
                    for ((expression, index) in statement.expressions.zip(statement.indexes)) {
                        emitExpression(methodVisitor, expression!!)

                        methodVisitor.visitVarInsn(expression.type!!.storeOpcode, index)
                    }
                }
            }
            is IfStatement -> {
                emitExpression(methodVisitor, statement.condition!!)

                val hasElseStatement = statement.elseStatement != null
                val elseLabel = Label()
                val endLabel = if (hasElseStatement) Label() else null

                methodVisitor.visitJumpInsn(Opcodes.IFEQ, elseLabel)

                emitStatement(methodVisitor, statement.trueStatement!!)

                if (hasElseStatement) methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
                methodVisitor.visitLabel(elseLabel)

                if (hasElseStatement) {
                    emitStatement(methodVisitor, statement.elseStatement!!)

                    methodVisitor.visitLabel(endLabel)
                }
            }
            is JForStatement -> {
                val startLabel = Label()
                val endLabel = Label()

                if (statement.initStatement != null)
                    emitStatement(methodVisitor, statement.initStatement)

                methodVisitor.visitLabel(startLabel)

                if (statement.condition != null) {
                    emitExpression(methodVisitor, statement.condition)
                    methodVisitor.visitJumpInsn(Opcodes.IFEQ, endLabel)
                }

                for (innerStatement in statement.statements) {
                    emitStatement(methodVisitor, innerStatement!!)
                }

                if (statement.postExpression != null)
                    emitExpression(methodVisitor, statement.postExpression)

                methodVisitor.visitJumpInsn(Opcodes.GOTO, startLabel)
                methodVisitor.visitLabel(endLabel)
            }
            is BlockStatement -> {
                statement.statements.forEach {
                    emitStatement(methodVisitor, it!!)
                }
            }
            is ExpressionStatement -> {
                statement.expression?.let {
                    emitExpression(methodVisitor, it)

                    if (it is InvokeCall && !it.retainValue) {
                        if (it is FunctionCallExpression) {
                            // Check if function's return value is unused, if yes, then add pop or pop2 opcode
                            if (it.type == PrimitiveType.I64 || it.type == PrimitiveType.F64) methodVisitor.visitInsn(
                                Opcodes.POP2
                            )
                            else if (it.type != PrimitiveType.Unit) methodVisitor.visitInsn(Opcodes.POP)
                        } else if (it is ConstructorCallExpression) methodVisitor.visitInsn(Opcodes.POP)
                    }
                }
            }
            is ReturnStatement -> {
                emitExpression(methodVisitor, statement.expression!!)

                methodVisitor.visitInsn(statement.returnType!!.returnOpcode)
            }
        }
    }

    private fun emitExpression(methodVisitor: MethodVisitor, expression: Expression) {
        when (expression) {
            is IntegerLiteral -> {
                when {
                    expression.isI64() -> {
                        expression.removeTypeSuffix()

                        methodVisitor.visitLdcInsn(expression.literal!!.literal.toLong())
                    }
                    else -> { // Handles I8, I16, I32
                        expression.removeTypeSuffix()

                        methodVisitor.visitLdcInsn(expression.literal!!.literal.toInt())
                    }
                }
            }
            is FloatLiteral -> {
                if (expression.isF64()) {
                    expression.removeTypeSuffix()

                    methodVisitor.visitLdcInsn(expression.literal!!.literal.toDouble())
                } else {
                    expression.removeTypeSuffix()

                    methodVisitor.visitLdcInsn(expression.literal!!.literal.toFloat())
                }
            }
            is CharLiteral -> {
                val char = expression.literal!!.literal[0]

                methodVisitor.visitLdcInsn(char.code)
            }
            is StrLiteral -> {
                val string = expression.literal!!.literal

                methodVisitor.visitLdcInsn(string)
            }
            is BoolLiteral -> when (expression.literal?.literal) {
                "false" -> methodVisitor.visitInsn(Opcodes.ICONST_0)
                "true" -> methodVisitor.visitInsn(Opcodes.ICONST_1)
            }
            is NullLiteral -> methodVisitor.visitInsn(Opcodes.ACONST_NULL)
            is AssignmentExpression -> emitAssignment(methodVisitor, expression)
            is IdentifierCallExpression -> {
                if (expression.ownerReference != null) {
                    // Appointed class field / local field
                    if (!expression.isCompField)
                        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)

                    methodVisitor.visitFieldInsn(
                        if (expression.isCompField) Opcodes.GETSTATIC else Opcodes.GETFIELD,
                        expression.ownerReference!!.internalName(),
                        expression.name!!.literal,
                        expression.type!!.descriptor
                    )
                } else if (expression.previousExpression != null) {
                    emitExpression(methodVisitor, expression.previousExpression!!)

                    if (expression.previousExpression!!.type is ArrayType && expression.name!!.literal == "len") {
                        // Array len calling
                        methodVisitor.visitInsn(Opcodes.ARRAYLENGTH)
                    } else {
                        // Chain Calling
                        methodVisitor.visitFieldInsn(
                            if (expression.isCompField) Opcodes.GETSTATIC else Opcodes.GETFIELD,
                            expression.previousExpression?.type?.internalName,
                            expression.name!!.literal,
                            expression.type!!.descriptor
                        )
                    }
                } else if (!expression.isClassName) {
                    if (!expression.isAssignedBy) {
                        // Local variable
                        methodVisitor.visitVarInsn(expression.type!!.loadOpcode, expression.index!!)
                    }
                }
            }
            is FunctionCallExpression -> {
                val functionSignature = expression.referenceFunctionSignature!!

                // Emit Chain calling expression
                if (expression.previousExpression != null)
                    emitExpression(methodVisitor, expression.previousExpression!!)

                // Emit arguments
                expression.arguments.forEach {
                    emitExpression(methodVisitor, it!!)
                }

                if (functionSignature.companion) {
                    // Use INVOKESTATIC
                    methodVisitor.visitMethodInsn(
                        Opcodes.INVOKESTATIC, // TODO: Support complex calling
                        functionSignature.ownerReference.internalName(),
                        functionSignature.name,
                        functionSignature.descriptor,
                        functionSignature.ownerType?.isTrait == true
                    )
                } else {
                    // Use INVOKEVIRTUAL instead
                    methodVisitor.visitMethodInsn(
                        when {
                            expression.superCall -> Opcodes.INVOKESPECIAL
                            functionSignature.ownerType?.isTrait == true -> Opcodes.INVOKEINTERFACE
                            else -> Opcodes.INVOKEVIRTUAL
                        },
                        functionSignature.ownerReference.internalName(),
                        functionSignature.name,
                        functionSignature.descriptor,
                        functionSignature.ownerType?.isTrait == true
                    )
                }
            }
            is ConstructorCallExpression -> {
                val signature = expression.referenceFunctionSignature!!

                methodVisitor.visitTypeInsn(Opcodes.NEW, signature.ownerReference.internalName())
                methodVisitor.visitInsn(Opcodes.DUP)

                // Emit arguments
                expression.arguments.forEach {
                    emitExpression(methodVisitor, it!!)
                }

                methodVisitor.visitMethodInsn(
                    Opcodes.INVOKESPECIAL,
                    signature.ownerReference.internalName(),
                    signature.name,
                    signature.descriptor,
                    false
                )
            }
            is IndexExpression -> {
                emitExpression(methodVisitor, expression.previousExpression!!)
                emitExpression(methodVisitor, expression.indexExpression!!)

                if (!expression.isAssignedBy)
                    methodVisitor.visitInsn((expression.previousExpression.type as ArrayType).getContentLoadOpcode()!!)
            }
            is UnaryExpression -> {
                emitExpression(methodVisitor, expression.expression!!)

                when (expression.operator!!.type) {
                    TokenType.Plus -> {} // No effect
                    TokenType.Minus -> methodVisitor.visitInsn((expression.type!! as PrimitiveType).negOpcode)
                    TokenType.DoublePlus, TokenType.DoubleMinus -> {
                        if (expression.postfix) {
                            if (expression.retainValue) methodVisitor.visitInsn(getDupOpcode(expression.type) ?: -1)
                            methodVisitor.visitLdcInsn(1)

                            if (expression.operator.type == TokenType.DoublePlus) {
                                methodVisitor.visitInsn((expression.type!! as PrimitiveType).addOpcode)
                            } else {
                                methodVisitor.visitInsn((expression.type!! as PrimitiveType).subOpcode)
                            }

                            methodVisitor.visitIntInsn(
                                expression.type!!.storeOpcode,
                                (expression.expression as IdentifierCallExpression).index!!
                            )
                        } else {
                            methodVisitor.visitLdcInsn(1)

                            if (expression.operator.type == TokenType.DoublePlus) {
                                methodVisitor.visitInsn((expression.type!! as PrimitiveType).addOpcode)
                            } else {
                                methodVisitor.visitInsn((expression.type!! as PrimitiveType).subOpcode)
                            }

                            if (expression.retainValue) methodVisitor.visitInsn(getDupOpcode(expression.type) ?: -1)
                            methodVisitor.visitIntInsn(
                                expression.type!!.storeOpcode,
                                (expression.expression as IdentifierCallExpression).index!!
                            )
                        }
                    }
                    TokenType.Bang -> {
                        emitComparisonExpression(methodVisitor, expression)
                    }
                    TokenType.Tilde -> when (expression.type) {
                        PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> {
                            methodVisitor.visitInsn(Opcodes.IXOR)
                        }
                        PrimitiveType.I64 -> {
                            methodVisitor.visitInsn(Opcodes.LXOR)
                        }
                        else -> {} // No effect
                    }
                    else -> {}
                }
            }
            is BinaryExpression -> {
                when (expression.operator?.type) {
                    TokenType.DoubleAmpersand, TokenType.DoublePipe -> {
                        emitAndOrOperators(methodVisitor, expression)
                    }
                    TokenType.Greater, TokenType.GreaterEqual, TokenType.Lesser,
                    TokenType.LesserEqual, TokenType.EqualEqual, TokenType.BangEqual -> {
                        emitBinaryExpressions(methodVisitor, expression.left!!, expression.right!!)

                        emitComparisonExpression(methodVisitor, expression)
                    }
                    TokenType.Hat, TokenType.Pipe, TokenType.Ampersand,
                    TokenType.DoubleGreater, TokenType.TripleGreater, TokenType.DoubleLesser -> {
                        emitBinaryExpressions(methodVisitor, expression.left!!, expression.right!!)

                        emitBitOperation(methodVisitor, expression)
                    }
                    else -> {
                        if (expression.isConcatExpression) {
                            emitStringConcat(methodVisitor, expression)
                        } else {
                            emitBinaryExpressions(methodVisitor, expression.left!!, expression.right!!)

                            val type = (expression.type!! as PrimitiveType)
                            val opcode = when (expression.operator?.type) {
                                TokenType.Plus -> type.addOpcode
                                TokenType.Minus -> type.subOpcode
                                TokenType.Star -> type.mulOpcode
                                TokenType.Slash -> type.divOpcode
                                TokenType.Percentage -> type.remOpcode
                                else -> null // Should not be null
                            }

                            methodVisitor.visitInsn(opcode!!)
                        }
                    }
                }
            }
            is ParenthesizedExpression -> emitExpression(methodVisitor, expression.expression!!)
            is ArrayInitialization -> {
                methodVisitor.visitLdcInsn(expression.elements.size)

                when (val baseType = (expression.type as ArrayType).baseType) {
                    is ArrayType -> methodVisitor.visitTypeInsn(
                        Opcodes.ANEWARRAY,
                        baseType.descriptor
                    )
                    // Cut down one dimension
                    is ClassType, PrimitiveType.Str -> methodVisitor.visitTypeInsn(
                        Opcodes.ANEWARRAY,
                        baseType.internalName
                    )
                    else -> methodVisitor.visitIntInsn(Opcodes.NEWARRAY, (baseType as PrimitiveType).typeOpcode)
                }

                methodVisitor.visitInsn(Opcodes.DUP)

                expression.elements.forEachIndexed { i, it ->
                    methodVisitor.visitLdcInsn(i)

                    emitExpression(methodVisitor, it!!)

                    methodVisitor.visitInsn((expression.type as ArrayType).getContentStoreOpcode()!!)

                    if (i != expression.elements.lastIndex) methodVisitor.visitInsn(Opcodes.DUP)
                }
            }
            is ArrayDeclaration -> {
                expression.dimensionExpressions.filterNotNull().forEach {
                    emitExpression(methodVisitor, it)
                }

                val arrayType = expression.type as ArrayType
                val baseType = arrayType.baseType

                if (expression.dimensionExpressions.size > 1) {
                    methodVisitor.visitMultiANewArrayInsn(
                        expression.type?.descriptor,
                        expression.dimensionExpressions.size
                    )
                } else if (baseType is PrimitiveType && baseType != PrimitiveType.Str) {
                    methodVisitor.visitIntInsn(Opcodes.NEWARRAY, baseType.typeOpcode)
                } else {
                    methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, baseType.internalName)
                }
            }
            is IfExpression -> {
                emitExpression(methodVisitor, expression.condition!!)

                val elseLabel = Label()
                val endLabel = Label()

                methodVisitor.visitJumpInsn(Opcodes.IFEQ, elseLabel)

                emitStatement(methodVisitor, expression.trueStatement!!)

                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
                methodVisitor.visitLabel(elseLabel)

                emitStatement(methodVisitor, expression.elseStatement!!)

                methodVisitor.visitLabel(endLabel)
            }
            is AsExpression -> {
                emitExpression(methodVisitor, expression.expression!!)

                if (expression.type is PrimitiveType) {
                    // It must be primitive-to-primitive casting
                    methodVisitor.visitInsn(expression.castOpcode)
                } else {
                    // It must be object-to-object casting
                }
            }
        }

        emitAutoCast(methodVisitor, expression)
    }

    private fun emitAssignment(
        methodVisitor: MethodVisitor,
        expression: AssignmentExpression,
        inAssignment: Boolean = false
    ) {
        if (expression.leftExpression is IdentifierCallExpression && expression.leftExpression.index == null) {
            if (!expression.leftExpression.isCompField)
                methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
        } else emitExpression(methodVisitor, expression.leftExpression!!)


        if (expression.rightExpression is AssignmentExpression) {
            emitAssignment(methodVisitor, expression.rightExpression, true)
            emitAutoCast(methodVisitor, expression.rightExpression)
        } else {
            emitExpression(methodVisitor, expression.rightExpression!!)
        }

        if (inAssignment || expression.retainLastValue) {
            // Duplicates value since there is another assignment going on
            val finalType = expression.rightExpression.castTo ?: expression.rightExpression.type!!

            when (expression.leftExpression) {
                is IndexExpression ->
                    methodVisitor.visitInsn(getDupOpcode(finalType, true)!!)
                else ->
                    methodVisitor.visitInsn(getDupOpcode(finalType)!!)
            }
        }

        // Store Values based on left expression's type
        when (expression.leftExpression) {
            is IndexExpression -> {
                methodVisitor.visitInsn((expression.leftExpression.previousExpression?.type as ArrayType).getContentStoreOpcode()!!)
            }
            is IdentifierCallExpression -> {
                if (expression.leftExpression.index == null) {
                    // Field assignment
                    methodVisitor.visitFieldInsn(
                        if (expression.leftExpression.isCompField) Opcodes.PUTSTATIC else Opcodes.PUTFIELD,
                        expression.leftExpression.ownerReference!!.fullQualifiedPath,
                        expression.leftExpression.name!!.literal,
                        expression.leftExpression.type!!.descriptor
                    )
                } else {
                    methodVisitor.visitVarInsn(
                        expression.leftExpression.type!!.storeOpcode,
                        expression.leftExpression.index!!
                    )
                }
            }
            else -> {}
        }
    }

    private fun emitStringConcat(
        methodVisitor: MethodVisitor,
        expression: BinaryExpression
    ) {
        val flattenedExpressions = Expression.flattenExpressionTree(expression)

        if (flattenedExpressions.all { it is LiteralExpression }) {
            val stringLiteral = flattenedExpressions.map { (it as LiteralExpression).getLiteral() }.joinToString("")

            methodVisitor.visitLdcInsn(stringLiteral)
        } else {
            val methodDescriptor = StringBuilder(flattenedExpressions.size)
            var builder = ""

            for (expr in flattenedExpressions) {
                expr?.let {
                    builder += when (it) {
                        is StrLiteral -> it.literal!!.literal
                        is IntegerLiteral -> it.literal!!.literal
                        is FloatLiteral -> it.literal!!.literal
                        is CharLiteral -> it.literal!!.literal
                        is BoolLiteral -> it.literal!!.literal
                        is NullLiteral -> it.literal!!.literal
                        else -> {
                            methodDescriptor.append(it.type!!.descriptor)
                            "\u0001"
                        }
                    }

                    if (expr !is LiteralExpression)
                        emitExpression(methodVisitor, expr)
                }
            }

            val mt = MethodType.methodType(
                CallSite::class.java,
                MethodHandles.Lookup::class.java,
                String::class.java,
                MethodType::class.java,
                String::class.java,
                Array<Any>::class.java
            )
            val h = Handle(
                Opcodes.H_INVOKESTATIC,
                "java/lang/invoke/StringConcatFactory",
                "makeConcatWithConstants",
                mt.toMethodDescriptorString(),
                false
            )

            methodVisitor.visitInvokeDynamicInsn(
                "makeConcatWithConstants",
                "($methodDescriptor)Ljava/lang/String;",
                h,
                builder
            )
        }
    }

    private fun emitBinaryExpressions(
        methodVisitor: MethodVisitor,
        leftExpression: Expression,
        rightExpression: Expression
    ) {
        emitExpression(methodVisitor, leftExpression)
        emitExpression(methodVisitor, rightExpression)
    }

    private fun emitComparisonExpression(methodVisitor: MethodVisitor, expression: Expression) {
        val trueLabel = Label()
        val endLabel = Label()

        if (expression is BinaryExpression) {
            val leftExpression = expression.left!!
            val leftType = leftExpression.castTo ?: leftExpression.type

            if (leftType == PrimitiveType.I64 || leftType == PrimitiveType.F32 || leftType == PrimitiveType.F64) {
                when (leftType) {
                    PrimitiveType.I64 -> methodVisitor.visitInsn(Opcodes.LCMP)
                    PrimitiveType.F32 -> methodVisitor.visitInsn(Opcodes.FCMPG)
                    PrimitiveType.F64 -> methodVisitor.visitInsn(Opcodes.DCMPG)
                    else -> {}
                }

                val opcode = when (expression.operator!!.type) {
                    TokenType.EqualEqual -> Opcodes.IFEQ
                    TokenType.BangEqual -> Opcodes.IFNE
                    TokenType.Greater -> Opcodes.IFGT
                    TokenType.GreaterEqual -> Opcodes.IFGE
                    TokenType.Lesser -> Opcodes.IFLT
                    TokenType.LesserEqual -> Opcodes.IFLE
                    else -> -1 // Should not be -1
                }

                methodVisitor.visitJumpInsn(opcode, trueLabel)
            } else {
                val opcode = when (expression.operator!!.type) {
                    TokenType.EqualEqual ->
                        if (expression.getExpressions().any { it?.type == PrimitiveType.Null })
                            Opcodes.IFNONNULL else Opcodes.IF_ICMPEQ
                    TokenType.BangEqual ->
                        if (expression.getExpressions().any { it?.type == PrimitiveType.Null })
                            Opcodes.IFNULL else Opcodes.IF_ICMPNE
                    TokenType.Greater -> Opcodes.IF_ICMPGT
                    TokenType.GreaterEqual -> Opcodes.IF_ICMPGE
                    TokenType.Lesser -> Opcodes.IF_ICMPLT
                    TokenType.LesserEqual -> Opcodes.IF_ICMPLE
                    else -> -1 // Should not be -1
                }

                methodVisitor.visitJumpInsn(opcode, trueLabel)
            }
        } else if (expression is UnaryExpression) {
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, trueLabel)
        }

        methodVisitor.visitInsn(Opcodes.ICONST_0)
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
        methodVisitor.visitLabel(trueLabel)
        methodVisitor.visitInsn(Opcodes.ICONST_1)
        methodVisitor.visitLabel(endLabel)
    }

    private fun emitAndOrOperators(
        methodVisitor: MethodVisitor,
        expression: BinaryExpression
    ) {
        val operatorType = expression.operator!!.type
        val trueLabel = Label()
        val endLabel = Label()

        emitExpression(methodVisitor, expression.left!!)

        if (operatorType == TokenType.DoubleAmpersand) {
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, trueLabel)
        } else if (operatorType == TokenType.DoublePipe) {
            methodVisitor.visitJumpInsn(Opcodes.IFNE, trueLabel)
        }

        emitExpression(methodVisitor, expression.right!!)

        if (operatorType == TokenType.DoubleAmpersand) {
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, trueLabel)
            methodVisitor.visitInsn(Opcodes.ICONST_1)
        } else if (operatorType == TokenType.DoublePipe) {
            methodVisitor.visitJumpInsn(Opcodes.IFNE, trueLabel)
            methodVisitor.visitInsn(Opcodes.ICONST_0)
        }

        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
        methodVisitor.visitLabel(trueLabel)

        if (operatorType == TokenType.DoubleAmpersand) {
            methodVisitor.visitInsn(Opcodes.ICONST_0)
        } else if (operatorType == TokenType.DoublePipe) {
            methodVisitor.visitInsn(Opcodes.ICONST_1)
        }

        methodVisitor.visitLabel(endLabel)
    }

    private fun emitBitOperation(methodVisitor: MethodVisitor, expression: Expression) {
        val operatorType = when (expression) {
            is BinaryExpression -> expression.operator?.type
            is UnaryExpression -> expression.operator?.type
            else -> null
        }
        val type = expression.type

        when (operatorType) {
            TokenType.Hat, TokenType.Tilde -> {
                if (type == PrimitiveType.I64) {
                    methodVisitor.visitInsn(Opcodes.LXOR)
                } else {
                    methodVisitor.visitInsn(Opcodes.IXOR)
                }
            }
            TokenType.Pipe -> {
                if (type == PrimitiveType.I64) {
                    methodVisitor.visitInsn(Opcodes.LOR)
                } else {
                    methodVisitor.visitInsn(Opcodes.IOR)
                }
            }
            TokenType.Ampersand -> {
                if (type == PrimitiveType.I64) {
                    methodVisitor.visitInsn(Opcodes.LAND)
                } else {
                    methodVisitor.visitInsn(Opcodes.IAND)
                }
            }
            TokenType.DoubleGreater -> {
                if (type == PrimitiveType.I64) {
                    methodVisitor.visitInsn(Opcodes.LSHR)
                } else {
                    methodVisitor.visitInsn(Opcodes.ISHR)
                }
            }
            TokenType.TripleGreater -> {
                if (type == PrimitiveType.I64) {
                    methodVisitor.visitInsn(Opcodes.LUSHR)
                } else {
                    methodVisitor.visitInsn(Opcodes.IUSHR)
                }
            }
            TokenType.DoubleLesser -> {
                if (type == PrimitiveType.I64) {
                    methodVisitor.visitInsn(Opcodes.LSHL)
                } else {
                    methodVisitor.visitInsn(Opcodes.ISHL)
                }
            }
            else -> {} // No effect
        }
    }

    private fun emitAutoCast(methodVisitor: MethodVisitor, expression: Expression) {
        if (expression.castTo != null) {
            emitAutoCast(methodVisitor, expression.type!!, expression.castTo!!)
        }
    }

    private fun emitAutoCast(methodVisitor: MethodVisitor, from: Type, to: Type) {
        if (from is PrimitiveType && to is PrimitiveType) {
            val opcode = TypeUtil.findPrimitiveCastOpcode(
                from,
                to
            )

            if (opcode != null)
                methodVisitor.visitInsn(opcode)
        } else if (from is ClassType && to is PrimitiveType && to != PrimitiveType.Null) {
            // Boxed primitive type casting, e.g. Integer -> int
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                from.internalName,
                "${to.internalName}Value",
                "()${to.descriptor}",
                false
            )
        } else if (from is PrimitiveType && to is ClassType && from != PrimitiveType.Null) {
            // Boxed primitive type casting, e.g. int -> Integer
            methodVisitor.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                to.internalName,
                "valueOf",
                "(${from.descriptor})${to.descriptor}",
                false
            )
        }
        // TODO: Class-cast-to-class support
    }

    private fun getDupOpcode(type: Type?, large: Boolean = false): Int? = when (type) {
        PrimitiveType.I64, PrimitiveType.F64 -> if (large) Opcodes.DUP2_X2 else Opcodes.DUP2
        null -> null
        else -> if (large) Opcodes.DUP_X2 else Opcodes.DUP
    }
}