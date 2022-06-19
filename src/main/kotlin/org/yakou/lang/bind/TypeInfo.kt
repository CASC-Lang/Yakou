package org.yakou.lang.bind

import org.objectweb.asm.Opcodes

sealed class TypeInfo {
    companion object {
        fun fromClass(clazz: java.lang.Class<*>): TypeInfo = when {
            clazz.isArray -> Array(fromClass(clazz.arrayType()))
            clazz.isPrimitive -> PrimitiveType.fromClass(clazz)!!
            else -> Class(
                clazz.modifiers,
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

    class Primitive(val type: PrimitiveType) : TypeInfo() {
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

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Primitive

            if (type != other.type) return false

            return true
        }

        override fun hashCode(): Int {
            return type.hashCode()
        }
    }

    class PackageClass(
        standardPackagePath: String
    ) : Class(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL, "$standardPackagePath::PackageYk", fromClass(Any::class.java) as Class, listOf())

    open class Class(
        val access: Int,
        val standardTypePath: String,
        val superClassType: Class?,
        val interfaceTypes: List<Class>
    ) : TypeInfo() {
        final override val internalName: String = standardTypePath.replace("::", "/")
        final override val descriptor: String = "L$internalName;"

        override fun toString(): String =
            standardTypePath

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Class

            if (standardTypePath != other.standardTypePath) return false

            return true
        }

        override fun hashCode(): Int {
            return standardTypePath.hashCode()
        }
    }

    class Array(val innerType: TypeInfo) : TypeInfo() {
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

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Array

            if (innerType != other.innerType) return false

            return true
        }

        override fun hashCode(): Int {
            return innerType.hashCode()
        }
    }
}