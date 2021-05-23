package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression
import io.github.chaosunity.casc.parsing.scope.CallingScope

data class Assignment(
    val variableName: String,
    val expression: Expression<*>,
    val callingScope: CallingScope,
    val dimensions: List<Expression<*>> = listOf()
) : Statement<Assignment>