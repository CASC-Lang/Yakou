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
    var packagePath: String,
    var className: String,
    val tokens: MutableList<Token?> = mutableListOf(),
    val pos: Position? = null,
    var fullQualifiedPath: String = packagePath.let {
        val pkgPath = it.split('.').dropLast(1).joinToString(".")

        if (pkgPath.isEmpty()) ""
        else "$pkgPath."
    } + className
) {
    constructor(clazz: Class<*>) : this(clazz.name, clazz.simpleName)
    constructor(packagePath: String, className: String, vararg tokens: Token?) : this(
        packagePath,
        className,
        tokens.toMutableList(),
        tokens.firstOrNull()?.pos?.extend(tokens.lastOrNull()?.pos)
    )

    constructor(fullQualifiedPath: String, vararg tokens: Token?) : this(
        fullQualifiedPath.split('.').dropLast(1).joinToString(),
        fullQualifiedPath.split('.').last(),
        tokens.toMutableList(),
        tokens.firstOrNull()?.pos?.extend(tokens.lastOrNull()?.pos),
        fullQualifiedPath = fullQualifiedPath
    )

    fun internalName(): String =
        fullQualifiedPath.replace('.', '/')

    fun asCASCStyle(): String =
        fullQualifiedPath.replace(".", "::")

    fun isSamePackage(reference: Reference?): Boolean =
        packagePath == reference?.packagePath

    fun prepend(pacakage: String) {
        packagePath = "$pacakage.$packagePath"
        fullQualifiedPath = "$pacakage.$fullQualifiedPath"
    }

    fun append(type: String) {
        packagePath += type
        className = type
        fullQualifiedPath += type
    }

    fun appendArrayDimension(dimension: Int) {
        packagePath += "[]".repeat(dimension)
        fullQualifiedPath += "[]".repeat(dimension)
    }

    override fun toString(): String = asCASCStyle()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Reference

        if (fullQualifiedPath != other.fullQualifiedPath) return false

        return true
    }

    override fun hashCode(): Int = fullQualifiedPath.hashCode()
}
