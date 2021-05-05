package io.github.chaosunity.casc.bytecode.expression

import io.github.chaosunity.casc.parsing.node.expression.Conditional
import jdk.internal.org.objectweb.asm.Label
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class ConditionalFactory(private val ef: ExpressionFactory, private val mv: MethodVisitor) {
    fun generate(conditional: Conditional) {
        val leftExpression = conditional.leftExpression
        val rightExpression = conditional.rightExpression

        leftExpression.accept(ef)
        rightExpression.accept(ef)

        val logicalOp = conditional.logicalOp
        val endLabel = Label()
        val trueLabel = Label()

        mv.visitJumpInsn(logicalOp.opcode, trueLabel)
        mv.visitInsn(ICONST_0)
        mv.visitJumpInsn(GOTO, endLabel)
        mv.visitLabel(trueLabel)
        mv.visitInsn(ICONST_1)
        mv.visitLabel(endLabel)
    }
}