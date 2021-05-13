package io.github.chaosunity.casc.visitor

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.scope.AccessModifier
import io.github.chaosunity.casc.parsing.scope.Field
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.util.TypeResolver

class FieldDeclarationVisitor(private val scope: Scope) : CASCBaseVisitor<List<Field>>() {
    override fun visitFieldDeclaration(ctx: CASCParser.FieldDeclarationContext): List<Field> {
        val accessModifier = AccessModifier.valueOf(ctx.findAccessMods()!!.text.toUpperCase())
        val finalized = ctx.MUT() == null

        return ctx.findField().map {
            Field(
                accessModifier,
                finalized,
                scope.classType,
                it.findName()!!.text,
                TypeResolver.getFromTypeContext(it.findType())
            )
        }
    }
}