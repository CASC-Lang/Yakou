package io.github.chaosunity.casc.visitor.expression.function

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.Parameter
import io.github.chaosunity.casc.util.TypeResolver
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor

class ParameterVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Parameter>() {
    override fun visitParameter(ctx: CASCParser.ParameterContext): Parameter {
        val name = ctx.ID()!!.text
        val type = TypeResolver.getFromTypeReferenceContext(ctx.findTypeReference())
        val defaultValue = ctx.findExpression()?.accept(ev)

        return Parameter(type, name, defaultValue)
    }
}