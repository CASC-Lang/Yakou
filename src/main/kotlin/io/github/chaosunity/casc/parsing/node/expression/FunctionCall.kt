package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.Type
import io.github.chaosunity.casc.parsing.scope.FunctionSignature

data class FunctionCall(
    val signature: FunctionSignature,
    override val arguments: List<Argument>,
    val owner: Expression<*>
) : Call<FunctionCall> {
    constructor(signature: FunctionSignature, arguments: List<Argument>, ownerType: Type) : this(
        signature,
        arguments,
        EmptyExpression(ownerType)
    )

    override val type: Type = signature.returnType
    override val identifier: String = signature.name
    override val negative: Boolean = false
}