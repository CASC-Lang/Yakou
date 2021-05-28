package org.casclang.casc.parsing.node.statement

import org.casclang.casc.parsing.node.expression.Expression
import org.casclang.casc.parsing.scope.Scope

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