package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression

abstract class OutputStatement<T>(val expression: Expression<*>) : Statement<T> where T : OutputStatement<T>