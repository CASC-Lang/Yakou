package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.ArrayDeclaration
import org.casclang.casc.util.TypeResolver
import org.casclang.casc.util.addError

class ArrayDeclarationVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<ArrayDeclaration>() {
    override fun visitArrayDeclaration(ctx: CASCParser.ArrayDeclarationContext): ArrayDeclaration {
        val type = TypeResolver.getFromTypeContext(ctx.findType())
        val expressions = ctx.findExpression().map {
            it.accept(ev)
        }.onEachIndexed { i, it ->
            if (!it.type.isInt() && !it.type.isLong())
                addError(ctx.findExpression(i), "Cannot allocate array with type ${it.type}.")
        }

        return ArrayDeclaration(type, expressions.size, expressions)
    }
}