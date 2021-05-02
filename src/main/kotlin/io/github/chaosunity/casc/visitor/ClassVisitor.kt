package io.github.chaosunity.casc.visitor

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.ClassDeclaration
import io.github.chaosunity.casc.parsing.Constructor
import io.github.chaosunity.casc.parsing.MetaData
import io.github.chaosunity.casc.parsing.node.statement.Block
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.BuiltInType

class ClassVisitor : CASCBaseVisitor<ClassDeclaration>() {
    private lateinit var scope: Scope

    override fun visitClassDeclaration(ctx: CASCParser.ClassDeclarationContext): ClassDeclaration {
        val name = ctx.findClassName()!!.text
        val methodsCtx = ctx.findClassBody()!!.findFunction()
        val fsv = FunctionSignatureVisitor(scope)
        val metaData = MetaData(name, "java.lang.Object")

        scope = Scope(metaData)
        methodsCtx.map {
            it.findFunctionDeclaration()!!.accept(fsv)
        }.forEach(scope::addSignature)

        val constructorsCtx = ctx.findClassBody()!!.findConstructor()
        val methods = methodsCtx.map {
            it.accept(FunctionVisitor(scope))
        }.toMutableList()

        if (constructorsCtx.isEmpty()) {
            scope.addSignature(FunctionSignature(name, listOf(), BuiltInType.VOID))
            methods += getDefaultConstructor()
        } else {
            constructorsCtx.map {
                it.findConstructorDeclaration()!!.accept(fsv)
            }.forEach(scope::addSignature)
            constructorsCtx.map {
                it.accept(FunctionVisitor(scope))
            }.forEach(methods::add)
        }

        return ClassDeclaration(name, methods)
    }

    private fun getDefaultConstructor(): Constructor =
        Constructor(scope.getMethodCallSignatureWithoutParameters(scope.className), Block(scope))
}