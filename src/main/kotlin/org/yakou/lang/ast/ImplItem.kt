package org.yakou.lang.ast

import chaos.unity.nenggao.Span

sealed interface ImplItem : AstNode {
    abstract override val span: Span
}
