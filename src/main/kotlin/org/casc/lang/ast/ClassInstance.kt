package org.casc.lang.ast

import org.casc.lang.table.Reference
import org.casc.lang.utils.getOrElse
import org.objectweb.asm.Opcodes

data class ClassInstance(
    override val packageReference: Reference?,
    val accessorToken: Token?,
    val abstrToken: Token?,
    val mutKeyword: Token?,
    val classKeyword: Token,
    override val typeReference: Reference,
    override val fields: List<Field>,
    override val accessor: Accessor = Accessor.fromString(accessorToken?.literal)
) : TypeInstance() {
    override val flag: Int by lazy {
        var flag = Opcodes.ACC_SUPER
        flag += accessor.access
        flag += abstrToken.getOrElse(Opcodes.ACC_ABSTRACT, mutKeyword.getOrElse(0, Opcodes.ACC_FINAL))
        flag
    }
}
