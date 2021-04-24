package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.global.CompilationUnit

class CompilationUnitVisitor : CASCBaseVisitor<CompilationUnit>() {
    override fun visitCompilationUnit(ctx: CASCParser.CompilationUnitContext?): CompilationUnit {
        val cv = ClassVisitor()
        val classDeclarationCtx = ctx?.classDeclaration()
        val classDeclaration = classDeclarationCtx?.accept(cv)

        return CompilationUnit(classDeclaration)
    }
}