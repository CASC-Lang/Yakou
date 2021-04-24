package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.scope.FunctionSignature

import java.util

class FunctionCall(private var _signature: FunctionSignature,
                   private var _params: util.List[Expression],
                   private var _owner: Type) extends Expression(_signature.returnType) {
    def functionName: String = _signature.name

    def parameters: util.List[Expression] = _params

    def owner: Option[Type] = Option.apply(_owner)

    def signature: FunctionSignature = _signature
}
