package org.yakou.lang.ast

sealed class Item {
    data class Package(
        val pkg: Token,
        val identifier: Token,
        val openBrace: Token?,
        val items: List<Item>?,
        val closeBrace: Token?
    ) : Item()

    data class Function(
        val fn: Token,
        val name: Token,
        val openParenthesis: Token,
        val parameters: List<Parameter>,
        val closeParenthesis: Token,
        val arrow: Token?,
        val returnType: Type?,
        val body: FunctionBody?,
    ) : Item()
}
