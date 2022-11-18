package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.objectweb.asm.Opcodes

class Modifiers : LinkedHashMap<Modifier, Span>(), AstNode {
    override val span: Span? by lazy {
        values.reduceOrNull(Span::expand)
    }

    fun set(modifier: Modifier, span: Span): Boolean =
        if (containsKey(modifier)) {
            false
        } else {
            this[modifier] = span
            true
        }

    fun sum(vararg additionalFlags: Int): Int {
        var access = Opcodes.ACC_FINAL + Opcodes.ACC_PUBLIC + additionalFlags.sum()

        for ((modifier, _) in this) {
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
