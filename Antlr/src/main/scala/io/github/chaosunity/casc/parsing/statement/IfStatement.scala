package io.github.chaosunity.casc.parsing.statement

import io.github.chaosunity.casc.parsing.expression.Expression

class IfStatement(private val _condition: Expression,
                  private val _trueStatement: Statement,
                  private val _falseStatement: Statement) extends Statement {
    def condition: Expression = _condition

    def trueStatement: Statement = _trueStatement

    def falseStatement: Statement = _falseStatement
}
