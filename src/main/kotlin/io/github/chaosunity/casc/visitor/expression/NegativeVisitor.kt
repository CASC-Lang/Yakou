package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.Negative

class NegativeVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Negative>() {
    override fun visitNegativeExpression(ctx: CASCParser.NegativeExpressionContext): Negative {
        val expression = ctx.findExpression()?.accept(ev)!!

        when(val negativeText = ctx.NEG!!.text) {
            "-" -> {
                if (!expression.type.isNumeric())
                    throw RuntimeException("Cannot negate non-numeric types with operator $negativeText.")
            }
            "!" -> {
                if (!expression.isBool())
                    throw RuntimeException("Cannot negate non-bool types with operator $negativeText.")
            }
        }

        return Negative(expression)
    }
}