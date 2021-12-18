package org.casc.lang.table

/**
 * Refers to function's local variable, its type might be null since its value could be null.
 */
data class Variable(val name: String, val type: Type?) {
    override fun equals(other: Any?): Boolean =
        if (other !is Variable) false
        else other.name == name
}
