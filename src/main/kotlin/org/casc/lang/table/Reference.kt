package org.casc.lang.table

import org.casc.lang.ast.Position
import org.casc.lang.ast.Token

/**
 * Reference provides a class reference's:
 * 1. Full qualified path: pkg.clazz
 * 2. Class name: clazz
 *
 * NOTE: Pass type name (separated by dot) instead of internal name (separated by slash)
 */
data class Reference(
    var fullQualifiedPath: String,
    val className: String,
    val pos: Position? = null,
    val tokens: List<Token?> = listOf()
) {
    constructor(token: Token?) : this(token?.literal ?: "", token?.literal ?: "", token?.pos, mutableListOf(token))
    constructor(path: String) : this(path, path.split('.').lastOrNull() ?: "", null)

    companion object {
        fun fromClass(clazz: Class<*>): Reference {
            val path = clazz.name
            val className = clazz.simpleName

            return Reference(path, className, null)
        }
    }

    fun internalName(): String =
        fullQualifiedPath.replace('.', '/')

    fun getPackagePath(): String =
        fullQualifiedPath.split('.').dropLast(1).joinToString(".")

    fun asCASCStyle(): String =
        fullQualifiedPath.replace(".", "::")

    fun isSamePackage(reference: Reference?): Boolean =
        getPackagePath() == reference?.getPackagePath()

    override fun toString(): String = asCASCStyle()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Reference

        if (fullQualifiedPath != other.fullQualifiedPath) return false
        if (className != other.className) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fullQualifiedPath.hashCode()
        result = 31 * result + className.hashCode()
        return result
    }
}
