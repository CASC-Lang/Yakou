package org.casc.lang.ast

import org.casc.lang.table.HasFlag
import org.casc.lang.table.Reference
import org.objectweb.asm.Opcodes

data class TraitInstance(
    override val packageReference: Reference?,
    val accessorToken: Token?,
    val traitKeyword: Token?,
    override val typeReference: Reference,
    override val fields: List<Field>,
    override val accessor: Accessor = Accessor.fromString(accessorToken?.literal)
) : TypeInstance(), HasFlag {
    override val flag: Int by lazy {
        accessor.access + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE
    }
}
