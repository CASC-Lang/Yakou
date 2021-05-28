package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.ArrayDeclaration
import org.casclang.casc.util.TypeResolver

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