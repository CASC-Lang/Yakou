package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.IfExpression

class IfExpressionVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<IfExpression>() {
    override fun visitIfExpression(ctx: CASCParser.IfExpressionContext): IfExpression {
        val condition = ctx.condition!!.accept(ev)
        val trueExpression = ctx.trueExpression!!.accept(ev)
        val falseExpression = ctx.falseExpression!!.accept(ev)

        return IfExpression(condition, trueExpression, falseExpression)
    }
}