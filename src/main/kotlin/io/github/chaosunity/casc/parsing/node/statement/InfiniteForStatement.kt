package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.scope.Scope

data class InfiniteForStatement(val statement: Statement<*>, val scope: Scope) : ForStatement<InfiniteForStatement>()