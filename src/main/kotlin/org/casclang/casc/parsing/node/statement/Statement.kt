package org.casclang.casc.parsing.node.statement

import org.casclang.casc.bytecode.statement.StatementFactory
import org.casclang.casc.parsing.node.Node

interface Statement<T> : Node where T : Statement<T> {
    fun accept(factory: StatementFactory) = factory.generate(this)
}