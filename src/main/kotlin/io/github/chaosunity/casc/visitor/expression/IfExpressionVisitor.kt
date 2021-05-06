package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.LogicalOp
import io.github.chaosunity.casc.parsing.node.expression.Conditional
import io.github.chaosunity.casc.parsing.node.expression.IfExpression

class IfExpressionVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<IfExpression>() {
    override fun visitIfExpression(ctx: CASCParser.IfExpressionContext): IfExpression {
        val condition = if (ctx.left == null) {
            ctx.condition?.accept(ev)!!
        } else {
            Conditional(ctx.left?.accept(ev)!!, ctx.right?.accept(ev)!!, LogicalOp.fromString(ctx.cmp?.text))
        }
        val trueExpression = ctx.trueExpression!!.accept(ev)
        val falseExpression = ctx.falseExpression!!.accept(ev)

        return IfExpression(condition, trueExpression, falseExpression)
    }
}