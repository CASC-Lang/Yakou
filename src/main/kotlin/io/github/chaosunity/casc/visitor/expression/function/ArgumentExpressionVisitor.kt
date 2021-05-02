package io.github.chaosunity.casc.visitor.expression.function

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.Argument
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor

class ArgumentExpressionVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Argument>() {
    override fun visitArgument(ctx: CASCParser.ArgumentContext): Argument {
        if (ctx.findName() != null) {
            return Argument(ctx.findName()?.text, ctx.findExpression()!!.accept(ev))
        }

        return Argument(null, ctx.findExpression()!!.accept(ev))
    }
}