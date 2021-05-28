package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.scope.AccessModifier
import org.casclang.casc.parsing.scope.Field
import org.casclang.casc.parsing.scope.Scope
import org.casclang.casc.util.TypeResolver

class FieldDeclarationVisitor(private val scope: Scope) :
    CASCBaseVisitor<List<Field>>() {
    override fun visitFieldDeclaration(ctx: CASCParser.FieldDeclarationContext): List<Field> {
        val accessModifier = AccessModifier.getModifier(ctx.findInnerAccessMods()!!.text)
        val static = ctx.COMP() != null
        val finalized = ctx.MUT() == null

        return ctx.findField().map {
            if ((ctx.findInnerAccessMods() != null && it.findInnerAccessMods() != null && accessModifier != AccessModifier.getModifier(
                    it.findInnerAccessMods()!!.text
                ))
            ) {
                throw RuntimeException("Field '${it.findName()?.text}' has different access modifier to its parent declaration.")
            } else {
                Field(
                    finalized && it.MUT() == null,
                    scope.classType,
                    it.findName()!!.text,
                    TypeResolver.getFromTypeReferenceContext(it.findTypeReference()),
                    accessModifier,
                    static = static
                )
            }
        }
    }
}