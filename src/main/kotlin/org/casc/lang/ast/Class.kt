package org.casc.lang.ast

data class Class(
    val accessorToken: Token?,
    val classKeyword: Token?,
    val name: Token?,
    var functions: List<Function>,
    val accessor: Accessor = Accessor.fromString(accessorToken?.literal)
)
