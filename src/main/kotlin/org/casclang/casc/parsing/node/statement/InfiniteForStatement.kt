package org.casclang.casc.parsing.node.statement

import org.casclang.casc.parsing.scope.Scope

data class InfiniteForStatement(val statement: Statement<*>, val scope: Scope) : ForStatement<InfiniteForStatement>()