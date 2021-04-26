package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type

class EmptyExpression(_type: Type) extends Expression {
    override def `type`: Type = _type

    override def negative: Boolean = false
}
