package org.casclang.casc.visitor.expression.function

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.Argument
import org.casclang.casc.visitor.expression.ExpressionVisitor

class ArgumentVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Argument>() {
    override fun visitArgument(ctx: CASCParser.ArgumentContext): Argument {
        if (ctx.findName() != null) {
            return Argument(ctx.findName()?.text, ctx.findExpression()!!.accept(ev))
        }

        return Argument(null, ctx.findExpression()!!.accept(ev))
    }
}