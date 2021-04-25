package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type

class Value(`type`: Type, val value: String) extends Expression(`type`) {
}
