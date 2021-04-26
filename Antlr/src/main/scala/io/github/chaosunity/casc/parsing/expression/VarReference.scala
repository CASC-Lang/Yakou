package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type

class VarReference(_type: Type,
                   val variableName: String,
                   _negative: Boolean) extends Expression {
    override def `type`: Type = _type

    override def negative: Boolean = _negative
}
