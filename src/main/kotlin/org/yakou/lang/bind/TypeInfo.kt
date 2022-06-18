package org.yakou.lang.bind

sealed class TypeInfo {
    data class Primitive(val type: PrimitiveType) : TypeInfo() {
        companion object {
            val UNIT_TYPE_INFO = Primitive(PrimitiveType.Unit)
        }
    }
    data class Type(val standardTypePath: String) : TypeInfo()
    data class Array(val innerType: TypeInfo) : TypeInfo() {
        val baseType: TypeInfo by lazy {
            var innerType = this.innerType

            while (innerType is Array)
                innerType = innerType.innerType

            innerType
        }
    }
}