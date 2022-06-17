package org.yakou.lang.ast

sealed class FunctionBody {
    data class SingleExpression(val equal: Token, val expression: Expression) : FunctionBody()
    data class BlockExpression(
        val openBrace: Token,
        val statements: List<Statement>,
        val closeBrace: Token
    ) : FunctionBody()
}
