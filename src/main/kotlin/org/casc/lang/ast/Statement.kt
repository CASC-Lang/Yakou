package org.casc.lang.ast

import org.casc.lang.table.Type

sealed class Statement {
    abstract val pos: Position?

    data class VariableDeclaration(
        val mutKeyword: Token?,
        val name: Token?,
        val operator: Token?,
        val expression: Expression?,
        var index: Int? = null,
        override val pos: Position? = (mutKeyword?.pos ?: name?.pos)?.extend(expression?.pos)
    ) : Statement()

    data class IfStatement(
        val condition: Expression?,
        val trueStatement: Statement?,
        val elseStatement: Statement?,
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