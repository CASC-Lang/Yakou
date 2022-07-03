package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.TypeInfo
import kotlin.properties.Delegates

sealed class Expression {
    abstract val span: Span
    lateinit var originalType: TypeInfo
    lateinit var finalType: TypeInfo

    class BinaryExpression(
        var leftExpression: Expression,
        val operator: List<Token>,
        var rightExpression: Expression,
        val operation: BinaryOperation
    ) : Expression() {
        override val span: Span by lazy {
            leftExpression.span.expand(rightExpression.span)
        }

        enum class BinaryOperation {
            Addition,
            Subtraction,
            Multiplication,
            Division,
            Modulo,
            LeftShift,
            RightShift,
            UnsignedRightShift
            ;

            fun isShiftOperation(): Boolean =
                this.ordinal in 5..7
        }
    }

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
