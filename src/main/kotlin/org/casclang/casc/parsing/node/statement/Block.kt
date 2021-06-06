package org.casclang.casc.parsing.node.statement

import org.casclang.casc.parsing.scope.Scope

data class Block(val scope: Scope, val statements: MutableList<Statement<*>> = mutableListOf()) : Statement<Block>
