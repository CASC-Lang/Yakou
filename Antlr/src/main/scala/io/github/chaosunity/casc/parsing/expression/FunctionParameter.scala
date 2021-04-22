package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type

class FunctionParameter(`type`: Type, private val _name: String) extends Expression(`type`) {
    def name: String = _name
}
