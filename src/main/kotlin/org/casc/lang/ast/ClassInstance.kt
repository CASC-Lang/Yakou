package org.casc.lang.ast

import org.casc.lang.table.HasFlag
import org.casc.lang.table.Reference
import org.casc.lang.utils.getOrElse
import org.objectweb.asm.Opcodes

data class ClassInstance(
    override val packageReference: Reference?,
    val accessorToken: Token?,
    val mutKeyword: Token?,
    val classKeyword: Token?,
    override val typeReference: Reference?,
    var fields: List<Field>,
    val accessor: Accessor = Accessor.fromString(accessorToken?.literal)
) : TypeInstance(packageReference, typeReference), HasFlag {
    override val flag: Int by lazy {
        var flag = Opcodes.ACC_SUPER
        flag += accessor.access
        flag += mutKeyword.getOrElse(0, Opcodes.ACC_FINAL)
        flag
    }
}
