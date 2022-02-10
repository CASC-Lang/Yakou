package org.casc.lang.ast

import org.casc.lang.table.FunctionSignature
import org.casc.lang.table.PrimitiveType
import org.casc.lang.table.Reference
import org.casc.lang.table.Type

sealed class Expression {
    abstract val pos: Position?
    var type: Type? = null // Provided by Checker Unit
    var castTo: Type? = null

    // Exceptions: PreviousExpression does not count as sub Expression
    open fun getExpressions(): List<Expression?>? = null

    data class IntegerLiteral(val literal: Token?, override val pos: Position? = literal?.pos) : Expression() {
        fun removeTypeSuffix() {
            if (literal?.literal?.lastOrNull()?.isLetter() == true) {
                literal.literal = literal.literal.dropLast(1)
            }
        }

        fun isI8(): Boolean =
            literal?.literal?.endsWith('B') ?: false

        fun isI16(): Boolean =
            literal?.literal?.endsWith('S') ?: false

        fun isI32(): Boolean =
            literal?.literal?.endsWith('I') ?: false || !(isI8() || isI16() || isI64())

        fun isI64(): Boolean =
            literal?.literal?.endsWith('L') ?: false
    }

    data class FloatLiteral(val literal: Token?, override val pos: Position? = literal?.pos) : Expression() {
        fun removeTypeSuffix() {
            if (literal?.literal?.lastOrNull()?.isLetter() == true) {
                literal.literal = literal.literal.dropLast(1)
            }
        }

        fun isF32(): Boolean =
            literal?.literal?.endsWith('F') ?: false || !isF64()

        fun isF64(): Boolean =
            literal?.literal?.endsWith('D') ?: false
    }

    data class CharLiteral(val literal: Token?, override val pos: Position? = literal?.pos) : Expression()

    data class StrLiteral(val literal: Token?, override val pos: Position? = literal?.pos) : Expression()

    data class BoolLiteral(val literal: Token?, override val pos: Position? = literal?.pos) : Expression()

    data class NullLiteral(val literal: Token?, override val pos: Position? = literal?.pos) : Expression()

    data class IdentifierCallExpression(
        var ownerReference: Reference?, // Companion field calling
        val name: Token?,
        var index: Int? = null,
        var isAssignedBy: Boolean = false,
        var isCompField: Boolean = false,
        var previousExpression: Expression? = null, // Used in chain calling, e.g. Identifier `a` in a.lol
        var isClassName: Boolean = false,
        override val pos: Position? = name?.pos
    ) : Expression()

    data class FunctionCallExpression(
        val ownerReference: Reference?, // Companion function calling
        val name: Token?,
        val arguments: List<Expression?>,
        val inCompanionContext: Boolean = false,
        var referenceFunctionSignature: FunctionSignature? = null, // Needs to be provided by checker
        var previousExpression: Expression? = null, // Used in chain calling, e.g. Identifier `a` in a.lol()
        override val pos: Position? = name?.pos?.extend(arguments.lastOrNull()?.pos)?.extend()
    ) : Expression() {
        override fun getExpressions(): List<Expression?> =
            arguments
    }

    data class IndexExpression(
        val previousExpression: Expression?,
        val indexExpression: Expression?,
        var isAssignedBy: Boolean = false,
        override val pos: Position? = previousExpression?.pos?.extend(indexExpression?.pos)
    ) : Expression() {
        override fun getExpressions(): List<Expression?> =
            listOf(previousExpression, indexExpression)
    }

    // Specifically used by local variable assignment
    // TODO: change identifier into expression
    data class AssignmentExpression(
        val leftExpression: Expression?,
        val operator: Token?,
        val rightExpression: Expression?,
        val retainLastValue: Boolean, // If assignment doesn't happen in pure ExpressionStatement, then it must retain its final value
        override val pos: Position? = leftExpression?.pos?.extend(rightExpression?.pos)
    ) : Expression() {
        override fun getExpressions(): List<Expression?> =
            listOf(rightExpression)
    }

    data class UnaryExpression(
        val operator: Token?,
        val expression: Expression?,
        val postfix: Boolean = false,
        val retainValue: Boolean = false, // Only used in increment and decrement
        override val pos: Position? = operator?.pos?.extend(expression?.pos)
    ) : Expression() {
        override fun getExpressions(): List<Expression?> =
            listOf(expression)
    }

    // TODO: Allows objects in BinaryExpression for further feature implementation such as Operator Overloading
    data class BinaryExpression(
        var left: Expression?, val operator: Token?, var right: Expression?,
        override val pos: Position? = left?.pos?.extend(right?.pos)
    ) : Expression() {
        override fun getExpressions(): List<Expression?> =
            listOf(left, right)

        // Perform promotion on expressions, promotion only checks until i32, in the end, promotion will assign its final type
        fun promote() {
            for (type in PrimitiveType.promotionTable.keys.toList().dropLast(2)) {
                if (left?.type == type) {
                    right?.castTo = type
                    this.type = left?.castTo ?: left?.type
                    break
                }

                if (right?.type == type) {
                    left?.castTo = type
                    this.type = left?.castTo ?: left?.type
                    break
                }
            }
        }
    }

    data class ParenthesizedExpression(
        val expression: Expression?,
        override val pos: Position? = expression?.pos
    ) : Expression()

    data class ArrayInitialization(
        val inferTypeReference: Reference?,
        val elements: List<Expression?>,
        override val pos: Position?
    ) : Expression() {
        override fun getExpressions(): List<Expression?> =
            elements
    }

    data class ArrayDeclaration(
        val baseTypeReference: Reference?,
        val dimensionExpressions: List<Expression?>,
        override val pos: Position?
    ) : Expression() {
        override fun getExpressions(): List<Expression?> =
            dimensionExpressions
    }
}
