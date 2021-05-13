package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.Type

data class Parameter(override val type: Type, val name: String, val defaultValue: Expression<*>?) :
    Expression<Parameter> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val (type1, _, defaultValue1) = other as Parameter

        return if (if (defaultValue != null) defaultValue != defaultValue1 else defaultValue1 != null) false else type == type1
    }

    override fun hashCode(): Int {
        var result = defaultValue?.hashCode() ?: 0
        result = 31 * result + type.hashCode()
        return result
    }
}
