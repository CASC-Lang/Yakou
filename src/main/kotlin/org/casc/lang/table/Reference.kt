package org.casc.lang.table

import org.casc.lang.ast.Position

data class Reference(val path: String, val className: String, private val position: Position? = null) {
    companion object {
        fun fromClass(clazz: Class<*>): Reference {
            val path = clazz.name
            val className = clazz.simpleName

            return Reference(path, className)
        }
    }

    fun position(): Position? =
        if (position == null) null
        else Position(
            position.lineNumber,
            position.start,
            position.start + path.length + path.filter { it == '.' }.length
        )

    fun internalName(): String =
        path.replace('.', '/')

    override fun toString(): String =
        path
}
