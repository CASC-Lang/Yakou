package org.casc.lang.emitter

import org.casc.lang.ast.*
import org.casc.lang.ast.Function
import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.table.*
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import java.io.File as JFile

class Emitter(private val preference: AbstractPreference) {
    fun emit(outDir: JFile, file: File) =
        emitFile(outDir, file)

    private fun emitFile(outDir: JFile, file: File) {
        val bytecode = emitClass(file.clazz)
        val outFile = JFile(outDir, "/${file.clazz.name!!.literal}.class")

        outDir.mkdirs()
        outFile.writeBytes(bytecode)
    }

    private fun emitClass(clazz: Class): ByteArray {
        val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)

        classWriter.visit(
            61,
            Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,
            "${
                if (clazz.packageReference != null) "${
                    clazz.packageReference.path.replace(
                        '.',
                        '/'
                    )
                }/" else ""
            }${clazz.name!!.literal}",
            null,
            "java/lang/Object",
            null
        )

        clazz.fields.forEach {
            emitField(classWriter, it)
        }

        clazz.functions.forEach {
            emitFunction(classWriter, it)
        }

        classWriter.visitEnd()

        return classWriter.toByteArray()
    }

    private fun emitField(classWriter: ClassWriter, field: Field) {
        val classField = field.asClassField()

        classWriter.visitField(field.accessFlag, classField.name, field.descriptor, null, null)
    }

    private fun emitFunction(classWriter: ClassWriter, function: Function) {
        val methodVisitor = classWriter.visitMethod(
            function.accessFlag,
            function.name!!.literal,
            function.descriptor,
            null,
            null
        )

        methodVisitor.visitCode()

        if (function.compKeyword == null)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0) // Auto load parent object

        function.statements.forEach {
            emitStatement(methodVisitor, it)
        }

        if (function.returnType == PrimitiveType.Unit)
            methodVisitor.visitInsn(Opcodes.RETURN)

        methodVisitor.visitMaxs(-1, -1)
        methodVisitor.visitEnd()
    }

    private fun emitStatement(methodVisitor: MethodVisitor, statement: Statement) {
        val label = Label()

        methodVisitor.visitLabel(label)
        methodVisitor.visitLineNumber(statement.pos?.lineNumber!!, label)

        when (statement) {
            is VariableDeclaration -> {
                emitExpression(methodVisitor, statement.expression!!)

                methodVisitor.visitVarInsn(statement.expression.type!!.storeOpcode, statement.index!!)
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
                if (statement.initStatement != null)
                    emitStatement(methodVisitor, statement.initStatement)

                val startLabel = Label()
                val endLabel = Label()

                methodVisitor.visitLabel(startLabel)

                if (statement.condition != null)
                    emitExpression(methodVisitor, statement.condition)
                else methodVisitor.visitInsn(Opcodes.ICONST_1)

                methodVisitor.visitJumpInsn(Opcodes.IFEQ, endLabel)

                emitStatement(methodVisitor, statement.statement!!)

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
                emitExpression(methodVisitor, statement.expression!!)
            }
            is ReturnStatement -> {
                emitExpression(methodVisitor, statement.expression!!)

                emitAutoCast(methodVisitor, statement.expression.type!!, statement.returnType!!)

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
                    methodVisitor.visitFieldInsn(
                        if (expression.isCompField) Opcodes.GETSTATIC else Opcodes.GETFIELD,
                        expression.ownerReference!!.internalName(),
                        expression.name!!.literal,
                        expression.type!!.descriptor
                    )
                } else if (expression.previousExpression != null) {
                    // Chain Calling
                    emitExpression(methodVisitor, expression.previousExpression!!)

                    // TODO: Implement GETFIELD
                    methodVisitor.visitFieldInsn(
                        Opcodes.GETSTATIC,
                        expression.previousExpression?.type?.internalName,
                        expression.name!!.literal,
                        expression.type!!.descriptor
                    )
                } else if (!expression.isClassName) {
                    // Local variables
                    if (!expression.isAssignedBy) {
                        val index = expression.index!!

                        // Local variable
                        methodVisitor.visitVarInsn(expression.type!!.loadOpcode, index)
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
                    // Use INVOKESTATIC or INVOKESPECIAL to call functions, depends on function itself is ctor or sth
                    methodVisitor.visitMethodInsn(
                        Opcodes.INVOKESTATIC, // TODO: Support complex calling
                        functionSignature.ownerReference.internalName(),
                        functionSignature.name,
                        functionSignature.descriptor,
                        false // TODO: Support interface function calling
                    )
                } else {
                    // Use INVOKEVIRTUAL instead
                    methodVisitor.visitMethodInsn(
                        Opcodes.INVOKEVIRTUAL,
                        functionSignature.ownerReference.internalName(),
                        functionSignature.name,
                        functionSignature.descriptor,
                        false // TODO: Support interface function calling
                    )
                }
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
                        emitBinaryExpressions(methodVisitor, expression.left!!, expression.right!!)

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
                expression.dimensionExpressions.forEach {
                    emitExpression(methodVisitor, it!!)
                }

                val baseType = (expression.type as ArrayType).baseType

                if (expression.dimensionExpressions.size > 1) {
                    methodVisitor.visitMultiANewArrayInsn(
                        expression.type?.descriptor,
                        expression.dimensionExpressions.size
                    )
                } else if (baseType is PrimitiveType) {
                    methodVisitor.visitIntInsn(Opcodes.NEWARRAY, baseType.typeOpcode)
                } else {
                    methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, baseType.internalName)
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
        emitExpression(methodVisitor, expression.leftExpression!!)

        if (expression.rightExpression is AssignmentExpression) {
            emitAssignment(methodVisitor, expression.rightExpression, true)
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
                // TODO: Implement field assignment
                methodVisitor.visitVarInsn(
                    expression.leftExpression.type!!.storeOpcode,
                    expression.leftExpression.index!!
                )
            }
            else -> {}
        }

        emitAutoCast(methodVisitor, expression)
    }

    private fun emitBinaryExpressions(
        methodVisitor: MethodVisitor,
        leftExpression: Expression,
        rightExpression: Expression
    ) {
        emitExpression(methodVisitor, leftExpression)
        emitAutoCast(methodVisitor, leftExpression)

        emitExpression(methodVisitor, rightExpression)
        emitAutoCast(methodVisitor, rightExpression)
    }

    private fun emitComparisonExpression(methodVisitor: MethodVisitor, expression: Expression) {
        val trueLabel = Label()
        val endLabel = Label()

        if (expression is BinaryExpression) {
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
        } else if (expression is UnaryExpression) {
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, trueLabel)
        }

        methodVisitor.visitInsn(Opcodes.ICONST_0)
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
        methodVisitor.visitLabel(trueLabel)
        methodVisitor.visitInsn(Opcodes.ICONST_1)
        methodVisitor.visitLabel(endLabel)
    }

    private fun emitAndOrOperators(methodVisitor: MethodVisitor, expression: BinaryExpression) {
        val operatorType = expression.operator!!.type
        val trueLabel = Label()
        val endLabel = Label()

        emitExpression(methodVisitor, expression.left!!)
        emitAutoCast(methodVisitor, expression.left!!)

        if (operatorType == TokenType.DoubleAmpersand) {
            methodVisitor.visitJumpInsn(Opcodes.IFEQ, trueLabel)
        } else if (operatorType == TokenType.DoublePipe) {
            methodVisitor.visitJumpInsn(Opcodes.IFNE, trueLabel)
        }

        emitExpression(methodVisitor, expression.right!!)
        emitAutoCast(methodVisitor, expression.right!!)

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