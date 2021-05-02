package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.scope.Scope

data class Block(val scope: Scope, val statements: List<Statement<*>> = listOf()) : Statement<Block>
