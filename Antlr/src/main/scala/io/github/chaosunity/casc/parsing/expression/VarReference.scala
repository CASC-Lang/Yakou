package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type

class VarReference(`type`: Type,
                   val variableName: String,
                   negative: Boolean) extends Expression(`type`, negative) {
}
