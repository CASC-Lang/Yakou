package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.LogicalOp
import io.github.chaosunity.casc.parsing.node.expression.Conditional
import io.github.chaosunity.casc.parsing.node.expression.Value
import io.github.chaosunity.casc.parsing.type.BuiltInType

class ConditionalVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Conditional>() {
    override fun visitConditionalExpression(ctx: CASCParser.ConditionalExpressionContext): Conditional {
        val leftExpression = ctx.findExpression(0)!!.accept(ev)
        val rightExpression = ctx.findExpression(1)?.accept(ev) ?: Value(BuiltInType.INT, "0")
        val cmp = ctx.cmp
        val logicalOp = if (cmp != null) {
            LogicalOp.fromString(cmp.text)
        } else {
            LogicalOp.NOT_EQ
        }

        return Conditional(leftExpression, rightExpression, logicalOp)
    }
}