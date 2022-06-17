package org.yakou.lang.ast

sealed class Item {
    data class Package(val identifier: Token, val openBrace: Token?, val items: List<Item>?, val closeBrace: Token?) : Item()
}
