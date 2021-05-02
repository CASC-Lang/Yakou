package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.Type

data class NegativeExpression(val expression: Expression<*>) : Expression<NegativeExpression> {
    override val type: Type = expression.type
}
