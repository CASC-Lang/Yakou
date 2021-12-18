package org.casc.lang.emitter

import org.casc.lang.ast.*
import org.casc.lang.ast.Function
import org.casc.lang.table.PrimitiveType
import org.casc.lang.table.TypeUtil
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
        }
    }

    private fun emitExpression(
        methodVisitor: MethodVisitor,
        expression: Expression,
        isInsideAssignment: Boolean = false
    ) {
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
            is IdentifierExpression -> {
                val variableIndex = expression.index!!

                methodVisitor.visitVarInsn(expression.type!!.loadOpcode, variableIndex)
            }
            is UnaryExpression -> {
                emitExpression(methodVisitor, expression.expression!!)

                when (expression.operator?.literal) {
                    "-" -> methodVisitor.visitInsn((expression.type!! as PrimitiveType).negOpcode)
                    "+" -> {} // No effect
                }
            }
        }
    }

    fun emitAssignment(methodVisitor: MethodVisitor, expression: AssignmentExpression, inAssignment: Boolean = false) {
        emitExpression(methodVisitor, expression.expression!!, true)

        if (inAssignment) {
            methodVisitor.visitInsn(Opcodes.DUP) // Duplicates value since there is another assignment going on
        }

        if (expression.castTo != null) {
            if (expression.type is PrimitiveType && expression.castTo is PrimitiveType) {
                val opcode = TypeUtil.findPrimitiveCastOpcode(
                    expression.type as PrimitiveType,
                    expression.castTo as PrimitiveType
                )

                if (opcode != null)
                    methodVisitor.visitInsn(opcode)
            }
            // TODO: Class-cast-to-class support
        }

        methodVisitor.visitVarInsn(
            expression.castTo?.storeOpcode ?: expression.expression.type!!.storeOpcode,
            expression.index!!
        )
    }
}