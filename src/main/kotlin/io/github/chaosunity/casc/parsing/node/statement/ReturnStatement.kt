package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression

data class ReturnStatement(val expression: Expression<*>) : Statement<ReturnStatement>
