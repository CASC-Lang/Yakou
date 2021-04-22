package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.expression.FunctionParameter
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.util.TypeResolver

class FunctionSignatureVisitor : CASCBaseVisitor<FunctionSignature>() {
    override fun visitFunctionDeclaration(ctx: CASCParser.FunctionDeclarationContext?): FunctionSignature {
        val funcName = ctx?.functionName()?.text
        val args = ctx?.functionArgument() ?: listOf()
        val params = mutableListOf<FunctionParameter>()

        for (arg in args) {
            val name = arg.ID().text
            val type = TypeResolver.getFromTypeName(arg.type())
            val param = FunctionParameter(type, name)

            params += param
        }

        val retType = TypeResolver.getFromTypeName(ctx?.type())

        return FunctionSignature(funcName, params, retType)
    }
}