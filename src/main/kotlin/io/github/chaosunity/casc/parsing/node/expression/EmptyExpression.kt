package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.Type

data class EmptyExpression(override val type: Type) : Expression<EmptyExpression> {
    override val negative: Boolean = false
}