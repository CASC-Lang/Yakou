package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.`class`.Constructor
import io.github.chaosunity.casc.parsing.`class`.Function
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.Statement

class FunctionVisitor(scope: Scope) : CASCBaseVisitor<Function>() {
    private val scope = Scope(scope)

    override fun visitFunction(ctx: CASCParser.FunctionContext?): Function {
        val signature = scope.getMethodCallSignature(ctx?.functionDeclaration()?.functionName()?.text)
        scope.addLocalVariable(LocalVariable(scope.classType(), "this"))

        addParametersAsLocalVariable(signature)

        val block = getBlock(ctx)

        return Function(signature, block)
    }

    override fun visitConstructor(ctx: CASCParser.ConstructorContext?): Function {
        val signature = scope.getMethodCallSignature((ctx?.parent?.parent as CASCParser.ClassDeclarationContext?)?.className()?.text)
        scope.addLocalVariable(LocalVariable(scope.classType(), "this"))

        addParametersAsLocalVariable(signature)

        val block = getBlock(ctx)

        return Constructor(signature, block)
    }

    private fun addParametersAsLocalVariable(signature: FunctionSignature) =
        signature.parameters().forEach { scope.addLocalVariable(LocalVariable(it.type(), it.name())) }

    private fun getBlock(ctx: CASCParser.FunctionContext?): Statement? {
        val statementVisitor = StatementVisitor(scope)
        val block = ctx?.block()

        return block?.accept(statementVisitor)
    }

    private fun getBlock(ctx: CASCParser.ConstructorContext?): Statement? {
        val statementVisitor = StatementVisitor(scope)
        val block = ctx?.block()

        return block?.accept(statementVisitor)
    }
}