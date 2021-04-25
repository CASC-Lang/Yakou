package io.github.chaosunity.casc.parsing.statement

import io.github.chaosunity.casc.parsing.expression.Expression

class IfStatement(val condition: Expression,
                  val trueStatement: Statement,
                  val falseStatement: Statement) extends Statement {
}
