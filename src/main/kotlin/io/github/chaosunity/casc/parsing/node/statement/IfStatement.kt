package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression

data class IfStatement(
    val condition: Expression<*>,
    val trueStatement: Statement<*>,
    val falseStatement: Statement<*>? = null
) : Statement<IfStatement>
