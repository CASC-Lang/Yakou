package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.LogicalOp
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.Type

data class ConditionalExpression(val leftExpression: Expression<*>, val rightExpression: Expression<*>, val logicalOp: LogicalOp) : Expression<ConditionalExpression> {
    override val type: Type = BuiltInType.BOOLEAN
    override val negative: Boolean = false
}
