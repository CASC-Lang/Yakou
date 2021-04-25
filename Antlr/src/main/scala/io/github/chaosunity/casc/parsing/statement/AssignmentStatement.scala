package io.github.chaosunity.casc.parsing.statement

import io.github.chaosunity.casc.parsing.expression.Expression

class AssignmentStatement(val variableName: String,
                          val expression: Expression) extends Statement {
    def this(declaration: VariableDeclaration) {
        this(declaration.name, declaration.expression)
    }
}
