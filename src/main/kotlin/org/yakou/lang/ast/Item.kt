package org.yakou.lang.ast

import chaos.unity.nenggao.Span

sealed interface Item : AstNode {
    abstract override val span: Span
}
