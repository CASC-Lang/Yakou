package org.casc.lang.ast

import org.casc.lang.table.FunctionSignature
import org.casc.lang.table.PrimitiveType
import org.casc.lang.table.Reference
import org.casc.lang.table.Type

sealed class Expression {
    abstract val pos: Position?
    var type: Type? = null // Provided by Checker Unit
    var referencedExpression: Expression? = null // Used when returned object's of expression member is called
    var castTo: Type? = null

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

    data class IdentifierCallExpression(
        val ownerReference: Reference?, // Companion field calling
        val name: Token?,
        var index: Int? = null,
        var previousExpression: Expression? = null, // Used in chain calling, e.g. Identifier `a` in a.lol
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
    ) : Expression()

    data class AssignmentExpression(
        val identifier: Token?,
        val operator: Token?,
        val expression: Expression?,
        val retainLastValue: Boolean, // If assignment doesn't happen in pure ExpressionStatement, then it must retain its final value
        var isFieldAssignment: Boolean = false,
        var index: Int? = null,
        override val pos: Position? = identifier?.pos?.extend(expression?.pos)
    ) : Expression()

    data class UnaryExpression(
        val operator: Token?,
        val expression: Expression?,
        override val pos: Position? = operator?.pos?.extend(expression?.pos)
    ) : Expression()

    // TODO: Allows objects in BinaryExpression for further feature implementation such as Operator Overloading
    data class BinaryExpression(
        var left: Expression?, val operator: Token?, var right: Expression?,
        override val pos: Position? = left?.pos?.extend(right?.pos)
    ) : Expression() {
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
}
