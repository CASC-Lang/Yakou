package org.casc.lang.ast

import org.casc.lang.table.*
import org.casc.lang.utils.getOrElse
import org.objectweb.asm.Opcodes

data class Field(
    val ownerReference: Reference?,
    val accessorToken: Token?,
    val abstrKeyword: Token?,
    val mutKeyword: Token?,
    val compKeyword: Token?,
    val name: Token?,
    val typeReference: Reference?,
    val accessor: Accessor = Accessor.fromString(accessorToken?.literal),
    var type: Type? = null
) : HasDescriptor, HasFlag {
    override val descriptor: String
        get() = type?.descriptor ?: ""
    override val flag: Int by lazy {
        var flag = accessor.access
        flag += compKeyword.getOrElse(Opcodes.ACC_STATIC)
        flag += abstrKeyword.getOrElse(Opcodes.ACC_ABSTRACT)
        flag += mutKeyword.getOrElse(0, Opcodes.ACC_FINAL)
        flag
    }

    fun asClassField(): TypeField =
        TypeField(
            ownerReference,
            compKeyword != null,
            mutKeyword != null,
            accessor,
            name?.literal ?: "<Unknown field name>",
            type ?: PrimitiveType.Unit
        )
}
