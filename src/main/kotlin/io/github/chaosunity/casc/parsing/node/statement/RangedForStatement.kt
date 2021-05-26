package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression
import io.github.chaosunity.casc.parsing.scope.Scope

data class RangedForStatement(
    val iteratorVariable: Statement<*>,
    val startExpression: Expression<*>,
    val arrowText: String,
    val endExpression: Expression<*>,
    val statement: Statement<*>,
    val iteratorVarName: String,
    val scope: Scope
) : ForStatement<RangedForStatement>() {
    init {
        if (!startExpression.isInt() || !endExpression.isInt()) throw RuntimeException("For statement only supports integer range.s")
    }

    val stopTo = if (arrowText.endsWith('>')) arrowText.startsWith('-') else arrowText.endsWith('-')
    val forward = arrowText.endsWith('>')
}