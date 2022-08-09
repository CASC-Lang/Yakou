package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.objectweb.asm.Opcodes

data class Modifiers(val modifierMap: LinkedHashMap<Modifier, Span> = linkedMapOf()): AstNode {
    override val span: Span? by lazy {
        modifierMap.values.reduceOrNull(Span::expand)
    }

    fun set(modifier: Modifier, span: Span): Boolean =
        modifierMap[modifier]?.let { false } ?: run {
            modifierMap[modifier] = span
            true
        }

    fun hasModifier(modifier: Modifier): Boolean =
        modifierMap[modifier] != null

    operator fun get(modifier: Modifier): Span? =
        modifierMap[modifier]

    fun isEmpty(): Boolean =
        modifierMap.isEmpty()

    fun sum(vararg additionalFlags: Int): Int {
        var access = Opcodes.ACC_FINAL + Opcodes.ACC_PUBLIC + additionalFlags.sum()

        for ((modifier, _) in modifierMap) {
            if (modifier is Modifier.AccessModifier) {
                access -= Opcodes.ACC_PUBLIC
                access += modifier.flag
                continue
            }

            access += modifier.flag
        }

        return access
    }
}