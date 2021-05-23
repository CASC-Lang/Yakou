package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.ArrayBridge
import io.github.chaosunity.casc.parsing.node.expression.ArrayDeclaration
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.util.TypeResolver

class ArrayDeclarationVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<ArrayBridge>() {
    override fun visitArrayDeclaration(ctx: CASCParser.ArrayDeclarationContext): ArrayBridge =
        try {
            val type = TypeResolver.getFromTypeContext(ctx.findType())
            val expressions = ctx.findExpression().map {
                it.accept(ev)
            }.onEach {
                if (!it.type.isInt() && !it.type.isLong())
                    throw RuntimeException("Cannot allocate array with type $type.")
            }

            ArrayDeclaration(type, expressions.size, expressions)
        } catch (e: Exception) {
            VariableReferenceVisitor(ev, scope).getReference(ctx.findType()!!.text, ctx.findExpression())
        } // Check type exists, otherwise it's a variable reference.
}