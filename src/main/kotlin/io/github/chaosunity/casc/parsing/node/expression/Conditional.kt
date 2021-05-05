package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.LogicalOp
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.Type

data class Conditional(val leftExpression: Expression<*>, val rightExpression: Expression<*>, val logicalOp: LogicalOp) : Expression<Conditional> {
    override val type: Type = BuiltInType.BOOLEAN
}
