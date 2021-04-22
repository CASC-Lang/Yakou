package io.github.chaosunity.casc.bytecodegen.instructions

import io.github.chaosunity.casc.parsing.symbol.Variable
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*

class PrintlnVariable(private val variable: Variable) : Instruction, Opcodes {
    override fun apply(mv: MethodVisitor) {
        val type = variable.type
        val id = variable.id

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")

        when (type) {
            io.github.chaosunity.antlr.CASCLexer.NUMBER -> {
                mv.visitVarInsn(ILOAD, id)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false)
            }
            io.github.chaosunity.antlr.CASCLexer.STRING -> {
                mv.visitVarInsn(ALOAD, id)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
            }
        }
    }
}