package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.`class`.Function
import io.github.chaosunity.casc.parsing.expression.FunctionParameter
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.Statement
import io.github.chaosunity.casc.parsing.type.Type
import io.github.chaosunity.casc.util.TypeResolver

class FunctionVisitor(scope: Scope) : CASCBaseVisitor<Function>() {
    private val scope = Scope(scope)

    override fun visitFunction(ctx: CASCParser.FunctionContext?): Function {
        val functionName = getName(ctx)
        val returnType = getReturnType(ctx)
        val arguments = getArguments(ctx)
        val instructions = getStatements(ctx)

        return Function(scope, functionName, returnType, arguments, instructions)
    }

    private fun getName(ctx: CASCParser.FunctionContext?): String? =
        ctx?.functionDeclaration()?.functionName()?.text

    private fun getReturnType(ctx: CASCParser.FunctionContext?): Type? {
        val typeCtx = ctx?.functionDeclaration()?.type()
        return TypeResolver.getFromTypeName(typeCtx)
    }

    private fun getArguments(ctx: CASCParser.FunctionContext?): List<FunctionParameter> {
        val argsCtx = ctx?.functionDeclaration()?.functionArgument() ?: listOf()
        return argsCtx.map {
            FunctionParameter(TypeResolver.getFromTypeName(it.type()), it.ID().text)
        }.onEach {
            scope.addLocalVariable(LocalVariable(it.type(), it.name()))
        }
    }

    private fun getStatements(ctx: CASCParser.FunctionContext?): List<Statement> {
        val statementVisitor = StatementVisitor(scope)
        val expressionVisitor = ExpressionVisitor(scope)
        val compositeVisitor = CompositeVisitor(statementVisitor, expressionVisitor)

        return ctx?.blockStatement()?.mapNotNull(compositeVisitor::accept) ?: listOf()
    }
}