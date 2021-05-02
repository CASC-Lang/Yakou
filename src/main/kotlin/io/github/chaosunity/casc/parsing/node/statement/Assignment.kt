package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression

data class Assignment(val variableName: String, val expression: Expression<*>) : Statement<Assignment>