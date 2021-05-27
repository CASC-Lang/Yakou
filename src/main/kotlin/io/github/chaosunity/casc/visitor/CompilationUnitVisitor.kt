package io.github.chaosunity.casc.visitor

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.CompilationUnit
import io.github.chaosunity.casc.visitor.util.QualifiedNameVisitor

class CompilationUnitVisitor : CASCBaseVisitor<CompilationUnit>() {
    override fun visitCompilationUnit(ctx: CASCParser.CompilationUnitContext): CompilationUnit {
        val cv = ClassVisitor()
        val packagePath = ctx.findModuleDeclaraion()?.findQualifiedName()?.accept(QualifiedNameVisitor())
        val classDeclaration = ctx.findClassDeclaration()!!.accept(cv)

        return CompilationUnit(packagePath, classDeclaration)
    }
}