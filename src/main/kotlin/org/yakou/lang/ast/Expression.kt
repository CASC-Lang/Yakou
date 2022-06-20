package org.yakou.lang.ast

import chaos.unity.nenggao.Span

sealed class Expression {
    abstract val span: Span?

    class NumberLiteral(
        val integerPart: Token?,
        val dot: Token?,
        val floatPart: Token?,
        val type: Type?,
        override val span: Span
    ) : Expression()

    object Undefined : Expression() {
        override val span: Span? = null
    }
}
