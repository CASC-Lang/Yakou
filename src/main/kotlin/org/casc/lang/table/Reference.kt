package org.casc.lang.table

import org.casc.lang.ast.Position

data class Reference(val path: String, val className: String, private val position: Position? = null) {
    fun position(): Position? =
        if (position == null) null
        else Position(
            position.lineNumber,
            position.start,
            position.start + path.length + path.filter { it == '.' }.length
        )

    override fun toString(): String =
        path
}
