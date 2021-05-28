package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.LogicalOp
import org.casclang.casc.parsing.node.expression.Conditional
import org.casclang.casc.parsing.node.expression.IfExpression

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