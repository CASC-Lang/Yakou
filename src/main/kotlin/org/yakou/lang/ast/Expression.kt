package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.TypeInfo
import kotlin.properties.Delegates

sealed class Expression {
    abstract val span: Span?
    lateinit var originalType: TypeInfo
    lateinit var finalType: TypeInfo

    class NumberLiteral(
        val integerPart: Token?,
        val dot: Token?,
        val floatPart: Token?,
        val specifiedType: Type?,
        override val span: Span
    ) : Expression() {
        var value by Delegates.notNull<Double>()
    }

    object Undefined : Expression() {
        override val span: Span? = null
    }
}
