package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.`class`.Constructor
import io.github.chaosunity.casc.parsing.expression.FunctionCall
import io.github.chaosunity.casc.parsing.global.ClassDeclaration
import io.github.chaosunity.casc.parsing.global.Metadata
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.Block
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.ClassType

class ClassVisitor : CASCBaseVisitor<ClassDeclaration>() {
    private lateinit var scope: Scope

    override fun visitClassDeclaration(ctx: CASCParser.ClassDeclarationContext?): ClassDeclaration {
        val name = ctx?.className()?.text
        val metadata = Metadata(ctx?.className()?.text, "java.lang.Object")

        scope = Scope(metadata)

        val constructorExists = ctx?.classBody()?.function()?.any {
            it.functionDeclaration().functionName().text == name
        } ?: false

        if (!constructorExists) {
            val constructorSignature = FunctionSignature(name, listOf(), BuiltInType.VOID())

            scope.addSignature(constructorSignature)
        }

        val functionSignatureVisitor = FunctionSignatureVisitor(scope)
        val methodsCtx = ctx?.classBody()?.function()

        methodsCtx?.map {
            it.functionDeclaration().accept(functionSignatureVisitor)
        }?.forEach(scope::addSignature)

        val methods = methodsCtx?.map {
            it.accept(FunctionVisitor(scope))
        }?.toMutableList() ?: mutableListOf()

        if (!constructorExists) {
            methods += getDefaultConstructor()
        }

        return ClassDeclaration(name, methods)
    }

    private fun getDefaultConstructor(): Constructor {
        val signature = scope.getMethodCallSignature(scope.className())

        return Constructor(signature, Block.empty(scope))
    }
}