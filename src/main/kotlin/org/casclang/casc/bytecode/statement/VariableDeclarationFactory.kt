package org.casclang.casc.bytecode.statement

import org.casclang.casc.bytecode.expression.ExpressionFactory
import org.casclang.casc.parsing.node.expression.LocalVariableReference
import org.casclang.casc.parsing.node.statement.Assignment
import org.casclang.casc.parsing.node.statement.VariableDeclaration
import org.casclang.casc.parsing.scope.Scope

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
            LocalVariableReference(scope.getLocalVariable(variableName)!!),
            expression,
            true,
            scope.callingScope
        )

        assignment.accept(sf)
    }
}