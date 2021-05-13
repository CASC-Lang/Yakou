package io.github.chaosunity.casc.parsing.node.expression

interface Reference<T> : Expression<T> where T : Reference<T> {
    val name: String
}