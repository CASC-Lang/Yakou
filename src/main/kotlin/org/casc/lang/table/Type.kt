package org.casc.lang.table

sealed interface Type: HasDescriptor {
    val typeName: String
    fun type(): Class<*>?
    val internalName: String
}