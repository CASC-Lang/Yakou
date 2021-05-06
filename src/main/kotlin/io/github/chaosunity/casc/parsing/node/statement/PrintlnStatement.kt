package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression

class PrintlnStatement(override val expression: Expression<*>) : Statement<PrintlnStatement>, Printable
