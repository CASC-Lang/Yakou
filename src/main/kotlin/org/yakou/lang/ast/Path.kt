package org.yakou.lang.ast

sealed class Path {
    data class SimplePath(val pathSegments: List<Token>) : Path() {
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
