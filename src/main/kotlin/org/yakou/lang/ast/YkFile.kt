package org.yakou.lang.ast

import chaos.unity.nenggao.Span

data class YkFile(val items: List<Item>): AstNode {
    override val span: Span? by lazy {
        items.firstOrNull()?.span?.expand(items.lastOrNull()?.span)
    }
}
