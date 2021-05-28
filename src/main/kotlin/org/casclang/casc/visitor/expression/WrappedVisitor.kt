package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.Wrapped

class WrappedVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Wrapped>() {
    override fun visitWrappedExpression(ctx: CASCParser.WrappedExpressionContext): Wrapped {
        val expression = ctx.findExpression()?.accept(ev)!!

        return Wrapped(expression)
    }
}