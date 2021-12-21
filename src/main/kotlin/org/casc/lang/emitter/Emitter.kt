package org.casc.lang.emitter

import org.casc.lang.ast.*
import org.casc.lang.ast.Function
import org.casc.lang.table.*
import org.objectweb.asm.ClassWriter
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
        when (statement) {
            is VariableDeclaration -> {
                emitExpression(methodVisitor, statement.expression!!)

                methodVisitor.visitVarInsn(statement.expression.type!!.storeOpcode, statement.index!!)
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
                    val variableIndex = expression.index!!

                    methodVisitor.visitVarInsn(expression.type!!.loadOpcode, variableIndex)
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
            is UnaryExpression -> {
                emitExpression(methodVisitor, expression.expression!!)

                emitAutoCast(methodVisitor, expression)

                when (expression.operator?.type) {
                    TokenType.Minus -> methodVisitor.visitInsn((expression.type!! as PrimitiveType).negOpcode)
                    TokenType.Plus -> {} // No effect
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

                val baseType = (expression.type as ArrayType).baseType

                if (baseType is ArrayType || baseType is ClassType || baseType == PrimitiveType.Str) {
                    methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, baseType.descriptor) // Cut down one dimension
                } else methodVisitor.visitIntInsn(Opcodes.NEWARRAY, (baseType as PrimitiveType).typeOpcode)

                methodVisitor.visitInsn(Opcodes.DUP)

                expression.elements.forEachIndexed { i, it ->
                    methodVisitor.visitLdcInsn(i)

                    emitExpression(methodVisitor, it!!)

                    methodVisitor.visitInsn((expression.type as ArrayType).getContentStoreOpcode()!!)

                    if (i != expression.elements.lastIndex) methodVisitor.visitInsn(Opcodes.DUP)
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
        if (expression.expression is AssignmentExpression) {
            emitAssignment(methodVisitor, expression.expression, true)
        } else {
            emitExpression(methodVisitor, expression.expression!!)
        }

        if (inAssignment || expression.retainLastValue) {
            // Duplicates value since there is another assignment going on
            val finalType = expression.expression.castTo ?: expression.expression.type!!
            if (finalType == PrimitiveType.F64 || finalType == PrimitiveType.I64) methodVisitor.visitInsn(Opcodes.DUP2)
            else methodVisitor.visitInsn(Opcodes.DUP)
        }

        emitAutoCast(methodVisitor, expression)

        methodVisitor.visitVarInsn(
            expression.castTo?.storeOpcode ?: expression.expression.type!!.storeOpcode,
            expression.index!!
        )
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
        } else if (from is ClassType && to is PrimitiveType) {
            // Boxed primitive type casting
            val primitiveFromClazz = PrimitiveType.fromClass(from.type())

            methodVisitor.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                from.internalName,
                "${to.internalName}Value",
                "()${to.descriptor}",
                false
            )
        }
        // TODO: Class-cast-to-class support
    }
}