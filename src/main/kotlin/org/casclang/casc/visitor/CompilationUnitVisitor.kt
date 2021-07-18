package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.compilation.PackageTree
import org.casclang.casc.parsing.CompilationUnit
import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.util.addError
import org.casclang.casc.visitor.util.UseReferenceVisitor

class CompilationUnitVisitor : CASCBaseVisitor<CompilationUnit>() {
    private val urv = UseReferenceVisitor()

    override fun visitCompilationUnit(ctx: CASCParser.CompilationUnitContext): CompilationUnit {
        val modulePath = ctx.findModuleDeclaraion()?.findQualifiedName()?.text?.replace("::", ".")
        val usages = ctx.findUseReference()
            .map { it.accept(urv) }
            .flatten()
        val cv = ClassVisitor(modulePath, usages)
        val classDeclaration = ctx.findClassDeclaration()!!.accept(cv)
        val compilationUnit = CompilationUnit(modulePath, classDeclaration)

        if (!PackageTree.classes.containsKey(compilationUnit.qualifiedClassName))
            addError(ctx.findModuleDeclaraion()!!.findQualifiedName(), "Module (or class) name mismatched.")

        return compilationUnit
    }
}