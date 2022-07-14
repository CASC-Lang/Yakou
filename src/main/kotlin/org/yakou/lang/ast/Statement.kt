package org.yakou.lang.ast

import chaos.unity.nenggao.Span

sealed class Statement {
    abstract val span: Span

    class VariableDeclaration(
        val let: Token,
        val mut: Token?,
        val name: Token,
        val colon: Token?,
        val specifiedType: Path.SimplePath?,
        val equal: Token,
        val expression: Expression,
    ) : Statement() {
        override val span: Span by lazy {
            let.span.expand(expression.span)
        }
    }

    class ExpressionStatement(val expression: Expression): Statement() {
        override val span: Span = expression.span
    }
}