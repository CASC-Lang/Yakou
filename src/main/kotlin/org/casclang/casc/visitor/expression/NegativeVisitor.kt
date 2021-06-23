package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.EmptyExpression
import org.casclang.casc.parsing.node.expression.Expression
import org.casclang.casc.parsing.node.expression.Negative
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.util.addError

class NegativeVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Expression<*>>() {
    override fun visitNegativeExpression(ctx: CASCParser.NegativeExpressionContext): Expression<*> {
        val expression = ctx.findExpression()?.accept(ev)!!

        when (val negativeText = ctx.NEG!!.text) {
            "-" -> {
                if (!expression.type.isNumeric()) {
                    addError(ctx, "Cannot negate non-numeric types with operator $negativeText.")
                    return EmptyExpression(BuiltInType.VOID)
                }
            }
            "!" -> {
                if (!expression.isBool()) {
                    addError(ctx, "Cannot negate non-bool types with operator $negativeText.")
                    return EmptyExpression(BuiltInType.VOID)
                }
            }
        }

        return Negative(expression)
    }
}