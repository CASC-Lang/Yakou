package org.casc.lang.ast

import org.casc.lang.table.*
import org.objectweb.asm.Opcodes

data class Function(
    val ownerReference: Reference?,
    val accessorToken: Token?,
    val mutKeyword: Token?,
    val compKeyword: Token?,
    val name: Token?,
    val parameters: List<Parameter>,
    val returnTypeReference: Reference?,
    val statements: List<Statement>,
    val accessor: Accessor = Accessor.fromString(accessorToken?.literal),
    var parameterTypes: List<Type?>? = listOf(),
    var returnType: Type? = null
) : HasDescriptor, HasAccess, HasSignature {
    override val descriptor: String
        get() = "(${
            parameterTypes?.fold("") { s, type ->
                s + type?.descriptor
            }
        })${returnType?.descriptor}"
    override val accessFlag: Int =
        (mutKeyword?.let { Opcodes.ACC_FINAL } ?: 0) + accessor.access + (compKeyword?.let { Opcodes.ACC_STATIC } ?: 0)

    override fun asSignature() =
        FunctionSignature(
            ownerReference!!,
            compKeyword != null,
            mutKeyword != null,
            accessor,
            name?.literal ?: "",
            parameterTypes!!.mapNotNull { it },
            returnType!!
        )
}
