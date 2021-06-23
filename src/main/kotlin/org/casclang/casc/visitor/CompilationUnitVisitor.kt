package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.CompilationUnit
import org.casclang.casc.parsing.scope.ClassUsage
import org.casclang.casc.parsing.scope.PathUsage
import org.casclang.casc.parsing.scope.Usage
import org.casclang.casc.util.addError
import org.casclang.casc.visitor.util.QualifiedNameVisitor
import org.casclang.casc.visitor.util.UseReferenceVisitor

class CompilationUnitVisitor : CASCBaseVisitor<CompilationUnit>() {
    private val urv = UseReferenceVisitor()

    override fun visitCompilationUnit(ctx: CASCParser.CompilationUnitContext): CompilationUnit {
        val modulePath = ctx.findModuleDeclaraion()?.findQualifiedName()?.accept(QualifiedNameVisitor)
        val usages = validateUsages(ctx.findUseReference())
        val cv = ClassVisitor(modulePath, usages)
        val classDeclaration = ctx.findClassDeclaration()!!.accept(cv)

        return CompilationUnit(modulePath?.qualifiedName, classDeclaration)
    }

    private fun validateUsages(usageCtxList: List<CASCParser.UseReferenceContext>): List<Usage> {
        val usages = usageCtxList.map {
            it.accept(urv)
        }
        val usageRef = mutableSetOf<String>()

        for ((i, usage) in usages.withIndex()) {
            when (usage) {
                is PathUsage -> {
                    val referencablePath = usage.qualifiedPath.split('.').last()

                    if (usageRef.contains(referencablePath))
                        addError(usageCtxList[i].findQualifiedName(), "Ambiguous usage. Cannot have $referencablePath in same scope.")

                    usageRef += referencablePath
                }
                is ClassUsage -> {
                    val referencableClassName = usage.className

                    if (usageRef.contains(referencableClassName))
                        addError(usageCtxList[i].findQualifiedName(), "Ambiguous usage. Cannot have $referencableClassName in same scope.")

                    usageRef += referencableClassName
                }
            }
        }

        return usages
    }
}