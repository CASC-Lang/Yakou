package org.yakou.lang.ast

import chaos.unity.nenggao.Span

data class Modifiers(val maskMap: LinkedHashMap<Int, Span> = linkedMapOf()) {
    fun set(flag: Int, modifierSpan: Span): Boolean =
        if (maskMap.containsKey(flag)) false
        else {
            maskMap[flag] = modifierSpan
            true
        }

    operator fun get(flag: Int): Span? =
        maskMap[flag]

    fun isEmpty(): Boolean =
        maskMap.isEmpty()
}