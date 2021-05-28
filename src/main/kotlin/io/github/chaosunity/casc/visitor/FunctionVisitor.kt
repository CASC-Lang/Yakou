package io.github.chaosunity.casc.visitor

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.Constructor
import io.github.chaosunity.casc.parsing.Function
import io.github.chaosunity.casc.parsing.node.statement.Block
import io.github.chaosunity.casc.parsing.scope.*
import io.github.chaosunity.casc.visitor.statement.BlockVisitor

class FunctionVisitor(scope: Scope) : CASCBaseVisitor<Function<*>>() {
    private val scope = Scope(scope)

    override fun visitFunction(ctx: CASCParser.FunctionContext): Function<*> {
        val accessModifier = AccessModifier.getModifier(ctx.findFunctionDeclaration()?.findInnerAccessMods()?.text)
        val static = ctx.findFunctionDeclaration()?.COMP() != null
        val signature = ctx.findFunctionDeclaration()!!.accept(FunctionSignatureVisitor(scope))

        scope.callingScope = CallingScope.getScope(ctx)
        if (scope.callingScope == CallingScope.OBJECT) scope.addLocalVariable(LocalVariable("self", scope.classType))
        addParameterAsLocalVariable(signature)

        scope.concealNonStaticFields()

        val block = getBlock(ctx.findBlock()!!)

        scope.revealNonStaticFields()

        return Function<Function<*>>(signature, block, accessModifier, static)
    }

    override fun visitConstructor(ctx: CASCParser.ConstructorContext): Function<*> {
        val accessModifier = AccessModifier.getModifier(ctx.findConstructorDeclaration()?.findInnerAccessMods()?.text)
        val signature = ctx.findConstructorDeclaration()!!.accept(FunctionSignatureVisitor(scope))

        scope.callingScope = CallingScope.getScope(ctx)
        scope.addLocalVariable(LocalVariable("self", scope.classType))
        addParameterAsLocalVariable(signature)

        val blockCtx = ctx.findBlock()
        val block = if (blockCtx != null) getBlock(blockCtx) else Block(scope)

        return Constructor(signature, block, accessModifier)
    }

    private fun addParameterAsLocalVariable(signature: FunctionSignature) {
        signature.parameters.forEach { scope.addLocalVariable(LocalVariable(it.name, it.type)) }
    }

    private fun getBlock(block: CASCParser.BlockContext): Block =
        block.accept(BlockVisitor(scope))
}