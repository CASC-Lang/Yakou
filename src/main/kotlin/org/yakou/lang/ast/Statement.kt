package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import kotlin.properties.Delegates

sealed class Statement {
    abstract val span: Span

    class VariableDeclaration(
        val let: Token,
        val mut: Token?,
        val name: Token,
        val colon: Token?,
        val specifiedType: Path.SimplePath?,
        val equal: Token,
        var expression: Expression,
    ) : Statement() {
        override val span: Span by lazy {
            let.span.expand(expression.span)
        }

        var ignore: Boolean = false
        var index by Delegates.notNull<Int>()
    }

    class ExpressionStatement(var expression: Expression): Statement() {
        override val span: Span = expression.span
    }
}