package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.Symbol
import org.yakou.lang.bind.TypeInfo
import kotlin.properties.Delegates

sealed class Expression {
    abstract val span: Span
    var originalType: TypeInfo = TypeInfo.Primitive.UNIT_TYPE_INFO
    var finalType: TypeInfo = TypeInfo.Primitive.UNIT_TYPE_INFO

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
            UnsignedRightShift,
            LogicalOr,
            LogicalAnd
            ;

            fun isShiftOperation(): Boolean =
                this.ordinal in 5..7
        }
    }

    class Identifier(
        val identifier: Token
    ) : Expression() {
        override val span: Span = identifier.span

        lateinit var symbolInstance: Symbol
    }

    class As(
        var expression: Expression,
        val `as`: Token,
        val type: Type
    ) : Expression() {
        override val span: Span by lazy {
            expression.span.expand(type.span)
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

    class BoolLiteral(
        val boolKeyword: Token?,
        override val span: Span
    ) : LiteralExpression() {
        var value by Delegates.notNull<Boolean>()
    }

    // Empty expression represents an empty optional expression, which is useful in several cases, e.g. return statement
    class Empty(override val span: Span) : Expression()

    // Undefined expression represents an unparseable expression, indicates unable to find an existing grammar rule set
    // for single expression
    object Undefined : Expression() {
        override val span: Span = TODO("Undefined expression does not have span")
    }
}
