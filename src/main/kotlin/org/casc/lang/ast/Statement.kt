package org.casc.lang.ast

sealed class Statement {
    abstract val position: Position?

    data class VariableDeclaration(
        val mutKeyword: Token?,
        val name: Token?,
        val operator: Token?,
        val expression: Expression?,
        var index: Int? = null,
        override val position: Position? = mutKeyword?.pos ?: name?.pos
    ) : Statement()

    data class ExpressionStatement(
        val expression: Expression?,
        override val position: Position? = expression?.pos
    ) : Statement()
}