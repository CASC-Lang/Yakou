package org.casc.lang.table

import org.casc.lang.ast.Position
import org.casc.lang.ast.Token

data class Reference(var path: String, val className: String, val position: Position?, val tokens: List<Token?> = listOf()) {
    constructor(token: Token?) : this(token?.literal ?: "", token?.literal ?: "", token?.pos, mutableListOf(token))

    companion object {
        fun fromClass(clazz: Class<*>): Reference {
            val path = clazz.name
            val className = clazz.simpleName

            return Reference(path, className, null)
        }
    }

    fun internalName(): String =
        path.replace('.', '/')

    fun asCascStyle(): String =
        path.replace(".", "::")

    override fun toString(): String =
        path

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Reference

        if (path != other.path) return false
        if (className != other.className) return false

        return true
    }

    override fun hashCode(): Int {
        var result = path.hashCode()
        result = 31 * result + className.hashCode()
        return result
    }
}
