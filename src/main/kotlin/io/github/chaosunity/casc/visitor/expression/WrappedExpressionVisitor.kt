package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.WrappedExpression

class WrappedExpressionVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<WrappedExpression>() {
    override fun visitWrappedExpression(ctx: CASCParser.WrappedExpressionContext): WrappedExpression {
        val expression = ctx.findExpression()?.accept(ev)!!

        return WrappedExpression(expression)
    }
}