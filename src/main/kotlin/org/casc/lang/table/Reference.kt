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

    override fun toString(): String =
        path
}
