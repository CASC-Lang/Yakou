package org.yakou.lang.ast

import chaos.unity.nenggao.Span

sealed class Path: AstNode {
    data class SimplePath(val pathSegments: List<Token>) : Path() {
        override val span: Span?
            get() = pathSegments.firstOrNull()?.span?.expand(pathSegments.lastOrNull()?.span)

        fun append(token: Token): SimplePath {
            val pathSegments = pathSegments.toMutableList()

            pathSegments += token

            return SimplePath(pathSegments)
        }

        fun append(path: SimplePath): SimplePath {
            val pathSegments = pathSegments.toMutableList()

            pathSegments += path.pathSegments

            return SimplePath(pathSegments)
        }

        override fun toString(): String =
            pathSegments.filter { it.type == TokenType.Identifier } // Ignore `::` since some situations won't have `::`
                .map(Token::literal)
                .joinToString(separator = "::")
    }
}
