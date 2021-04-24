package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.global.ClassDeclaration
import io.github.chaosunity.casc.parsing.global.Metadata
import io.github.chaosunity.casc.parsing.scope.Scope

class ClassVisitor : CASCBaseVisitor<ClassDeclaration>() {
    private lateinit var scope: Scope

    override fun visitClassDeclaration(ctx: CASCParser.ClassDeclarationContext?): ClassDeclaration {
        val name = ctx?.className()?.text
        val functionSignatureVisitor = FunctionSignatureVisitor()
        val methodsCtx = ctx?.classBody()?.function()
        val metadata = Metadata(ctx?.className()?.text)

        scope = Scope(metadata)

        methodsCtx?.map {
            it.functionDeclaration().accept(functionSignatureVisitor)
        }?.forEach(scope::addSignature)

        val methods = methodsCtx?.map {
            it.accept(FunctionVisitor(scope))
        }

        return ClassDeclaration(name, methods)
    }
}