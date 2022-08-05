package org.yakou.lang.ast

data class PrimaryConstructor(
    val modifiers: Modifiers,
    val openParenthesis: Token,
    val self: Token?,
    val selfComma: Token?,
    val parameters: List<ConstructorParameter>,
    val closeParenthesis: Token
) {
    class ConstructorParameter(val modifiers: Modifiers, name: Token, colon: Token, type: Type): Parameter(name, colon, type)
}