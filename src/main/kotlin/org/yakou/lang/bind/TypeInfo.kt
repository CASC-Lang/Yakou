package org.yakou.lang.bind

sealed class TypeInfo {
    companion object {
        fun fromClass(clazz: java.lang.Class<*>): TypeInfo = when {
            clazz.isArray -> Array(fromClass(clazz.arrayType()))
            clazz.isPrimitive -> PrimitiveType.fromClass(clazz)!!
            else -> Class(
                standardizeTypeName(clazz.typeName),
                clazz.superclass?.let { fromClass(it) as Class },
                clazz.interfaces.map(::fromClass).map { it as Class }
            )
        }

        fun standardizeTypeName(typeName: String): String =
            typeName.replace(".", "::")
    }

    abstract val internalName: String?
    abstract val descriptor: String

    data class Primitive(val type: PrimitiveType) : TypeInfo() {
        companion object {
            val UNIT_TYPE_INFO = Primitive(PrimitiveType.Unit)
        }

        override val internalName: String? = when (type) {
            PrimitiveType.Str -> "java/lang/String"
            else -> null
        }
        override val descriptor: String = type.descriptor

        override fun toString(): String =
            type.typeLiteral
    }

    data class Class(
        val standardTypePath: String,
        val superClassType: Class?,
        val interfaceTypes: List<Class>
    ) : TypeInfo() {
        override val internalName: String = standardTypePath.replace("::", "/")
        override val descriptor: String = "L$internalName;"

        override fun toString(): String =
            standardTypePath
    }

    data class Array(val innerType: TypeInfo) : TypeInfo() {
        override val internalName: String? = null
        override val descriptor: String = "[${innerType.descriptor}"
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