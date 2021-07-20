package org.casclang.casc.visitor

import com.google.common.reflect.ClassPath
import org.antlr.v4.kotlinruntime.tree.TerminalNode
import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.compilation.PackageTree
import org.casclang.casc.parsing.CompilationUnit
import org.casclang.casc.parsing.MetaData
import org.casclang.casc.parsing.scope.Reference
import org.casclang.casc.parsing.scope.Scope
import org.casclang.casc.util.addError
import org.casclang.casc.visitor.util.UseReferenceVisitor
import java.net.URLClassLoader

class CompilationUnitVisitor : CASCBaseVisitor<CompilationUnit>() {
    private lateinit var scope: Scope
    private val urv = UseReferenceVisitor()

    override fun visitCompilationUnit(ctx: CASCParser.CompilationUnitContext): CompilationUnit {
        val simpleClazzName = ctx.findClassDeclaration()!!.findClassName()!!.text
        val modulePath = ctx.findModuleDeclaraion()?.findQualifiedName()?.text?.replace("::", ".")
        val usages = getJavaLangClasses() + ctx.findUseReference()
            .map { it.accept(urv) }
            .flatten()

        val clazzName = if (modulePath != null) "$modulePath.$simpleClazzName" else simpleClazzName
        val metadata = MetaData(clazzName, findSuperClass(ctx, usages))

        scope = Scope(metadata, usages)

        val cv = ClassVisitor(scope, ctx.findImplDeclaration())
        val classDeclaration = ctx.findClassDeclaration()!!.accept(cv)
        val compilationUnit = CompilationUnit(modulePath, classDeclaration)

        if (!PackageTree.classes.containsKey(compilationUnit.qualifiedClassName))
            addError(ctx.findModuleDeclaraion()!!.findQualifiedName(), "Module (or class) name mismatched.")

        return compilationUnit
    }

    private fun getJavaLangClasses(): MutableList<Reference> =
        ClassPath.from(URLClassLoader.getSystemClassLoader()).getTopLevelClasses("java.lang")
            .map(ClassPath.ClassInfo::getName)
            .map(::Reference)
            .toMutableList()

    private fun findSuperClass(ctx: CASCParser.CompilationUnitContext, usages: List<Reference>): String {
        val impls = ctx.findImplDeclaration().filter { it.superCtor != null }

        if (impls.size > 1)
            addError(ctx.findClassDeclaration(), "Cannot inherit two superclasses. Found superclasses: $")

        val name = impls.firstOrNull()?.findQualifiedName()

        return if (name == null) {
            "java.lang.Object"
        } else {
            if (name.ID().size > 1) {
                name.ID()
                    .map(TerminalNode::text)
                    .reduce { a, b -> "$a.$b" }
            } else {
                usages.find { it.localName == name.text }?.fullPath ?: run {
                    addError(name, "Unresolved reference: ${name.text}")
                    ""
                }
            }
        }
    }
}