package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.FunctionParameter

import java.util

class FunctionSignature(val name: String,
                        val parameters: util.List[FunctionParameter],
                        val returnType: Type) {
}
