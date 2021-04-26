package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.scope.FunctionSignature

import java.util

class FunctionCall(val signature: FunctionSignature,
                   val arguments: util.List[Expression],
                   private val _owner: Expression,
                   _negative: Boolean) extends Call {
    override def `type`: Type = signature.returnType

    override def identifier: String = signature.name

    override def negative: Boolean = _negative

    def functionName: String = signature.name

    def owner: Expression = _owner

    def ownerType: Type = _owner.`type`
}
