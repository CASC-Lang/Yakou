package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression
import io.github.chaosunity.casc.parsing.scope.Scope

data class ForLoopStatement(
    val initStatement: Statement<*>?,
    val conditionExpression: Expression<*>?,
    val postStatement: Statement<*>?,
    val statement: Statement<*>,
    val scope: Scope
) : ForStatement<ForLoopStatement>() {
    init {
        if (conditionExpression != null && !conditionExpression.type.isBool())
            throw RuntimeException("Cannot break loop by checking with type ${conditionExpression.type}.")
    }
}