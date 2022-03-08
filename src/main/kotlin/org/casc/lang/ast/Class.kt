package org.casc.lang.ast

import org.casc.lang.table.HasFlag
import org.casc.lang.table.Reference
import org.casc.lang.utils.getOrElse
import org.objectweb.asm.Opcodes

data class Class(
    val packageReference: Reference?,
    val usages: List<Reference?>,
    val parentClassReference: Reference?,
    val interfaceReferences: List<Reference?>,
    val accessorToken: Token?,
    val mutKeyword: Token?,
    val classKeyword: Token?,
    val name: Token?,
    var fields: List<Field>,
    var constructors: List<Constructor>,
    var functions: List<Function>,
    val accessor: Accessor = Accessor.fromString(accessorToken?.literal)
) : HasFlag {
    override val flag: Int =
        Opcodes.ACC_SUPER + accessor.access + mutKeyword.getOrElse(0, Opcodes.ACC_FINAL)

    fun getReference(): Reference =
        Reference(packageReference?.let { "${it.fullQualifiedPath}/${name!!.literal}" } ?: name!!.literal,
            name!!.literal)
}
