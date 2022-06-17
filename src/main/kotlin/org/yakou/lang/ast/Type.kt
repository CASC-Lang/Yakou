package org.yakou.lang.ast

sealed class Type {
    data class TypePath(val path: Path.SimplePath) : Type()
    data class Array(val openBracket: Token, val type: Type, val closeBracket: Token) : Type()
}
