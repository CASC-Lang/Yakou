package org.casc.lang.table

import org.casc.lang.ast.Position

data class Reference(var path: String, val className: String, val position: Position?) {
    companion object {
        fun fromClass(clazz: Class<*>): Reference {
            val path = clazz.name
            val className = clazz.simpleName

            return Reference(path, className, null)
        }
    }

    fun internalName(): String =
        path.replace('.', '/')

    override fun toString(): String =
        path
}
