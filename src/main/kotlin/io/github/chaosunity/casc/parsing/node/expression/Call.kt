package io.github.chaosunity.casc.parsing.node.expression

interface Call<T> : Expression<T> where T : Call<T> {
    val arguments: List<Argument>
    val identifier: String
}