package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.Type

data class Wrapped(val expression: Expression<*>) : Expression<Wrapped> {
    override val type: Type = expression.type
}
