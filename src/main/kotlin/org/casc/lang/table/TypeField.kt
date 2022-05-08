package org.casc.lang.table

import org.casc.lang.ast.Accessor

data class TypeField(
    val ownerReference: Reference?,
    val companion: Boolean,
    val mutable: Boolean,
    override val accessor: Accessor,
    val name: String,
    val type: Type
) : HasAccessor
