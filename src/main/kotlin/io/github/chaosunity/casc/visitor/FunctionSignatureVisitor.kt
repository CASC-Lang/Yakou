package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.expression.Expression
import io.github.chaosunity.casc.parsing.expression.FunctionParameter
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.util.TypeResolver
import scala.Option

class FunctionSignatureVisitor(private val scope: Scope) : CASCBaseVisitor<FunctionSignature>() {
    private val ev = ExpressionVisitor(scope)

    override fun visitFunctionDeclaration(ctx: CASCParser.FunctionDeclarationContext?): FunctionSignature {
        val funcName = ctx?.functionName()?.text
        val args = ctx?.functionParameter() ?: listOf()
        val params = mutableListOf<FunctionParameter>()

        for (arg in args) {
            val name = arg.ID().text
            val type = TypeResolver.getFromTypeName(arg.type())
            val defaultValue = getParameterDefaultValue(arg)
            val parameter = FunctionParameter(type, name, Option.apply(defaultValue), false)

            params += parameter
        }

        val retType = TypeResolver.getFromTypeName(ctx?.type())

        return FunctionSignature(funcName, params, retType)
    }

    override fun visitConstructorDeclaration(ctx: CASCParser.ConstructorDeclarationContext?): FunctionSignature {
        val ctorName = (ctx?.parent?.parent?.parent as CASCParser.ClassDeclarationContext?)?.className()?.text
        val args = ctx?.functionParameter() ?: listOf()
        val params = mutableListOf<FunctionParameter>()

        for (arg in args) {
            val name = arg.ID().text
            val type = TypeResolver.getFromTypeName(arg.type())
            val defaultValue = getParameterDefaultValue(arg)
            val parameter = FunctionParameter(type, name, Option.apply(defaultValue), false)

            params += parameter
        }

        return FunctionSignature(ctorName, params, BuiltInType.VOID())
    }

    private fun getParameterDefaultValue(ctx: CASCParser.FunctionParameterContext?): Expression? =
        if (ctx?.functionParameterDefaultValue() != null) ctx.functionParameterDefaultValue()?.expression()?.accept(ev)
        else null
}