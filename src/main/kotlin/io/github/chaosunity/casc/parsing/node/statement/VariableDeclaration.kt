package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression

data class VariableDeclaration(val variableName: String, val expression: Expression<*>) : Statement<VariableDeclaration>
