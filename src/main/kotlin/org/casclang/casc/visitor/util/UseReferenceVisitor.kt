package org.casclang.casc.visitor.util

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.scope.Reference
import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.util.addError

class UseReferenceVisitor : CASCBaseVisitor<List<Reference>>() {
    override fun visitUseReference(ctx: CASCParser.UseReferenceContext): List<Reference> {
        val references = ctx.findReference()!!.accept(this)

        references.forEach {
            val clazzType = ClassType(it.fullPath)

            if (clazzType.isCached())
                clazzType.tryInitClass()

            if (!clazzType.isClassExists())
                addError(ctx.findReference(), "Unresolved class reference: ${it.fullPath}")
        }

        return references
    }

    override fun visitSimpleReference(ctx: CASCParser.SimpleReferenceContext): List<Reference> =
        listOf(Reference(ctx.findQualifiedName()!!.text, ctx.ID()?.text ?: ""))

    override fun visitMultiReference(ctx: CASCParser.MultiReferenceContext): List<Reference> =
        ctx.findReference()
            .map { it.accept(this) }
            .flatten()
            .map {
                it.fullPath = "${ctx.findQualifiedName()!!.text}.${it.fullPath}"
                it
            }
}