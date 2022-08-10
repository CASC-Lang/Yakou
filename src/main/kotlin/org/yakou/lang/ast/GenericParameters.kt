package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.TypeInfo

data class GenericParameters(val openBracket: Token, val parameters: List<GenericParameter>, val closeBracket: Token) :
    AstNode {
    override val span: Span by lazy {
        openBracket.span.expand(closeBracket.span)
    }

    sealed class GenericParameter : AstNode {
        abstract override val span: Span
    }

    data class ConstraintGenericParameter(
        val identifier: Token,
        val boundIndicator: Token?,
        val constraints: List<Type>
    ) : GenericParameter() {
        override val span: Span by lazy {
            identifier.span.expand(boundIndicator?.span).expand(constraints.lastOrNull()?.span)
        }
    }

    data class WildCardConstraintGenericParameter(
        val boundIndicator: Token,
        val type: Type
    ) : GenericParameter() { // TODO: Constraints
        override val span: Span by lazy {
            boundIndicator.span.expand(type.span)
        }

        val genericConstraint: TypeInfo.GenericConstraint = TypeInfo.GenericConstraint(
            "_",
            when (boundIndicator.type) {
                TokenType.PlusColon -> TypeInfo.GenericConstraint.BoundType.UPPER
                TokenType.MinusColon -> TypeInfo.GenericConstraint.BoundType.LOWER
                else -> TypeInfo.GenericConstraint.BoundType.NONE
            },
            TypeInfo.GenericConstraint.VarianceType.INVARIANCE,

            )
    }

    data class VarianceGenericParameter(
        val varianceIndicator: Token,
        val identifier: Token
    ) : GenericParameter() {
        override val span: Span by lazy {
            varianceIndicator.span.expand(identifier.span)
        }

        val genericConstraint: TypeInfo.GenericConstraint = TypeInfo.GenericConstraint(
            identifier.literal,
            TypeInfo.GenericConstraint.BoundType.NONE,
            when (varianceIndicator.type) {
                TokenType.Plus -> TypeInfo.GenericConstraint.VarianceType.COVARIANCE
                TokenType.Minus -> TypeInfo.GenericConstraint.VarianceType.CONTRAVARIANCE
                else -> TypeInfo.GenericConstraint.VarianceType.INVARIANCE
            }
        )
    }
}