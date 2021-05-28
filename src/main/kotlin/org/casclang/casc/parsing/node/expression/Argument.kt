package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.Type

data class Argument(val argumentName: String?, val expression: Expression<*>) : Expression<Argument> {
    override val type: Type = expression.type
}
