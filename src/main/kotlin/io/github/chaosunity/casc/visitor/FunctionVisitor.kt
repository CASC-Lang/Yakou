package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.bytecodegen.StatementFactory
import io.github.chaosunity.casc.parsing.`class`.Function
import io.github.chaosunity.casc.parsing.expression.FunctionParameter
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.BlockStatement
import io.github.chaosunity.casc.parsing.statement.Statement
import io.github.chaosunity.casc.parsing.type.Type
import io.github.chaosunity.casc.util.TypeResolver

class FunctionVisitor(scope: Scope) : CASCBaseVisitor<Function>() {
    private val scope = Scope(scope)

    override fun visitFunction(ctx: CASCParser.FunctionContext?): Function {
        val signature = scope.getSignature(ctx?.functionDeclaration()?.functionName()?.text)

        addParametersAsLocalVariable(signature)

        val block = getBlock(ctx)

        return Function(signature, block)
    }

    private fun addParametersAsLocalVariable(signature: FunctionSignature) =
        signature.parameters().forEach { scope.addLocalVariable(LocalVariable(it.type(), it.name())) }

    private fun getBlock(ctx: CASCParser.FunctionContext?): Statement? {
        val statementVisitor = StatementVisitor(scope)
        val block = ctx?.block()

        return block?.accept(statementVisitor)
    }
}