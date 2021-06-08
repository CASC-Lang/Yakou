package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.LogicalOp
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.Type

data class Conditional(
    val leftExpression: Expression<*>,
    val rightExpression: Expression<*>,
    val logicalOp: LogicalOp
) : Expression<Conditional> {
    override val type: Type = BuiltInType.BOOLEAN
}
