package org.casc.lang.table

sealed interface Type : HasDescriptor {
    val typeName: String
    fun type(): Class<*>?
    val internalName: String

    val loadOpcode: Int
    val storeOpcode: Int
    val returnOpcode: Int
}