package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.Type

data class FieldCall(val owner: Expression<*>, override val identifier: String, override val type: Type, val static: Boolean) : Call<FieldCall> {
    override val arguments: List<Argument> = listOf()
}