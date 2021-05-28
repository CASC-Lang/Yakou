package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.scope.AccessModifier
import org.casclang.casc.parsing.scope.Field
import org.casclang.casc.parsing.scope.Scope
import org.casclang.casc.util.TypeResolver

class FieldVisitor(private val scope: Scope) : CASCBaseVisitor<Field>() {
    override fun visitField(ctx: CASCParser.FieldContext): Field {
        val accessModifier = AccessModifier.getModifier(ctx.findInnerAccessMods()?.text)
        val static = ctx.COMP() != null
        val finalized = ctx.MUT() == null

        return Field(
            finalized,
            scope.classType,
            ctx.findName()!!.text,
            TypeResolver.getFromTypeReferenceContext(ctx.findTypeReference()),
            accessModifier,
            static = static
        )
    }
}