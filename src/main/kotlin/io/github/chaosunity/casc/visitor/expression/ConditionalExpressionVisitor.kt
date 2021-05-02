package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.LogicalOp
import io.github.chaosunity.casc.parsing.node.expression.ConditionalExpression
import io.github.chaosunity.casc.parsing.node.expression.Value
import io.github.chaosunity.casc.parsing.type.BuiltInType

class ConditionalExpressionVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<ConditionalExpression>() {
    override fun visitConditionalExpression(ctx: CASCParser.ConditionalExpressionContext): ConditionalExpression {
        val leftExpression = ctx.findExpression(0)!!.accept(ev)
        val rightExpression = ctx.findExpression(1)?.accept(ev) ?: Value(BuiltInType.INT, "0")
        val cmp = ctx.cmp
        val logicalOp = if (cmp != null) {
            LogicalOp.fromString(cmp.text)
        } else {
            LogicalOp.NOT_EQ
        }

        return ConditionalExpression(leftExpression, rightExpression, logicalOp)
    }
}