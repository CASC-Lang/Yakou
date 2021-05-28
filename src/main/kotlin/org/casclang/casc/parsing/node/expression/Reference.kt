package org.casclang.casc.parsing.node.expression

interface Reference<T> : Expression<T> where T : Reference<T> {
    val name: String
}