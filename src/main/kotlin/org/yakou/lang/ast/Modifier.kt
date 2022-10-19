package org.yakou.lang.ast

import org.objectweb.asm.Opcodes

sealed class Modifier(val flag: Int) {
    object Inline : Modifier(0)
    object Mut : Modifier(-Opcodes.ACC_FINAL)

    sealed class AccessModifier(flag: Int) : Modifier(flag)

    object Pub : AccessModifier(Opcodes.ACC_PUBLIC)
    object Prot : AccessModifier(Opcodes.ACC_PROTECTED)
    object PubPkg : AccessModifier(0)
    object Priv : AccessModifier(Opcodes.ACC_PRIVATE)
}
