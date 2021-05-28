package org.casclang.casc.parsing.node.statement

import org.casclang.casc.parsing.scope.Scope

data class Block(val scope: Scope, val statements: List<Statement<*>> = listOf()) : Statement<Block>
