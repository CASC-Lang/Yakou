package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.Expression

class LocalVariable(_type: Type, val name: String) extends Expression {
    override def `type`: Type = _type

    override def negative: Boolean = false
}
