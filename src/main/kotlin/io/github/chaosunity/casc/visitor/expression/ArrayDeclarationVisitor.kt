package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.ArrayDeclaration
import io.github.chaosunity.casc.util.TypeResolver

class ArrayDeclarationVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<ArrayDeclaration>() {
    override fun visitArrayDeclaration(ctx: CASCParser.ArrayDeclarationContext): ArrayDeclaration {
        val type = TypeResolver.getFromTypeContext(ctx.findType())
        val expressions = ctx.findExpression().map {
            it.accept(ev)
        }.onEach {
            if (!it.type.isInt() && !it.type.isLong())
                throw RuntimeException("Cannot allocate array with type $type.")
        }

        return ArrayDeclaration(type, expressions.size, expressions)
    }
}