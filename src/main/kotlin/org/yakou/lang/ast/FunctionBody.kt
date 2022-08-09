package org.yakou.lang.ast

import chaos.unity.nenggao.Span

sealed class FunctionBody: AstNode {
    abstract override val span: Span

    data class SingleExpression(val equal: Token, var expression: Expression) : FunctionBody() {
        override val span: Span by lazy {
            equal.span.expand(expression.span)
        }
    }

    data class BlockExpression(
        val openBrace: Token,
        val statements: List<Statement>,
        val closeBrace: Token
    ) : FunctionBody() {
        override val span: Span by lazy {
            openBrace.span.expand(closeBrace.span)
        }
    }
}
