package org.yakou.lang.ast

import org.objectweb.asm.Opcodes

sealed class Modifier(val flag: Int, val keywordLiteral: String) {
    override fun toString(): String =
        keywordLiteral
    
    object Inline : Modifier(0, Keyword.INLINE.literal)
    object Mut : Modifier(-Opcodes.ACC_FINAL, Keyword.MUT.literal)
    object Inner : Modifier(0, Keyword.INNER.literal)

    sealed class AccessModifier(flag: Int, keywordLiteral: String) : Modifier(flag, keywordLiteral)

    object Pub : AccessModifier(Opcodes.ACC_PUBLIC, Keyword.PUB.literal)
    object Prot : AccessModifier(Opcodes.ACC_PROTECTED, Keyword.PROT.literal)
    object PubPkg : AccessModifier(0, "${Keyword.PUB.literal}(${Keyword.PKG.literal})")
    object Priv : AccessModifier(Opcodes.ACC_PRIVATE, Keyword.PRIV.literal)
}
