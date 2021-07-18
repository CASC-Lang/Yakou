package org.casclang.casc.visitor.util

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.scope.Usage
import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.util.addError

class UseReferenceVisitor : CASCBaseVisitor<Usage?>() {
    override fun visitUseReference(ctx: CASCParser.UseReferenceContext): Usage? =
        ctx.let {
            val qualifiedName = it.findQualifiedName()?.accept(QualifiedNameVisitor)!!

            val classType = ClassType(qualifiedName.qualifiedName)

            if (classType.isCached())
                classType.tryInitClass()

            if (classType.isClassExists()) {
                Usage(qualifiedName.qualifiedName)
            } else {
                addError(ctx.findQualifiedName(), "Unresolved class reference: ${qualifiedName.qualifiedName}")

                null
            }
        }
}