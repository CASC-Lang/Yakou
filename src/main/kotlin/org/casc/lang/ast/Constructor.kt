package org.casc.lang.ast

import org.casc.lang.table.*

data class Constructor(
    val ownerReference: Reference?,
    var parentReference: Reference?,
    val accessorToken: Token?,
    val newKeyword: Token,
    val parameters: List<Parameter>,
    val statements: List<Statement>,
    val superKeyword: Token?,
    val selfKeyword: Token?,
    val parentConstructorArguments: List<Expression?>,
    var ownerType: ClassType? = null,
    var parentType: ClassType? = null,
    val accessor: Accessor = Accessor.fromString(accessorToken?.literal),
    var parameterTypes: List<Type?> = listOf(),
    var parentConstructorArgumentsTypes: List<Type?> = listOf(),
    var parentConstructorSignature: FunctionSignature? = null
) : Method(), HasDescriptor, HasFlag, HasSignature {
    override val descriptor: String
        get() = "(${
            parameterTypes.fold("") { s, type ->
                s + type?.descriptor
            }
        })V"
    override val flag: Int =
        accessor.access

    override fun asSignature(): FunctionSignature =
        FunctionSignature(
            ownerReference!!,
            ownerType,
            companion = true,
            mutable = false,
            accessor,
            "<init>",
            parameterTypes.mapNotNull { it },
            ownerType!!
        )
}
