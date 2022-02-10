package org.casc.lang.ast

import org.casc.lang.table.*
import org.objectweb.asm.Opcodes

data class Field(
    val ownerReference: Reference?,
    val accessorToken: Token?,
    val mutKeyword: Token?,
    val compKeyword: Token?,
    val name: Token?,
    val typeReference: Reference?,
    val accessor: Accessor = Accessor.fromString(accessorToken?.literal),
    var type: Type? = null
) : HasDescriptor, HasAccess {
    override val descriptor: String
        get() = type?.descriptor ?: ""
    override val accessFlag: Int =
        (mutKeyword?.let { Opcodes.ACC_FINAL } ?: 0) + accessor.access + (compKeyword?.let { Opcodes.ACC_STATIC } ?: 0)

    fun asClassField(): ClassField =
        ClassField(
            ownerReference,
            compKeyword != null,
            mutKeyword != null,
            accessor,
            name?.literal ?: "<Unknown field name>",
            type ?: PrimitiveType.Unit
        )
}
