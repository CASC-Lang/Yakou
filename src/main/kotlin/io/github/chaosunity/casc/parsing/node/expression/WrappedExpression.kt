package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.Type

data class WrappedExpression(val expression: Expression<*>) : Expression<WrappedExpression> {
    override val type: Type = expression.type
}
