package org.casc.lang.emitter

import org.casc.lang.ast.*
import org.casc.lang.ast.Function
import org.casc.lang.table.*
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import java.io.File as JFile

class Emitter(private val outDir: JFile, private val files: List<File>) {
    fun emit() {
        for (file in files)
            emit(file)
    }

    private fun emit(file: File) {
        val bytecode = emitClass(file.clazz)
        val outFile = JFile(outDir, "/${file.clazz.name!!.literal}.class")

        outFile.writeBytes(bytecode)
    }

    private fun emitClass(clazz: Class): ByteArray {
        val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)

        classWriter.visit(
            61,
            Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,
            clazz.name!!.literal,
            null,
            "java/lang/Object",
            null
        )

        clazz.functions.forEach {
            emitFunction(classWriter, it)
        }

        classWriter.visitEnd()

        return classWriter.toByteArray()
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
                if (statement.initExpression != null)
                    emitExpression(methodVisitor, statement.initExpression)

                val startLabel = Label()
                val endLabel = Label()

                methodVisitor.visitLabel(startLabel)

                if (statement.condition != null)
                    emitExpression(methodVisitor, statement.condition)
                else methodVisitor.visitInsn(Opcodes.ICONST_1)

                methodVisitor.visitJumpInsn(Opcodes.IFEQ, endLabel)

                statement.statements.forEach {
                    emitStatement(methodVisitor, it!!)
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
                    // Appointed class field
                    methodVisitor.visitFieldInsn(
                        Opcodes.GETSTATIC,
                        expression.ownerReference.internalName(),
                        expression.name!!.literal,
                        expression.type!!.descriptor
                    )
                } else if (expression.previousExpression != null) {
                    // Chain Calling
                    emitExpression(methodVisitor, expression.previousExpression!!)

                    methodVisitor.visitFieldInsn(
                        Opcodes.GETSTATIC,
                        expression.previousExpression?.type?.internalName,
                        expression.name!!.literal,
                        expression.type!!.descriptor
                    )
                } else {
                    // Local variables / current class fields
                    if (!expression.isAssignedBy) {
                        val variableIndex = expression.index!!

                        methodVisitor.visitVarInsn(expression.type!!.loadOpcode, variableIndex)
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

                emitAutoCast(methodVisitor, expression)

                when (expression.operator?.type) {
                    TokenType.Minus -> methodVisitor.visitInsn((expression.type!! as PrimitiveType).negOpcode)
                    TokenType.Plus -> {} // No effect
                    else -> {}
                }
            }
            is BinaryExpression -> {
                emitExpression(methodVisitor, expression.left!!)
                if (expression.left!!.castTo != null)
                    emitAutoCast(methodVisitor, expression.left!!.type!!, expression.left!!.castTo!!)

                emitExpression(methodVisitor, expression.right!!)
                if (expression.right!!.castTo != null)
                    emitAutoCast(methodVisitor, expression.right!!.type!!, expression.right!!.castTo!!)

                emitAutoCast(methodVisitor, expression)

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
            val requireLargeDup = finalType == PrimitiveType.F64 || finalType == PrimitiveType.I64

            when (expression.leftExpression) {
                is IndexExpression ->
                    methodVisitor.visitInsn(if (requireLargeDup) Opcodes.DUP2_X2 else Opcodes.DUP_X2)
                else ->
                    methodVisitor.visitInsn(if (requireLargeDup) Opcodes.DUP2 else Opcodes.DUP)
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

//        methodVisitor.visitVarInsn(
//            expression.rightExpression.castTo?.storeOpcode ?: expression.rightExpression.type!!.storeOpcode,
//            expression.index!!
//        )

        emitAutoCast(methodVisitor, expression)
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
}