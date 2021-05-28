package org.casclang.casc.parsing.node.statement

import org.casclang.casc.parsing.node.expression.Expression

class PrintlnStatement(expression: Expression<*>) : OutputStatement<PrintlnStatement>(expression)
