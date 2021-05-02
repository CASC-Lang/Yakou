package io.github.chaosunity.casc.visitor

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.CompilationUnit

class CompilationUnitVisitor : CASCBaseVisitor<CompilationUnit>() {
    override fun visitCompilationUnit(ctx: CASCParser.CompilationUnitContext): CompilationUnit {
        return super.visitCompilationUnit(ctx)
    }
}