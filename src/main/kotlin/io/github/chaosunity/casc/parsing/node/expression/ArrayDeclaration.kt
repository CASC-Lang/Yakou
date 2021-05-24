package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.ArrayType
import io.github.chaosunity.casc.parsing.type.Type

data class ArrayDeclaration(
    val baseType: Type,
    val dimension: Int,
    val expressions: List<Expression<*>>
) : Expression<ArrayDeclaration> {
    override val type: ArrayType = ArrayType(baseType, dimension)
}
