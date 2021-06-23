package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.EmptyExpression
import org.casclang.casc.parsing.node.expression.Expression
import org.casclang.casc.parsing.node.expression.Index
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.util.addError

class IndexVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Expression<*>>() {
    override fun visitIndexEpxression(ctx: CASCParser.IndexEpxressionContext): Expression<*> {
        val expression = ctx.referenceExpression!!.accept(ev)
        val indexExpression = ctx.indexExpression!!.accept(ev)

        return if (!expression.isInt() || !expression.isLong()) {
            addError(ctx, "Cannot index with type ${expression.type}.")
            EmptyExpression(BuiltInType.VOID)
        } else Index(expression, indexExpression)
    }
}