package io.github.chaosunity.casc.visitor

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.scope.AccessModifier
import io.github.chaosunity.casc.parsing.scope.Field
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.util.TypeResolver

class FieldDeclarationVisitor(private val scope: Scope) : CASCBaseVisitor<List<Field>>() {
    override fun visitFieldDeclaration(ctx: CASCParser.FieldDeclarationContext): List<Field> {
        val accessModifier = AccessModifier.getModifier(ctx.findInnerAccessMods()!!.text)
        val static = ctx.COMP() != null
        val finalized = ctx.MUT() == null

        return ctx.findField().map {
            if ((ctx.findInnerAccessMods() != null && it.findInnerAccessMods() != null && accessModifier != AccessModifier.getModifier(it.findInnerAccessMods()!!.text))) {
                throw RuntimeException("Field '${it.findName()?.text}' has different access modifier to its parent declaration.")
            } else {
                Field(
                    finalized && it.MUT() == null,
                    scope.classType,
                    it.findName()!!.text,
                    TypeResolver.getFromTypeReferenceContext(it.findTypeReference()),
                    accessModifier,
                    static
                )
            }
        }
    }
}