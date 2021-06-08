package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.LogicalOp
import org.casclang.casc.parsing.node.expression.Conditional
import org.casclang.casc.parsing.node.expression.Expression
import org.casclang.casc.parsing.node.expression.Value
import org.casclang.casc.parsing.type.BuiltInType
import java.math.BigDecimal

class ConditionalVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Expression<*>>() {
    override fun visitConditionalExpression(ctx: CASCParser.ConditionalExpressionContext): Expression<*> {
        val leftExpression = ctx.findExpression(0)!!.accept(ev)
        val rightExpression = ctx.findExpression(1)!!.accept(ev)
        val cmp = ctx.cmp
        val logicalOp = if (cmp != null) {
            LogicalOp.fromString(cmp.text)
        } else {
            LogicalOp.NOT_EQ
        }

        return if (leftExpression is Value && rightExpression is Value) {
            val (v1, v2) = leftExpression.evaluate() to rightExpression.evaluate()

            val evaluatedValue = when (logicalOp) {
                LogicalOp.EQ -> v1 == v2
                LogicalOp.NOT_EQ -> v1 != v2
                LogicalOp.LESS -> BigDecimal(v1.toString()) < BigDecimal(v2.toString())
                LogicalOp.GREATER -> BigDecimal(v1.toString()) > BigDecimal(v2.toString())
                LogicalOp.LESS_EQ -> BigDecimal(v1.toString()) <= BigDecimal(v2.toString())
                LogicalOp.GREATER_EQ -> BigDecimal(v1.toString()) >= BigDecimal(v2.toString())
            }

            Value(BuiltInType.BOOLEAN, "$evaluatedValue")
        } else Conditional(leftExpression, rightExpression, logicalOp)
    }
}