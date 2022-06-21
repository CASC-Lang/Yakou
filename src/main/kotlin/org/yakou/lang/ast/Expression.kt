package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.TypeInfo
import kotlin.properties.Delegates

sealed class Expression {
    abstract val span: Span
    lateinit var originalType: TypeInfo
    lateinit var finalType: TypeInfo

    sealed class LiteralExpression : Expression()

    class NumberLiteral(
        val integerPart: Token?,
        val dot: Token?,
        val floatPart: Token?,
        val specifiedType: Type?,
        override val span: Span
    ) : LiteralExpression() {
        var value by Delegates.notNull<Double>()
        var specifiedTypeInfo: TypeInfo.Primitive? = null
    }

    object Undefined : Expression() {
        override val span: Span = TODO("Undefined expression does not have span")
    }
}
