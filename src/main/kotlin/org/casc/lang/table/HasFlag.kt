package org.casc.lang.table

import org.casc.lang.ast.Accessor
import org.casc.lang.utils.getOrElse
import org.objectweb.asm.Opcodes

interface HasFlag {
    companion object {
        fun getFlag(accessor: Accessor, mutable: Boolean): Int =
            accessor.access + mutable.getOrElse(0, Opcodes.ACC_FINAL)
    }

    val flag: Int
}