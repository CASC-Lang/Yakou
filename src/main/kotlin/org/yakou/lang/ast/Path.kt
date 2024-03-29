package org.yakou.lang.ast

import chaos.unity.nenggao.Span

sealed class Path : AstNode {
    abstract override fun toString(): String

    class TokenPath(val token: Token) : Path() {
        override val span: Span by lazy(token::span)

        override fun toString(): String =
            token.literal
    }

    data class SimplePath(val pathSegments: List<Path>, val separator: String = "::") : Path() {
        override val span: Span? by lazy {
            pathSegments.firstOrNull()?.span?.expand(pathSegments.lastOrNull()?.span)
        }

        constructor(vararg pathTokens: Token, separator: String = "::") :
            this(pathTokens.map(::TokenPath), separator)

        fun append(token: Token): SimplePath {
            val pathSegments = pathSegments.toMutableList()

            pathSegments += TokenPath(token)

            return copy(pathSegments = pathSegments)
        }

        fun append(path: SimplePath): SimplePath {
            val pathSegments = pathSegments.toMutableList()

            pathSegments += path

            return copy(pathSegments = pathSegments)
        }

        override fun toString(): String =
            pathSegments.joinToString("") {
                if (it is TokenPath && it.token.type is TokenType.DoubleColon) {
                    separator
                } else {
                    it.toString()
                }
            }
    }
}
