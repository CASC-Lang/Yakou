package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.ArrayInitialization

class ArrayInitializationVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<ArrayInitialization>() {
    override fun visitArrayInitialization(ctx: CASCParser.ArrayInitializationContext): ArrayInitialization {
        val expressionsCtx = ctx.findExpression()

        if (expressionsCtx.isEmpty())
            throw RuntimeException("Cannot initialize array without any contents.")

        val expressions = expressionsCtx.map { it.accept(ev) }

        return ArrayInitialization(expressions)
    }
}