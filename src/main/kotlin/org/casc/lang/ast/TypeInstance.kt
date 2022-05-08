package org.casc.lang.ast

import org.casc.lang.table.Reference

sealed class TypeInstance(open val packageReference: Reference?, open val typeReference: Reference?) {
    var impl: Impl? = null
    var traitImpls: List<TraitImpl>? = null
    var memberTypeInstances: List<TypeInstance>? = null
    abstract val accessor: Accessor

    val reference: Reference by lazy {
        Reference(packageReference?.fullQualifiedPath, typeReference?.fullQualifiedPath)
    }
}