package io.github.chaosunity.casc.parsing.scope

import jdk.internal.org.objectweb.asm.Opcodes.*

enum class AccessModifier(val accessOpcode: Int) {
    PUB(ACC_PUBLIC), PROT(ACC_PROTECTED), INTL(0), PRIV(ACC_PRIVATE)
}