package io.github.chaosunity.casc.parsing.scope

import jdk.internal.org.objectweb.asm.Opcodes.*
import java.lang.reflect.Modifier

enum class AccessModifier(val accessOpcode: Int) {
    PUB(ACC_PUBLIC), PROT(ACC_PROTECTED), INTL(0), PRIV(ACC_PRIVATE);

    companion object {
        fun getModifier(value: String?) =
            AccessModifier.valueOf(value?.toUpperCase() ?: "PUB")

        fun getModifier(modifier: Int) =
            when {
                Modifier.isPublic(modifier) -> PUB
                Modifier.isProtected(modifier) -> PROT
                Modifier.isPrivate(modifier) -> PRIV
                else -> INTL
            }
    }
}