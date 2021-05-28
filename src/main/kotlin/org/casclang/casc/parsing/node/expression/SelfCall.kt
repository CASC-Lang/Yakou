package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.Type

data class SelfCall(override val arguments: List<Argument> = listOf()) : Call<SelfCall> {
    companion object {
        const val SELF_CALL_ID = "self"
    }

    override val identifier: String = SELF_CALL_ID
    override val type: Type = BuiltInType.VOID
}