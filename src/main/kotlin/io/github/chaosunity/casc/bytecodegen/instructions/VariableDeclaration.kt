package io.github.chaosunity.casc.bytecodegen.instructions

import io.github.chaosunity.casc.parsing.symbol.Variable
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*

class VariableDeclaration(val variable: Variable) : Instruction, Opcodes {
    override fun apply(mv: MethodVisitor) {
        val type = variable.type

        when (type) {
            io.github.chaosunity.antlr.CASCLexer.NUMBER -> {
                val value = variable.value.toInt()
                mv.visitIntInsn(BIPUSH, value)
                mv.visitVarInsn(ISTORE, variable.id)
            }
            io.github.chaosunity.antlr.CASCLexer.STRING -> {
                mv.visitLdcInsn(variable.value)
                mv.visitVarInsn(ASTORE, variable.id)
            }
        }
    }
}