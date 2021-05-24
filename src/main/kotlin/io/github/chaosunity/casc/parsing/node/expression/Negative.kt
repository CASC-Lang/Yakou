package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.Type

data class Negative(val expression: Expression<*>) : Expression<Negative> {
    override val type: Type = expression.type
}
