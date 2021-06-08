package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.CompilationUnit
import org.casclang.casc.parsing.scope.ClassUsage
import org.casclang.casc.parsing.scope.PathUsage
import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.visitor.util.QualifiedNameVisitor
import org.casclang.casc.visitor.util.UseReferenceVisitor

class CompilationUnitVisitor : CASCBaseVisitor<CompilationUnit>() {
    private val urv = UseReferenceVisitor()

    override fun visitCompilationUnit(ctx: CASCParser.CompilationUnitContext): CompilationUnit {
        val packagePath = ctx.findModuleDeclaraion()?.findQualifiedName()?.accept(QualifiedNameVisitor)
        val usages = ctx.findUseReference().map {
            it.accept(urv)
        }
        val cv = ClassVisitor(usages)
        val classDeclaration = ctx.findClassDeclaration()!!.accept(cv)

        println(ctx.position?.start?.column)
        println(ctx.position?.end?.column)

        return CompilationUnit(packagePath?.qualifiedName, classDeclaration)
    }
}