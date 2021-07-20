package org.casclang.casc.parsing

import org.casclang.casc.parsing.node.expression.ConstructorCall

data class Implementation(
    val superClassName: String,
    val superClassCtor: ConstructorCall?,
    val overrideFunctions: List<Function<*>>
)
