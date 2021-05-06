package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.LogicalOp
import io.github.chaosunity.casc.parsing.node.expression.Conditional
import io.github.chaosunity.casc.parsing.node.expression.VariableReference
import io.github.chaosunity.casc.parsing.node.statement.RangedForStatement
import io.github.chaosunity.casc.parsing.node.statement.StopAt
import jdk.internal.org.objectweb.asm.Label
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class ForFactory(private val mv: MethodVisitor) {
    fun generate(rangedFor: RangedForStatement) {
        val innerScope = rangedFor.scope
        val sf = StatementFactory(mv, innerScope)
        val ef = ExpressionFactory(mv, innerScope)
        val iterator = rangedFor.iteratorVariable

        val trueLabel = Label()
        val endLabel = Label()

        val iteratorVariableName = rangedFor.iteratorVarName
        val rightExpression = rangedFor.endExpression
        val leftExpression = VariableReference(rightExpression.type, iteratorVariableName)
        val conditionalExpression = Conditional(
            leftExpression,
            rightExpression,
            when (rangedFor.stopAt) {
                StopAt.TO -> LogicalOp.LESS_EQ
                StopAt.UNTIL -> LogicalOp.LESS
            }
        )

        sf.generate(iterator)
        ef.generate(conditionalExpression)
        mv.visitJumpInsn(IFEQ, endLabel)
        mv.visitLabel(trueLabel)
        sf.generate(rangedFor.statement)
        mv.visitIincInsn(innerScope.getLocalVariableIndex(iteratorVariableName), 1)
        ef.generate(conditionalExpression)
        mv.visitJumpInsn(IFNE, trueLabel)
        mv.visitLabel(endLabel)
    }
}