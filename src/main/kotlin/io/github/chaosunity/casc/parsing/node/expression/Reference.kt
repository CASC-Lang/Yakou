package io.github.chaosunity.casc.parsing.node.expression

interface Reference<T> : ArrayBridge where T : Reference<T> {
    val name: String
    val dimensions: List<Expression<*>>
}