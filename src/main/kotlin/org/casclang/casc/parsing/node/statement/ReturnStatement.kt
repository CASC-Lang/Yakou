package org.casclang.casc.parsing.node.statement

import org.casclang.casc.parsing.node.expression.Expression

data class ReturnStatement(val expression: Expression<*>) : Statement<ReturnStatement>
