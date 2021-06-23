package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.Constructor
import org.casclang.casc.parsing.Function
import org.casclang.casc.parsing.node.expression.Argument
import org.casclang.casc.parsing.node.expression.EmptyExpression
import org.casclang.casc.parsing.node.statement.Block
import org.casclang.casc.parsing.node.statement.IfStatement
import org.casclang.casc.parsing.node.statement.ReturnStatement
import org.casclang.casc.parsing.node.statement.Statement
import org.casclang.casc.parsing.scope.*
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.Type
import org.casclang.casc.util.addError
import org.casclang.casc.util.fromContext
import org.casclang.casc.util.prepend
import org.casclang.casc.visitor.expression.ExpressionVisitor
import org.casclang.casc.visitor.expression.function.CallVisitor
import org.casclang.casc.visitor.statement.BlockVisitor

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

        if (signature.returnType != BuiltInType.VOID && !validateAllCodePathsHaveReturn(block, signature.returnType))
            addError(ctx, "Function must return a value of type ${signature.returnType}")

        scope.revealNonStaticFields()

        return Function<Function<*>>(signature, block, accessModifier, static)
    }

    override fun visitConstructor(ctx: CASCParser.ConstructorContext): Function<*> {
        val accessModifier = AccessModifier.getModifier(ctx.findConstructorDeclaration()?.findInnerAccessMods()?.text)
        val signature = ctx.findConstructorDeclaration()!!.accept(FunctionSignatureVisitor(scope))
        val selfCall = CallVisitor(ExpressionVisitor(scope), scope).buildSelfCall(
            ctx.findConstructorDeclaration()!!.findArgument()
        )

        scope.callingScope = CallingScope.getScope(ctx)
        scope.addLocalVariable(LocalVariable("self", scope.classType))
        addParameterAsLocalVariable(signature)

        val blockCtx = ctx.findBlock()
        val block = if (blockCtx != null) getBlock(blockCtx) else Block(scope)

        block.statements.prepend(selfCall)
        
        return Constructor(signature, block, accessModifier)
    }

    private fun addParameterAsLocalVariable(signature: FunctionSignature) {
        signature.parameters.forEach { scope.addLocalVariable(LocalVariable(it.name, it.type)) }
    }

    private fun getBlock(block: CASCParser.BlockContext): Block =
        block.accept(BlockVisitor(scope))

    private fun validateAllCodePathsHaveReturn(statement: Statement<*>, returnType: Type): Boolean {
        return when (statement) {
            is Block ->
                when (val lastStatement = statement.statements.lastOrNull()) {
                    is IfStatement -> validateAllCodePathsHaveReturn(lastStatement, returnType)
                    !is ReturnStatement -> false
                    else -> true
                }
            is IfStatement ->
                validateAllCodePathsHaveReturn(statement.trueStatement, returnType) &&
                        if (statement.falseStatement != null) validateAllCodePathsHaveReturn(
                            statement.falseStatement,
                            returnType
                        )
                        else false
            is ReturnStatement -> statement.expression !is EmptyExpression && statement.expression.type == returnType
            else -> false
        }
    }
}