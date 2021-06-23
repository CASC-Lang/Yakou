package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.ArrayInitialization
import org.casclang.casc.parsing.node.expression.EmptyExpression
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.util.addError

class ArrayInitializationVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<ArrayInitialization>() {
    override fun visitArrayInitialization(ctx: CASCParser.ArrayInitializationContext): ArrayInitialization {
        val expressionsCtx = ctx.findExpression()

        if (expressionsCtx.isEmpty()) {
            addError(ctx, "Cannot initialize array without any contents.")
            EmptyExpression(BuiltInType.VOID)
        }

        val expressions = expressionsCtx.map { it.accept(ev) }

        return ArrayInitialization(expressions)
    }
}