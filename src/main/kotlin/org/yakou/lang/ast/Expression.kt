package org.yakou.lang.ast

import chaos.unity.nenggao.Span

sealed class Expression {
    abstract val span: Span
}
