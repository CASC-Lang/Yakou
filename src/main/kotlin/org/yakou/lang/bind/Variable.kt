package org.yakou.lang.bind

import org.yakou.lang.ast.Token

open class Variable(val nameToken: Token, val name: String = nameToken.literal) {
    var isUsed: Boolean = false
    var typeInfo: TypeInfo = TypeInfo.Primitive.UNIT_TYPE_INFO

    fun markUsed() {
        isUsed = true
    }

    fun size(): Int =
        typeInfo.asPrimitive()?.let {
            if (it.type == PrimitiveType.I64 || it.type == PrimitiveType.F64) 2
            else 1
        } ?: 1

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

    class ValueParameter(nameToken: Token): Variable(nameToken)
}