package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.bytecode.statement.StatementFactory
import io.github.chaosunity.casc.parsing.node.Node

interface Statement<T> : Node where T:Statement<T> {
    fun accept(factory: StatementFactory) = factory.generate(this)
}