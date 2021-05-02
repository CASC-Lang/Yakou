package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.parsing.type.Type

data class ConstructorCall(private val className: String, override val arguments: List<Argument>) : Call<ConstructorCall> {
    constructor(identifier: String) : this(identifier, listOf())

    override val identifier: String = className
    override val type: Type = ClassType(className)
    override val negative: Boolean = false
}