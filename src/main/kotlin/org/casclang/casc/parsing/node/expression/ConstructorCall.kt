package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.parsing.type.Type

data class ConstructorCall(private val className: String, override val arguments: List<Argument> = listOf()) :
    Call<ConstructorCall> {
    override val identifier: String = className
    override val type: Type = ClassType(className)
}