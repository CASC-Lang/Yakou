package io.github.chaosunity.casc.bytecode.expression

import io.github.chaosunity.casc.parsing.node.expression.IfExpression
import jdk.internal.org.objectweb.asm.Label
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class IfFactory(private val ef: ExpressionFactory, private val mv: MethodVisitor) {
    fun generate(ifExpression: IfExpression) {
        val condition = ifExpression.condition

        condition.accept(ef)

        val trueExpression = ifExpression.trueExpression
        val falseExpression = ifExpression.falseExpression

        val trueLabel = Label()
        val endLabel = Label()

        mv.visitJumpInsn(IFNE, trueLabel)

        falseExpression.accept(ef)

        mv.visitJumpInsn(GOTO, endLabel)
        mv.visitLabel(trueLabel)
        trueExpression.accept(ef)
        mv.visitLabel(endLabel)
    }
}