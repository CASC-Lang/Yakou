package org.casclang.casc.visitor.util

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.scope.ClassUsage
import org.casclang.casc.parsing.scope.PathUsage
import org.casclang.casc.parsing.scope.Usage
import org.casclang.casc.parsing.type.ClassType

class UseReferenceVisitor : CASCBaseVisitor<Usage>() {
    override fun visitUseReference(ctx: CASCParser.UseReferenceContext): Usage =
        ctx.let {
            val qualifiedName = it.findQualifiedName()?.accept(QualifiedNameVisitor)
                ?: throw IllegalArgumentException("No qualified name provided.")
            // We firstly try the qualified path as class path
            try {
                val classType = ClassType(qualifiedName.qualifiedName)

                classType.classType() // We try to initialize a class to check if it is existed.

                ClassUsage(qualifiedName.qualifiedPath, qualifiedName.reference)
            } catch (e: ClassNotFoundException) {
                // If it is not a exist class, we assume it is just a qualified package path.

                PathUsage(qualifiedName.qualifiedName)
            }
        }
}