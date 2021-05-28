package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.ArrayType
import org.casclang.casc.parsing.type.Type

data class ArrayDeclaration(
    val baseType: Type,
    val dimension: Int,
    val expressions: List<Expression<*>>
) : Expression<ArrayDeclaration> {
    override val type: ArrayType = ArrayType(baseType, dimension)
}
