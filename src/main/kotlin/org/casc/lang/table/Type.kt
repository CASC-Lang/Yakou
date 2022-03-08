package org.casc.lang.table

sealed interface Type : HasDescriptor {
    val typeName: String
    fun type(): Class<*>?
    val internalName: String

    val loadOpcode: Int
    val storeOpcode: Int
    val returnOpcode: Int

    fun asCASCStyle(): String

    fun getPackagePath(): String =
        typeName.split('.').dropLast(1).joinToString(".")

    fun getReference(): Reference =
        Reference(typeName)

    fun isSamePackage(type: Type?): Boolean = when (this) {
        is PrimitiveType -> type is PrimitiveType
        else -> getPackagePath() == type?.getPackagePath()
    }
}