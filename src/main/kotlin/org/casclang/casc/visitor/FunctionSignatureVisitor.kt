package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.scope.FunctionSignature
import org.casclang.casc.parsing.scope.Scope
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.util.TypeResolver
import org.casclang.casc.visitor.expression.ExpressionVisitor
import org.casclang.casc.visitor.expression.function.ParameterVisitor

class FunctionSignatureVisitor(private val scope: Scope) : CASCBaseVisitor<FunctionSignature>() {
    private val ev = ExpressionVisitor(scope)

    override fun visitFunctionDeclaration(ctx: CASCParser.FunctionDeclarationContext): FunctionSignature {
        val functionName = ctx.findFunctionName()!!.text
        val returnType = TypeResolver.getFromTypeReferenceContext(ctx.findTypeReference())
        val parameters = ctx.findParameterSet()!!.findParameter().map { it.accept(ParameterVisitor(ev)) }
        val static = ctx.COMP() != null

        return FunctionSignature(functionName, parameters, returnType, static)
    }

    override fun visitConstructorDeclaration(ctx: CASCParser.ConstructorDeclarationContext): FunctionSignature {
        val parameters = ctx.findParameterSet()!!.findParameter().map { it.accept(ParameterVisitor(ev)) }

        return FunctionSignature(scope.className, parameters, BuiltInType.VOID, false)
    }
}