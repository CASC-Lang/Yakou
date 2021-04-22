package io.github.chaosunity.casc.parsing.statement

import io.github.chaosunity.casc.parsing.expression.Expression

class PrintlnStatement(private var _expression: Expression) extends Statement {
    def expression: Expression = _expression
}
