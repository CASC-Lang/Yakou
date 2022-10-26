package org.yakou.lang.ast

import chaos.unity.nenggao.Span

sealed class Type : AstNode {
    abstract override val span: Span

    fun standardizeType(): String = when (this) {
        is Array -> {
            val typeBuilder = StringBuilder()
            var dimensionCount = 1
            var innerType = type

            while (innerType is Array) {
                dimensionCount++
                innerType = innerType.type
            }

            typeBuilder.append("[".repeat(dimensionCount))
            typeBuilder.append(innerType.standardizeType())
            typeBuilder.append("]".repeat(dimensionCount))

            typeBuilder.toString()
        }
        is TypePath -> {
            val typeBuilder = StringBuilder()

            for (token in path.pathSegments) {
                when (token.type) {
                    is TokenType.Identifier -> {
                        typeBuilder.append(token.literal)
                    }

                    is TokenType.DoubleColon -> {
                        typeBuilder.append("::")
                    }

                    is TokenType.Keyword -> {
                        // Self type
                        typeBuilder.append(token.type.literal)
                    }

                    else -> {}
                }
            }

            typeBuilder.toString()
        }
    }

    data class TypePath(val path: Path.SimplePath, val genericParameters: GenericParameters?) : Type() {
        override val span: Span by lazy {
            path.pathSegments.first().span.expand(path.pathSegments.last().span).expand(genericParameters?.span)
        }
    }

    data class Array(val openBracket: Token, val type: Type, val closeBracket: Token) : Type() {
        override val span: Span by lazy {
            openBracket.span.expand(closeBracket.span)
        }
    }
}
