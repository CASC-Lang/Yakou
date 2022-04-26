package org.casc.lang.ast

import org.casc.lang.table.Type

sealed class Statement {
    abstract val pos: Position?

    data class VariableDeclaration(
        val variables: List<Pair<Token?, Token?>>,
        val operator: Token?,
        val expressions: List<Expression?>,
        var indexes: MutableList<Int> = mutableListOf(),
        override val pos: Position? = Position.fromMultipleAndExtend(
            *variables.firstOrNull()?.toList()?.map { it?.pos }?.toTypedArray() ?: arrayOf())
            ?.extend(expressions.lastOrNull()?.pos)
    ) : Statement()

    data class IfStatement(
        val condition: Expression?,
        val trueStatement: Statement?,
        val elseStatement: Statement?,
        override val pos: Position?
    ) : Statement()

    data class JForStatement(
        val initStatement: Statement?,
        val condition: Expression?,
        val postExpression: Expression?,
        val statement: Statement?,
        override val pos: Position?
    ) : Statement()

    data class BlockStatement(
        val statements: List<Statement?>,
        override val pos: Position?
    ) : Statement()

    data class ExpressionStatement(
        val expression: Expression?,
        override val pos: Position? = expression?.pos
    ) : Statement()

    data class ReturnStatement(
        val expression: Expression?,
        var returnType: Type? = null,
        override val pos: Position?
    ) : Statement()
}