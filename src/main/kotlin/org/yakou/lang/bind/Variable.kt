package org.yakou.lang.bind

import org.yakou.lang.ast.Expression
import org.yakou.lang.ast.Token

open class Variable(val mutToken: Token?, val nameToken: Token, val index: Int, val name: String = nameToken.literal) : Symbol() {
    override val mutable: Boolean = mutToken != null
    var propagatable: Boolean = false // Constant folding usage
    var referencedCount: Int = 0 // Constant folding usage
    lateinit var propagateExpression: Expression

    var isUsed: Boolean = false
    override var typeInfo: TypeInfo = TypeInfo.Primitive.UNIT_TYPE_INFO

    fun markUsed() {
        isUsed = true
    }

    fun reference() {
        referencedCount++
    }

    fun dereference() {
        referencedCount--
    }

    fun size(): Int =
        typeInfo.asPrimitive()?.let {
            if (it.type == PrimitiveType.I64 || it.type == PrimitiveType.F64) {
                2
            } else {
                1
            }
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

    class ValueParameter(nameToken: Token, index: Int) : Variable(nameToken, nameToken, index)
}
