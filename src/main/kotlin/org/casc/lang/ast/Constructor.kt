package org.casc.lang.ast

import org.casc.lang.table.*

data class Constructor(
    val ownerReference: Reference?,
    val parentReference: Reference?,
    val accessorToken: Token?,
    val parameters: List<Parameter>,
    val statements: List<Statement>,
    val parentConstructorArguments: List<Expression?>,
    var ownerType: Type? = null,
    var parentType: Type? = null,
    val accessor: Accessor = Accessor.fromString(accessorToken?.literal),
    var parameterTypes: List<Type?>? = listOf()
) : HasDescriptor, HasAccess, HasSignature {
    override val descriptor: String
        get() = "(${
            parameterTypes?.fold("") { s, type ->
                s + type?.descriptor
            }
        })V"
    override val accessFlag: Int =
        accessor.access

    override fun asSignature(): FunctionSignature =
        FunctionSignature(
            ownerReference!!,
            companion = true,
            mutable = false,
            accessor,
            "<init>",
            parameterTypes!!.mapNotNull { it },
            ownerType!!
        )
}
