package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.FunctionParameter

class FunctionSignature(private val _name: String,
                        private val _params: List[FunctionParameter],
                        private val _retType: Type) {
    def name: String = _name

    def parameters: List[FunctionParameter] = _params

    def returnType: Type = _retType
}
