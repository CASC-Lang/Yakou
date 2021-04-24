package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type

class VarReference(`type`: Type, private val _variableName: String) extends Expression(`type`) {
    def variableName: String = _variableName
}
