package org.casclang.casc.parsing.node.statement

import org.casclang.casc.parsing.node.expression.Expression

data class VariableDeclaration(val variableName: String, val expression: Expression<*>) : Statement<VariableDeclaration>
