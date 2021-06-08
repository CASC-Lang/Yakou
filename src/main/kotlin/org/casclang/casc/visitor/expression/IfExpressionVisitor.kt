package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.LogicalOp
import org.casclang.casc.parsing.node.expression.Conditional
import org.casclang.casc.parsing.node.expression.Expression
import org.casclang.casc.parsing.node.expression.IfExpression
import org.casclang.casc.parsing.node.expression.Value
import org.casclang.casc.parsing.node.statement.IfStatement

class IfExpressionVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Expression<*>>() {
    override fun visitIfExpression(ctx: CASCParser.IfExpressionContext): Expression<*> {
        val condition = ctx.condition!!.accept(ev)
        val trueExpression = ctx.trueExpression!!.accept(ev)
        val falseExpression = ctx.falseExpression!!.accept(ev)

        if (!condition.isBool())
            throw IllegalArgumentException("Cannot convert ${condition.type} into bool.")

        return if (condition is Value) {
            if (condition.value == "true") trueExpression
            else falseExpression
        } else IfExpression(condition, trueExpression, falseExpression)
    }
}