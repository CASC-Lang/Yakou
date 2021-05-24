package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.Wrapped

class WrappedVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Wrapped>() {
    override fun visitWrappedExpression(ctx: CASCParser.WrappedExpressionContext): Wrapped {
        val expression = ctx.findExpression()?.accept(ev)!!

        return Wrapped(expression)
    }
}