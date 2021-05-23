package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.FieldReference
import io.github.chaosunity.casc.parsing.node.expression.LocalVariableReference
import io.github.chaosunity.casc.parsing.node.expression.Reference
import io.github.chaosunity.casc.parsing.scope.Scope

class VariableReferenceVisitor(private val ev: ExpressionVisitor, private val scope: Scope) :
    CASCBaseVisitor<Reference<*>>() {
    override fun visitVarRef(ctx: CASCParser.VarRefContext): Reference<*> =
        getReference(ctx.text)

    override fun visitVarReference(ctx: CASCParser.VarReferenceContext): Reference<*> =
        getReference(ctx.text)

    override fun visitArrayReference(ctx: CASCParser.ArrayReferenceContext): Reference<*> =
        getReference(ctx.text, ctx.findExpression())

    internal fun getReference(name: String, dimensionsCtx: List<CASCParser.ExpressionContext> = listOf()): Reference<*> {
        val dimensions = dimensionsCtx.map {
            it.accept(ev)
        }.onEach {
            if (!it.type.isInt() && !it.type.isLong())
                throw RuntimeException("Cannot index an array with type ${it.type}.")
        }

        return if (scope.isFieldExists(name)) FieldReference(scope.getField(name), dimensions)
        else LocalVariableReference(scope.getLocalVariable(name), dimensions)
    }

}