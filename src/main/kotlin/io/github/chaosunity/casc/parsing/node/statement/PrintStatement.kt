package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression

class PrintStatement(expression: Expression<*>) : OutputStatement<PrintStatement>(expression)