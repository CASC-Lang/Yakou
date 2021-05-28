package org.casclang.casc.parsing.node.statement

import org.casclang.casc.parsing.node.expression.Expression
import org.casclang.casc.parsing.scope.CallingScope

data class Assignment(
    val variableName: String?,
    val variableReference: Expression<*>,
    val expression: Expression<*>,
    val initialAssignment: Boolean,
    val callingScope: CallingScope
) : Statement<Assignment>