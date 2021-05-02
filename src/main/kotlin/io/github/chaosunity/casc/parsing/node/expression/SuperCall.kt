package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.Type

data class SuperCall(override val arguments: List<Argument>) : Call<SuperCall> {
    companion object {
        const val SUPER_CALL_ID = "super"
    }

    constructor() : this(listOf())

    override val identifier: String = SUPER_CALL_ID
    override val type: Type = BuiltInType.VOID
    override val negative: Boolean = false
}