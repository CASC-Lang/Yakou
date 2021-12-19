package org.casc.lang.ast

import org.casc.lang.table.HasAccess
import org.casc.lang.table.HasDescriptor
import org.casc.lang.table.Reference
import org.casc.lang.table.Type
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
) : HasDescriptor, HasAccess {
    override val descriptor: String
        get() = "(${
            parameterTypes?.fold("") { s, type ->
                s + type?.descriptor
            }
        })${returnType?.descriptor}"
    override val accessFlag: Int =
        (if (mutKeyword == null) Opcodes.ACC_FINAL else 0) + accessor.access + (if (compKeyword == null) 0 else Opcodes.ACC_STATIC)
}
