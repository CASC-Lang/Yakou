package org.yakou.lang.ast

import org.yakou.lang.bind.TypeInfo

sealed class Item {
    data class Package(
        val pkg: Token,
        val identifier: Token,
        val openBrace: Token?,
        val items: List<Item>?,
        val closeBrace: Token?
    ) : Item()

    data class Function(
        val modifiers: Modifiers,
        val fn: Token,
        val name: Token,
        val openParenthesis: Token,
        val parameters: List<Parameter>,
        val closeParenthesis: Token,
        val arrow: Token?,
        val returnType: Type?,
        val body: FunctionBody?,
    ) : Item() {
        lateinit var returnTypeInfo: TypeInfo
    }
}
