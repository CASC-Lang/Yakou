package io.github.chaosunity.casc.parsing.statement

import io.github.chaosunity.casc.parsing.expression.Expression

class VariableDeclaration(val name: String,
                          val expression: Expression) extends Statement {
}
