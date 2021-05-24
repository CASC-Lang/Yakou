package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.Immutable
import io.github.chaosunity.casc.parsing.node.expression.LocalVariableReference
import io.github.chaosunity.casc.parsing.node.statement.Assignment
import io.github.chaosunity.casc.parsing.node.statement.VariableDeclaration
import io.github.chaosunity.casc.parsing.scope.Scope

class VariableDeclarationFactory(
    private val sf: StatementFactory,
    private val ef: ExpressionFactory,
    private val scope: Scope
) {
    fun generate(variableDeclaration: VariableDeclaration) {
        val expression = variableDeclaration.expression
        val variableName = variableDeclaration.variableName

        expression.accept(ef)

        val assignment = Assignment(
            variableName,
            LocalVariableReference(scope.getLocalVariable(variableName)),
            expression,
            scope.callingScope
        )

        sf.generate(assignment, true) // Bypass the limit of assigment
    }
}