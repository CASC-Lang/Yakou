package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.scope.FunctionSignature
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.Type

data class FunctionCall(
    val signature: FunctionSignature?,
    override val arguments: List<Argument>,
    val owner: Expression<*>,
    val static: Boolean
) : Call<FunctionCall> {
    constructor(signature: FunctionSignature?, arguments: List<Argument>, ownerType: Type, static: Boolean) : this(
        signature,
        arguments,
        EmptyExpression(ownerType),
        static
    )

    override val type: Type = signature?.returnType ?: BuiltInType.VOID
    override val identifier: String = signature?.name ?: "Unknown"
}