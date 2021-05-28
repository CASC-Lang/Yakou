package org.casclang.casc.parsing.node.statement

import org.casclang.casc.parsing.node.expression.Expression

class PrintStatement(expression: Expression<*>) : OutputStatement<PrintStatement>(expression)