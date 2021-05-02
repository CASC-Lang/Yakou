package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.Type

data class Parameter(override val type: Type, val name: String, val defaultValue: Expression<*>?) : Expression<Parameter>
