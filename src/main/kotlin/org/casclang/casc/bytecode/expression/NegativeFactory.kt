package org.casclang.casc.bytecode.expression

import org.casclang.casc.parsing.node.expression.Negative
import jdk.internal.org.objectweb.asm.Label
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class NegativeFactory(private val mv: MethodVisitor, private val ef: ExpressionFactory) {
    fun generate(negative: Negative) {
        val type = negative.type

        negative.expression.accept(ef)

        if (type.isNumeric()) {
            mv.visitInsn(type.negativeOpcode)
        } else {
            val trueLabel = Label()
            val endLabel = Label()

            mv.visitJumpInsn(IFNE, trueLabel)
            mv.visitInsn(ICONST_1)
            mv.visitJumpInsn(GOTO, endLabel)
            mv.visitLabel(trueLabel)
            mv.visitInsn(ICONST_0)
            mv.visitLabel(endLabel)
        }
    }
}