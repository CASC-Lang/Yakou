package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type

class Value(_type: Type,
            val value: String,
            _negative: Boolean) extends Expression {
    override def `type`: Type = _type

    override def negative: Boolean = _negative
}
