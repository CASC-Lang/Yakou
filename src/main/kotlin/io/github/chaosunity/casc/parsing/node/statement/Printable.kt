package io.github.chaosunity.casc.parsing.node.statement

import io.github.chaosunity.casc.parsing.node.expression.Expression

sealed interface Printable {
    val expression: Expression<*>
}