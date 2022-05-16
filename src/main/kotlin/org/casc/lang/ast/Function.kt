package org.casc.lang.ast

import org.casc.lang.table.*
import org.casc.lang.utils.getOrElse
import org.objectweb.asm.Opcodes

data class Function(
    val ownerReference: Reference?,
    val accessorToken: Token?,
    val ovrdKeyword: Token?,
    val abstrKeyword: Token?,
    val mutKeyword: Token?,
    val fnKeyword: Token,
    val selfKeyword: Token?, // determine whether function is companion
    val name: Token?,
    val parameters: List<Parameter>,
    val returnTypeReference: Reference?,
    val statements: List<Statement>?,
    val accessor: Accessor = Accessor.fromString(accessorToken?.literal),
    var ownerType: ClassType? = null,
    var parameterTypes: List<Type?>? = listOf(),
    var returnType: Type? = null
) : Method(), HasDescriptor, HasFlag, HasSignature {
    override val descriptor: String
        get() = "(${
            parameterTypes?.fold("") { s, type ->
                s + type?.descriptor
            }
        })${returnType?.descriptor ?: "V"}"
    override val flag: Int by lazy {
        var flag = accessor.access
        flag += selfKeyword.getOrElse(0, Opcodes.ACC_STATIC)
        flag += statements.getOrElse(abstrKeyword.getOrElse(Opcodes.ACC_ABSTRACT), Opcodes.ACC_ABSTRACT)
        if (ownerType?.isTrait == false)
            flag += mutKeyword.getOrElse(0, Opcodes.ACC_FINAL)
        flag
    }
    var abstract: Boolean = abstrKeyword != null

    override fun asSignature() =
        FunctionSignature(
            ownerReference!!,
            ownerType,
            selfKeyword == null,
            mutKeyword != null,
            abstrKeyword != null,
            accessor,
            name?.literal ?: "",
            parameterTypes!!.mapNotNull { it },
            returnType ?: PrimitiveType.Unit
        )
}
