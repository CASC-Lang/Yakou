package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.LogicalOp
import org.casclang.casc.parsing.node.expression.*
import org.casclang.casc.parsing.node.statement.IfStatement
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.util.addError

class IfExpressionVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Expression<*>>() {
    override fun visitIfExpression(ctx: CASCParser.IfExpressionContext): Expression<*> {
        val condition = ctx.condition!!.accept(ev)
        val trueExpression = ctx.trueExpression!!.accept(ev)
        val falseExpression = ctx.falseExpression!!.accept(ev)

        if (!condition.isBool()) {
            addError(ctx, "Cannot convert ${condition.type} into bool.")
            return EmptyExpression(BuiltInType.VOID)
        }

        return if (condition is Value) {
            if (condition.value == "true") trueExpression
            else falseExpression
        } else IfExpression(condition, trueExpression, falseExpression)
    }
}