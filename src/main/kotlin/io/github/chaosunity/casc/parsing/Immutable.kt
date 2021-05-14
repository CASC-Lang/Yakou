package io.github.chaosunity.casc.parsing

import jdk.internal.org.objectweb.asm.Opcodes.ACC_FINAL

interface Immutable {
    val immutable: Boolean
    val finalOpcode: Int
        get() = if (immutable) ACC_FINAL else 0
}