package io.github.chaosunity.casc.parsing.statement

import io.github.chaosunity.casc.parsing.expression.Expression

class VariableDeclaration(private val _name: String,
                          private val _expression: Expression) extends Statement {
    def name: String = _name

    def expression: Expression = _expression
}
