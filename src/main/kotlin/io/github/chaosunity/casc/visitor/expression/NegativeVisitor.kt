package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.NegativeExpression

class NegativeVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<NegativeExpression>() {
    override fun visitNegativeExpression(ctx: CASCParser.NegativeExpressionContext): NegativeExpression {
        val expression = ctx.findExpression()?.accept(ev)!!

        return NegativeExpression(expression)
    }
}