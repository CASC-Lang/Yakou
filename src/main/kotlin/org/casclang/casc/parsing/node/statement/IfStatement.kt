package org.casclang.casc.parsing.node.statement

import org.casclang.casc.parsing.node.expression.Expression

data class IfStatement(
    val condition: Expression<*>,
    val trueStatement: Statement<*>,
    val falseStatement: Statement<*>? = null
) : Statement<IfStatement>
