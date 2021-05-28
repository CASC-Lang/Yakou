package org.casclang.casc.visitor.expression.function

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.Parameter
import org.casclang.casc.util.TypeResolver
import org.casclang.casc.visitor.expression.ExpressionVisitor

class ParameterVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Parameter>() {
    override fun visitParameter(ctx: CASCParser.ParameterContext): Parameter {
        val name = ctx.ID()!!.text
        val type = TypeResolver.getFromTypeReferenceContext(ctx.findTypeReference())
        val defaultValue = ctx.findExpression()?.accept(ev)

        return Parameter(type, name, defaultValue)
    }
}