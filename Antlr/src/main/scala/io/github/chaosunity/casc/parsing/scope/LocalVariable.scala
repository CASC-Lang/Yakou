package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.Expression

class LocalVariable(`type`: Type, val name: String) extends Expression(`type`) {
}
