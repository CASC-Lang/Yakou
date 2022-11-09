package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.TypeInfo

data class GenericDeclarationParameters(
    val openBracket: Token,
    val parameters: List<GenericDeclarationParameter>,
    val closeBracket: Token
) : AstNode {
    override val span: Span by lazy {
        openBracket.span.expand(closeBracket.span)
    }

    sealed class GenericDeclarationParameter : AstNode {
        abstract override val span: Span
        abstract val genericConstraint: TypeInfo.GenericConstraint
    }

    data class ConstraintGenericDeclarationParameter(
        val identifier: Token,
        val boundIndicator: Token?,
        val constraints: List<Type>
    ) : GenericDeclarationParameter() {
        override val span: Span by lazy {
            identifier.span.expand(boundIndicator?.span).expand(constraints.lastOrNull()?.span)
        }

        override val genericConstraint: TypeInfo.GenericConstraint = TypeInfo.GenericConstraint(
            identifier.literal,
            when (boundIndicator?.type) {
                TokenType.LesserColon -> TypeInfo.GenericConstraint.BoundType.UPPER
                TokenType.GreaterColon -> TypeInfo.GenericConstraint.BoundType.LOWER
                else -> TypeInfo.GenericConstraint.BoundType.NONE
            },
            TypeInfo.GenericConstraint.VarianceType.INVARIANCE
        )
    }

    @Deprecated("Wildcard is not allowed in declaration context")
    data class WildcardConstraintGenericDeclarationParameter(
        val boundIndicator: Token,
        val type: Type
    ) : GenericDeclarationParameter() { // TODO: Constraints
        override val span: Span by lazy {
            boundIndicator.span.expand(type.span)
        }

        override val genericConstraint: TypeInfo.GenericConstraint = TypeInfo.GenericConstraint(
            "_",
            when (boundIndicator.type) {
                TokenType.PlusColon -> TypeInfo.GenericConstraint.BoundType.UPPER
                TokenType.MinusColon -> TypeInfo.GenericConstraint.BoundType.LOWER
                else -> TypeInfo.GenericConstraint.BoundType.NONE
            },
            TypeInfo.GenericConstraint.VarianceType.INVARIANCE

        )
    }

    data class VarianceGenericDeclarationParameter(
        val varianceIndicator: Token,
        val identifier: Token
    ) : GenericDeclarationParameter() {
        override val span: Span by lazy {
            varianceIndicator.span.expand(identifier.span)
        }

        override val genericConstraint: TypeInfo.GenericConstraint = TypeInfo.GenericConstraint(
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
