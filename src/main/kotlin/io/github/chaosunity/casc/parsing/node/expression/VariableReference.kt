package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.Type

data class VariableReference(override val type: Type, override val negative: Boolean, val variableName: String) : Expression<VariableReference> {
}