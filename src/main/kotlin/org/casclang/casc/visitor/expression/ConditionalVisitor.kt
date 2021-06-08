package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.LogicalOp
import org.casclang.casc.parsing.node.expression.Conditional
import org.casclang.casc.parsing.node.expression.Value
import org.casclang.casc.parsing.type.BuiltInType

class ConditionalVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Conditional>() {
    override fun visitConditionalExpression(ctx: CASCParser.ConditionalExpressionContext): Conditional {
        val leftExpression = ctx.findExpression(0)!!.accept(ev)
        val rightExpression = ctx.findExpression(1)?.accept(ev)
        val cmp = ctx.cmp
        val logicalOp = if (cmp != null) {
            LogicalOp.fromString(cmp.text)
        } else {
            LogicalOp.NOT_EQ
        }

        return Conditional(leftExpression, rightExpression, logicalOp)
    }
}