package org.casc.lang.table

/**
 * Refers to function's local variable, its type might be null since its value could be null.
 */
data class Variable(
    val mutable: Boolean,
    val name: String,
    var type: Type?,
    val index: Int,
    val declaredScopeDepth: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Variable

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
