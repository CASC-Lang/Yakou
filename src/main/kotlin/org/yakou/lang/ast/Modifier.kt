package org.yakou.lang.ast

import org.objectweb.asm.Opcodes

sealed class Modifier(val flag: Int) {
    sealed class AccessModifier(flag: Int): Modifier(flag)

    object Pub: AccessModifier(Opcodes.ACC_PUBLIC)
    object Prot: AccessModifier(Opcodes.ACC_PROTECTED)
    object PubPkg: AccessModifier(0)
    object Priv: Modifier(Opcodes.ACC_PRIVATE)
    object Mut: Modifier(-Opcodes.ACC_FINAL)
}