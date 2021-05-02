package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression

open class PrintStatement<T>(val expression: Expression<*>) : Statement<T> where T : PrintStatement<T>