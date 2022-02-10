package org.casc.lang.ast

import org.casc.lang.table.Reference
import org.casc.lang.table.Type

data class Parameter(
    val mutable: Token?,
    val name: Token?,
    val colon: Token?,
    val typeReference: Reference?,
    val type: Type? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Parameter

        if (typeReference != other.typeReference) return false

        return true
    }

    override fun hashCode(): Int {
        return typeReference?.hashCode() ?: 0
    }
}
