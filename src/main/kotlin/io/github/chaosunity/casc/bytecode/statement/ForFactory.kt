package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.LogicalOp
import io.github.chaosunity.casc.parsing.node.expression.Conditional
import io.github.chaosunity.casc.parsing.node.expression.LocalVariableReference
import io.github.chaosunity.casc.parsing.node.expression.Value
import io.github.chaosunity.casc.parsing.node.statement.ForLoopStatement
import io.github.chaosunity.casc.parsing.node.statement.InfiniteForStatement
import io.github.chaosunity.casc.parsing.node.statement.RangedForStatement
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.type.BuiltInType
import jdk.internal.org.objectweb.asm.Label
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class ForFactory(private val mv: MethodVisitor) {
    fun generate(infiniteFor: InfiniteForStatement) {
        val sf = StatementFactory(mv, infiniteFor.scope)

        val entranceLabel = Label()

        mv.visitLabel(entranceLabel)
        infiniteFor.statement.accept(sf)
        mv.visitJumpInsn(GOTO, entranceLabel)
    }

    fun generate(forLoop: ForLoopStatement) {
        val innerScope = forLoop.scope
        val sf = StatementFactory(mv, innerScope)
        val ef = ExpressionFactory(mv, innerScope)

        val conditionExpression = forLoop.conditionExpression ?: Value(BuiltInType.BOOLEAN, "true")

        val startLabel = Label()
        val endLabel = Label()

        forLoop.initStatement?.accept(sf)
        mv.visitLabel(startLabel)
        conditionExpression.accept(ef)
        mv.visitJumpInsn(IFEQ, endLabel)
        forLoop.statement.accept(sf)
        forLoop.postStatement?.accept(sf)
        mv.visitJumpInsn(GOTO, startLabel)
        mv.visitLabel(endLabel)
    }

    fun generate(rangedFor: RangedForStatement) {
        val innerScope = rangedFor.scope
        val sf = StatementFactory(mv, innerScope)
        val ef = ExpressionFactory(mv, innerScope)
        val iterator = rangedFor.iteratorVariable

        val trueLabel = Label()
        val endLabel = Label()

        val iteratorVariableName = rangedFor.iteratorVarName
        val rightExpression = rangedFor.endExpression
        val leftExpression = LocalVariable(iteratorVariableName, rightExpression.type)
        val iteratorVariable = LocalVariableReference(leftExpression)
        val conditionalExpression = if (rangedFor.forward) Conditional(
            iteratorVariable,
            rightExpression,
            if (rangedFor.stopTo) LogicalOp.LESS_EQ else LogicalOp.LESS
        ) else Conditional(
            iteratorVariable,
            rangedFor.startExpression,
            if (rangedFor.stopTo) LogicalOp.GREATER_EQ else LogicalOp.GREATER
        )

        iterator.accept(sf)
        conditionalExpression.accept(ef)
        mv.visitJumpInsn(IFEQ, endLabel)
        mv.visitLabel(trueLabel)
        rangedFor.statement.accept(sf)

        if (rangedFor.forward)
            mv.visitIincInsn(innerScope.getLocalVariableIndex(iteratorVariableName), 1)
        else
            mv.visitIincInsn(innerScope.getLocalVariableIndex(iteratorVariableName), -1)

        conditionalExpression.accept(ef)
        mv.visitJumpInsn(IFNE, trueLabel)
        mv.visitLabel(endLabel)
    }
}