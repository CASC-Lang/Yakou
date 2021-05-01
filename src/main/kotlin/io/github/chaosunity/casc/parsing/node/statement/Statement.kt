package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.bytecode.statement.StatementFactory
import io.github.chaosunity.casc.parsing.node.Node

@FunctionalInterface
interface Statement : Node {
    fun accept(factory: StatementFactory)
}