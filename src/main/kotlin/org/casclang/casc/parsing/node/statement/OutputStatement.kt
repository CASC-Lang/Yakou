package org.casclang.casc.parsing.node.statement

import org.casclang.casc.parsing.node.expression.Expression

abstract class OutputStatement<T>(val expression: Expression<*>) : Statement<T> where T : OutputStatement<T>