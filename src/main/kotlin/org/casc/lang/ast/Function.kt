package org.casc.lang.ast

import org.casc.lang.table.*
import org.casc.lang.utils.getOrElse
import org.objectweb.asm.Opcodes

data class Function(
    val ownerReference: Reference?,
    val accessorToken: Token?,
    val ovrdKeyword: Token?,
    val mutKeyword: Token?,
    val selfKeyword: Token?, // determine whether function is companion
    val name: Token?,
    val parameters: List<Parameter>,
    val returnTypeReference: Reference?,
    val statements: List<Statement>,
    val accessor: Accessor = Accessor.fromString(accessorToken?.literal),
    var ownerType: Type? = null,
    var parameterTypes: List<Type?>? = listOf(),
    var returnType: Type? = null
) : HasDescriptor, HasFlag, HasSignature {
    override val descriptor: String
        get() = "(${
            parameterTypes?.fold("") { s, type ->
                s + type?.descriptor
            }
        })${returnType?.descriptor}"
    override val flag: Int =
        mutKeyword.getOrElse(0, Opcodes.ACC_FINAL) + accessor.access + selfKeyword.getOrElse(Opcodes.ACC_STATIC)

    override fun asSignature() =
        FunctionSignature(
            ownerReference!!,
            selfKeyword != null,
            mutKeyword != null,
            accessor,
            name?.literal ?: "",
            parameterTypes!!.mapNotNull { it },
            returnType ?: PrimitiveType.Unit
        )
}
