package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.ArrayInitialization

class ArrayInitializationVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<ArrayInitialization>() {
    override fun visitArrayInitialization(ctx: CASCParser.ArrayInitializationContext): ArrayInitialization {
        val expressionsCtx = ctx.findExpression()

        if (expressionsCtx.isEmpty())
            throw RuntimeException("Cannot initialize array without any contents.")

        val expressions = expressionsCtx.map { it.accept(ev) }

        return ArrayInitialization(expressions)
    }
}