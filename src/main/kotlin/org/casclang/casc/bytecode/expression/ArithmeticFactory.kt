package org.casclang.casc.bytecode.expression

import org.casclang.casc.parsing.node.expression.ArithmeticExpression
import org.casclang.casc.parsing.node.expression.ArithmeticExpression.*
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class ArithmeticFactory(private val ef: ExpressionFactory, private val mv: MethodVisitor) {
    fun generate(addition: Addition) {
        evaluateArithmeticComponents(addition)

        mv.visitInsn(addition.type.addOpcode)
    }

    fun generate(subtraction: Subtraction) {
        evaluateArithmeticComponents(subtraction)

        mv.visitInsn(subtraction.type.subtractOpcode)
    }

    fun generate(multiplication: Multiplication) {
        evaluateArithmeticComponents(multiplication)

        mv.visitInsn(multiplication.type.multiplyOpcode)
    }

    fun generate(division: Division) {
        evaluateArithmeticComponents(division)

        mv.visitInsn(division.type.divideOpcode)
    }

    private fun evaluateArithmeticComponents(expression: ArithmeticExpression<*>) {
        expression.leftExpression.accept(ef)
        expression.rightExpression.accept(ef)
    }

    private fun generateStringAppend(addition: Addition) {
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder")
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)

        val leftExpression = addition.leftExpression

        leftExpression.accept(ef)

        val leftExprDescriptor: String = leftExpression.type.descriptor
        var descriptor = "($leftExprDescriptor)Ljava/lang/StringBuilder;"

        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false)

        val rightExpression = addition.rightExpression

        rightExpression.accept(ef)

        val rightExprDescriptor: String = rightExpression.type.descriptor

        descriptor = "($rightExprDescriptor)Ljava/lang/StringBuilder;"
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false)
        mv.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/lang/StringBuilder",
            "toString",
            "()Ljava/lang/String;",
            false
        )
    }
}