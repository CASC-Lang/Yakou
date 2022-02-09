package org.casc.lang.ast

import org.casc.lang.table.HasAccess
import org.casc.lang.table.HasDescriptor
import org.casc.lang.table.Reference
import org.casc.lang.table.Type

data class Constructor(
    val ownerReference: Reference?,
    val accessorToken: Token?,
    val parameters: List<Parameter>,
    val statements: List<Statement>,
    val accessor: Accessor = Accessor.fromString(accessorToken?.literal),
    var parameterTypes: List<Type?>? = listOf(),
    val ownerType: Type?
) : HasDescriptor, HasAccess {
    override val descriptor: String
        get() = "(${
            parameterTypes?.fold("") { s, type ->
                s + type?.descriptor
            }
        })V"
    override val accessFlag: Int =
        accessor.access
}
