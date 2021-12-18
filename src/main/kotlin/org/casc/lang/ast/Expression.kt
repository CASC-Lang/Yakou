package org.casc.lang.ast

import org.casc.lang.table.PrimitiveType
import org.casc.lang.table.Type

sealed class Expression {
    abstract val pos: Position?
    abstract var type: Type?

    data class IntegerLiteral(val literal: Token?, override val pos: Position? = literal?.pos) : Expression() {
        override var type: Type? = when {
            isI8() -> PrimitiveType.I8
            isI16() -> PrimitiveType.I16
            isI32() -> PrimitiveType.I32
            isI64() -> PrimitiveType.I64
            else -> null // Should not be null
        }

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
        override var type: Type? = when {
            literal?.literal?.endsWith('D') == true -> PrimitiveType.F64
            else -> PrimitiveType.F32
        }

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

    data class IdentifierExpression(
        val name: Token?,
        var index: Int? = null,
        override val pos: Position? = name?.pos
    ) : Expression() {
        override var type: Type? = null // Needs to be provided by checker
    }

    data class AssignmentExpression(
        val identifier: Token?,
        val operator: Token?,
        val expression: Expression?,
        var isFieldAssignment: Boolean = false,
        var index: Int? = null,
        var castTo: Type? = null,
        override val pos: Position? = identifier?.pos
    ) : Expression() {
        override var type: Type? = null
            get() = expression?.type
    }

    data class UnaryExpression(
        val operator: Token?,
        val expression: Expression?,
        override val pos: Position? = operator?.pos
    ) : Expression() {
        override var type: Type? = expression?.type
    }

    // TODO: Allows objects in BinaryExpression for further feature implementation such as Operator Overloading
    data class BinaryExpression(
        var left: Expression?, val operator: Token?, var right: Expression?,
        override val pos: Position? = left?.pos
    ) : Expression() {


        override var type: Type? = left?.type

        // Perform promotion on expressions, promotion only checks until i32
        fun promote() {
            for (type in PrimitiveType.promotionTable.keys.reversed().dropLast(2)) {
                if (left?.type == type) {
                    right?.type = type
                    break
                }

                if (right?.type == type) {
                    left?.type = type
                    break
                }
            }
        }
    }
}
