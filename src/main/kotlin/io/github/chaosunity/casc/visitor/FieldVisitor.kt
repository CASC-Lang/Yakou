package io.github.chaosunity.casc.visitor

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.scope.AccessModifier
import io.github.chaosunity.casc.parsing.scope.Field
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.util.TypeResolver

class FieldVisitor(private val scope: Scope) : CASCBaseVisitor<Field>() {
    override fun visitField(ctx: CASCParser.FieldContext): Field {
        val accessModifier = AccessModifier.getModifier(ctx.findInnerAccessMods()?.text)
        val static = ctx.COMP() != null
        val finalized = ctx.MUT() == null

        return Field(
                finalized,
                scope.classType,
                ctx.findName()!!.text,
                TypeResolver.getFromTypeContext(ctx.findType()),
                accessModifier,
                static
            )
    }
}