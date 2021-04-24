package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type

class Value(`type`: Type, private var _value: String) extends Expression(`type`) {
    def value: String = _value
}
