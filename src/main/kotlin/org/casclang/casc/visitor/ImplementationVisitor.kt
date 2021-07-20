package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.Implementation

class ImplementationVisitor : CASCBaseVisitor<Implementation>() {
    override fun visitImplDeclaration(ctx: CASCParser.ImplDeclarationContext): Implementation {
        return super.visitImplDeclaration(ctx)
    }
}