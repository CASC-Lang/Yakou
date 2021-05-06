package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression

class PrintlnStatement(expression: Expression<*>) : OutputStatement<PrintlnStatement>(expression)
