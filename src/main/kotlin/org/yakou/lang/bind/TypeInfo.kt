package org.yakou.lang.bind

sealed class TypeInfo {
    data class Primitive(val type: PrimitiveType) : TypeInfo() {
        companion object {
            val UNIT_TYPE_INFO = Primitive(PrimitiveType.Unit)
        }

        override fun toString(): String =
            type.typeLiteral
    }
    data class Type(val standardTypePath: String) : TypeInfo() {
        override fun toString(): String =
            standardTypePath
    }
    data class Array(val innerType: TypeInfo) : TypeInfo() {
        val baseType: TypeInfo by lazy {
            var innerType = this.innerType

            while (innerType is Array)
                innerType = innerType.innerType

            innerType
        }

        override fun toString(): String =
            "[$innerType]"
    }
}