package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.Type

data class Wrapped(val expression: Expression<*>) : Expression<Wrapped> {
    override val type: Type = expression.type
}
