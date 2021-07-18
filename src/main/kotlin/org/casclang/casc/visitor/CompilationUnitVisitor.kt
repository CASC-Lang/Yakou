package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.CompilationUnit
import org.casclang.casc.visitor.util.QualifiedNameVisitor
import org.casclang.casc.visitor.util.UseReferenceVisitor

class CompilationUnitVisitor : CASCBaseVisitor<CompilationUnit>() {
    private val urv = UseReferenceVisitor()

    override fun visitCompilationUnit(ctx: CASCParser.CompilationUnitContext): CompilationUnit {
        val modulePath = ctx.findModuleDeclaraion()?.findQualifiedName()?.accept(QualifiedNameVisitor)
        val usages = ctx.findUseReference().mapNotNull { it.accept(urv) }
        val cv = ClassVisitor(modulePath, usages)
        val classDeclaration = ctx.findClassDeclaration()!!.accept(cv)

        return CompilationUnit(modulePath?.qualifiedName, classDeclaration)
    }
}