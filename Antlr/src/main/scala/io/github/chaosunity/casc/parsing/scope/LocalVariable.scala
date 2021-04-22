package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.Expression

class LocalVariable(`type`: Type, private val _name: String) extends Expression(`type`) {
    def name: String = _name
}
