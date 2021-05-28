package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.ArrayType

data class ArrayInitialization(val expressions: List<Expression<*>>) : Expression<ArrayInitialization> {
    override val type: ArrayType
        get() {
            val type = expressions.first().type

            if (!expressions.map(Expression<*>::type).all(type::equals))
                throw RuntimeException("Cannot initialize array with different types.")

            return if (type is ArrayType) ArrayType(type.baseType, type.dimension + 1)
            else ArrayType(type, 1)
        }
}
