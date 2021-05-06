package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.node.statement.Assignment
import io.github.chaosunity.casc.parsing.node.statement.VariableDeclaration

class VariableDeclarationFactory(private val sf: StatementFactory, private val ef: ExpressionFactory) {
    fun generate(variableDeclaration: VariableDeclaration) {
        val expression = variableDeclaration.expression

        expression.accept(ef)

        val assignment = Assignment(variableDeclaration.variableName, expression)

        assignment.accept(sf)
    }
}