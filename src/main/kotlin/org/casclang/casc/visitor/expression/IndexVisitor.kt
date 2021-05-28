package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.Index

class IndexVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Index>() {
    override fun visitIndexEpxression(ctx: CASCParser.IndexEpxressionContext): Index {
        val expression = ctx.referenceExpression!!.accept(ev)
        val indexExpression = ctx.indexExpression!!.accept(ev)

        return Index(expression, indexExpression)
    }
}