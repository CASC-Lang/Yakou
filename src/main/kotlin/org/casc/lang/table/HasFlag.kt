package org.casc.lang.table

import org.casc.lang.ast.Accessor
import org.casc.lang.utils.getOrElse
import org.objectweb.asm.Opcodes

interface HasFlag {
    companion object {
        fun getFlag(accessor: Accessor, abstr: Boolean, mutable: Boolean): Int =
            accessor.access + if (abstr) Opcodes.ACC_ABSTRACT else if (!mutable) Opcodes.ACC_FINAL else 0
    }

    val flag: Int
}