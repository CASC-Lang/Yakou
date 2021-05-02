package io.github.chaosunity.casc.visitor

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.Function
import io.github.chaosunity.casc.parsing.node.statement.Block
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.visitor.statement.BlockVisitor

class FunctionVisitor(scope: Scope) : CASCBaseVisitor<Function<*>>() {
    private val scope = Scope(scope)

    override fun visitFunction(ctx: CASCParser.FunctionContext): Function<*> {
        val signature = ctx.findFunctionDeclaration()!!.accept(FunctionSignatureVisitor(scope))

        scope.addLocalVariable(LocalVariable("this", scope.classType))

        val block = getBlock(ctx.findBlock()!!)

        return Function<Function<*>>(signature, block)
    }

    override fun visitConstructor(ctx: CASCParser.ConstructorContext): Function<*> {
        val signature = ctx.findConstructorDeclaration()!!.accept(FunctionSignatureVisitor(scope))

        scope.addLocalVariable(LocalVariable("this", scope.classType))

        val blockCtx = ctx.findBlock()
        val block = if (blockCtx != null) getBlock(blockCtx) else Block(scope)

        return Function<Function<*>>(signature, block)
    }

    private fun addParameterAsLocalVariable(signature: FunctionSignature) {
        signature.parameters.forEach { scope.addLocalVariable(LocalVariable(it.name, it.type)) }
    }

    private fun getBlock(block: CASCParser.BlockContext): Block =
        block.accept(BlockVisitor(scope))
}