package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.Type

data class LengthCall(val owner: Expression<*>) : Call<LengthCall> {
    override val type: Type = BuiltInType.INT
    override val identifier: String = "length"
    override val arguments: List<Argument> = listOf()
}
