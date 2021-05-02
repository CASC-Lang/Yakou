package io.github.chaosunity.casc.visitor

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.util.TypeResolver
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor
import io.github.chaosunity.casc.visitor.expression.function.ParameterVisitor

class FunctionSignatureVisitor(private val scope: Scope) : CASCBaseVisitor<FunctionSignature>() {
    private val ev = ExpressionVisitor(scope)

    override fun visitFunctionDeclaration(ctx: CASCParser.FunctionDeclarationContext): FunctionSignature {
        val functionName = ctx.findFunctionName()!!.text
        val returnType = TypeResolver.getFromTypeName(ctx.findType())
        val parameters = ctx.findParameter().map { it.accept(ParameterVisitor(ev)) }

        return FunctionSignature(functionName, parameters, returnType)
    }

    override fun visitConstructorDeclaration(ctx: CASCParser.ConstructorDeclarationContext): FunctionSignature {
        val parameters = ctx.findParameter().map { it.accept(ParameterVisitor(ev)) }

        return FunctionSignature(scope.className, parameters, BuiltInType.VOID)
    }
}