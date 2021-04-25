package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type

class FunctionParameter(`type`: Type,
                        val name: String,
                        val defaultValue: Option[Expression],
                        negative: Boolean) extends Expression(`type`, negative) {
}
