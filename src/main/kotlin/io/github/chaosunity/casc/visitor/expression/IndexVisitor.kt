package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.Index

class IndexVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Index>() {
    override fun visitIndexEpxression(ctx: CASCParser.IndexEpxressionContext): Index {
        val expression = ctx.referenceExpression!!.accept(ev)
        val indexExpression = ctx.indexExpression!!.accept(ev)

        return Index(expression, indexExpression)
    }
}