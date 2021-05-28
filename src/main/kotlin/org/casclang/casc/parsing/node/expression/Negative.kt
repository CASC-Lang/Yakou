package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.Type

data class Negative(val expression: Expression<*>) : Expression<Negative> {
    override val type: Type = expression.type
}
