package io.github.chaosunity.casc.visitor

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.CompilationUnit
import io.github.chaosunity.casc.parsing.scope.ClassUsage
import io.github.chaosunity.casc.parsing.scope.PathUsage
import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.visitor.util.QualifiedNameVisitor

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