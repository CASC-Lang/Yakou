package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.Type

data class FieldCall(
    val owner: Expression<*>,
    override val identifier: String,
    override val type: Type,
    val static: Boolean
) : Call<FieldCall> {
    override val arguments: List<Argument> = listOf()
}