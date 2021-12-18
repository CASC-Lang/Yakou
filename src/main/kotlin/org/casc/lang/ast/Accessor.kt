package org.casc.lang.ast

import org.objectweb.asm.Opcodes

enum class Accessor(val access: Int) {
    Pub(Opcodes.ACC_PUBLIC),        // Public
    Prot(Opcodes.ACC_PROTECTED),    // Protected
    Intl(0),                        // Internal
    Priv(Opcodes.ACC_PRIVATE)       // Private
    ;

    companion object {
        val validKeywords: Array<String> = arrayOf(
            "pub", "prot", "intl", "priv"
        )

        fun fromString(literal: String?): Accessor = when (literal) {
            "pub" -> Pub
            "prot" -> Prot
            "intl" -> Intl
            "priv" -> Priv
            else -> Pub
        }
    }
}