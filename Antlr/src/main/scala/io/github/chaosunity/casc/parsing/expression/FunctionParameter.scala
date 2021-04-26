package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type

class FunctionParameter(_type: Type,
                        val name: String,
                        val defaultValue: Option[Expression],
                        _negative: Boolean) extends Expression {
    override def `type`: Type = _type

    override def negative: Boolean = _negative
}
