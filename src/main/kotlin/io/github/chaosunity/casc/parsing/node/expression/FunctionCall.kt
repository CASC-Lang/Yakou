package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.type.Type

data class FunctionCall(
    val signature: FunctionSignature,
    override val arguments: List<Argument>,
    val owner: Expression<*>,
    val static: Boolean
) : Call<FunctionCall> {
    constructor(signature: FunctionSignature, arguments: List<Argument>, ownerType: Type, static: Boolean) : this(
        signature,
        arguments,
        EmptyExpression(ownerType),
        static
    )

    override val type: Type = signature.returnType
    override val identifier: String = signature.name
}