package org.yakou.lang.ast

import chaos.unity.nenggao.Span

sealed class Type {
    abstract val span: Span

    data class TypePath(val path: Path.SimplePath) : Type() {
        override val span: Span by lazy {
            path.pathSegments.first().span.expand(path.pathSegments.last().span)
        }
    }

    data class Array(val openBracket: Token, val type: Type, val closeBracket: Token) : Type() {
        override val span: Span by lazy {
            openBracket.span.expand(closeBracket.span)
        }
    }
}
