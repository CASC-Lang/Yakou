package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.CompilationUnit
import org.casclang.casc.parsing.scope.ClassUsage
import org.casclang.casc.parsing.scope.PathUsage
import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.visitor.util.QualifiedNameVisitor

class CompilationUnitVisitor : CASCBaseVisitor<CompilationUnit>() {
    override fun visitCompilationUnit(ctx: CASCParser.CompilationUnitContext): CompilationUnit {
        val packagePath = ctx.findModuleDeclaraion()?.findQualifiedName()?.accept(QualifiedNameVisitor)
        val usages = ctx.findUseReference().mapNotNull {
            it.findQualifiedName()?.accept(QualifiedNameVisitor)
        }.map {
            // We firstly try the qualified path as class path
            try {
                val classType = ClassType(it.qualifiedName)

                classType.classType() // We try to initialize a class to check if it is existed.

                ClassUsage(it.qualifiedPath, it.reference)
            } catch (e: ClassNotFoundException) {
                // If it is not a exist class, we assume it is just a qualified package path.

                PathUsage(it.qualifiedName)
            }
        }
        val cv = ClassVisitor(usages)
        val classDeclaration = ctx.findClassDeclaration()!!.accept(cv)

        return CompilationUnit(packagePath?.qualifiedName, classDeclaration)
    }
}