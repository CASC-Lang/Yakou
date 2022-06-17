package org.yakou.lang.ast

import chaos.unity.nenggao.Span

data class Modifiers(val maskMap: LinkedHashMap<Int, Span>) {
    fun isEmpty(): Boolean =
        maskMap.isEmpty()
}