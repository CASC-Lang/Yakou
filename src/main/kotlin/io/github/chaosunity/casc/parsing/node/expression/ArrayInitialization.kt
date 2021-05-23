package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.ArrayType
import io.github.chaosunity.casc.parsing.type.Type

data class ArrayInitialization(val expressions: List<Expression<*>>) : Expression<ArrayInitialization> {
    override val type: ArrayType
        get() {
            val type = expressions.first().type

            if (!expressions.map(Expression<*>::type).all(type::equals))
                throw RuntimeException("Cannot initialize array with different types.")

            return if (type is ArrayType)ArrayType(type.baseType, type.dimension + 1)
            else ArrayType(type, 1)
        }
}
