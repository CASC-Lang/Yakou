package org.yakou.lang.ast

sealed class Item {
    data class Package(val identifier: Token, val items: List<Item>) : Item()

}
