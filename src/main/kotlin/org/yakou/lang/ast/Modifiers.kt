package org.yakou.lang.ast

import chaos.unity.nenggao.Span

data class Modifiers(val modifierMap: LinkedHashMap<Modifier, Span> = linkedMapOf(), val immutable: Boolean) {
    fun set(modifier: Modifier, span: Span): Boolean =
        if (modifierMap.containsKey(modifier)) false
        else {
            modifierMap[modifier] = span
            true
        }

    operator fun get(modifier: Modifier): Span? =
        modifierMap[modifier]

    fun isEmpty(): Boolean =
        modifierMap.isEmpty()
}