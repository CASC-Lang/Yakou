package org.casclang.casc.visitor.util

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.scope.QualifiedName

object QualifiedNameVisitor : CASCBaseVisitor<QualifiedName>() {
    override fun visitQualifiedName(ctx: CASCParser.QualifiedNameContext): QualifiedName {
        val qualifiedName = ctx.text.replace("::", ".")

        return if (qualifiedName.contains('.')) {
            val lastDotIndex = qualifiedName.lastIndexOf('.')
            val qualifiedPath = qualifiedName.substring(0 until lastDotIndex)
            val reference = qualifiedName.substring(lastDotIndex + 1..qualifiedName.lastIndex)

            QualifiedName(qualifiedPath, reference)
        } else {
            QualifiedName("", qualifiedName)
        }
    }
}