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

    infix fun promote(otherTypeInfo: TypeInfo): Primitive? {
        val leftPrimitiveType =
            PrimitiveType.primitiveTypes.find { it.jvmClazz.descriptorString() == descriptor || it.wrappedJvmClazz.descriptorString() == descriptor }
        val rightPrimitiveType =
            PrimitiveType.primitiveTypes.find { it.jvmClazz.descriptorString() == otherTypeInfo.descriptor || it.wrappedJvmClazz.descriptorString() == otherTypeInfo.descriptor }

        if (leftPrimitiveType == null || rightPrimitiveType == null)
            return null

        if (leftPrimitiveType.precedence == -1 || rightPrimitiveType.precedence == -1)
            return null

        return if (leftPrimitiveType.precedence > rightPrimitiveType.precedence) Primitive(leftPrimitiveType)
        else if (leftPrimitiveType.precedence < rightPrimitiveType.precedence) Primitive(rightPrimitiveType)
        else Primitive(leftPrimitiveType)
    }

    class Primitive(val type: PrimitiveType) : TypeInfo() {
        companion object {
            val UNIT_TYPE_INFO = Primitive(PrimitiveType.Unit)
        }

        val addOpcode: Int = when (type) {
            PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.IADD
            PrimitiveType.I64 -> Opcodes.LADD
            PrimitiveType.F32 -> Opcodes.FADD
            PrimitiveType.F64 -> Opcodes.DADD
            else -> -1
        }
        val subOpcode: Int = when (type) {
            PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.ISUB
            PrimitiveType.I64 -> Opcodes.LSUB
            PrimitiveType.F32 -> Opcodes.FSUB
            PrimitiveType.F64 -> Opcodes.DSUB
            else -> -1
        }
        val mulOpcode: Int = when (type) {
            PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.IMUL
            PrimitiveType.I64 -> Opcodes.LMUL
            PrimitiveType.F32 -> Opcodes.FMUL
            PrimitiveType.F64 -> Opcodes.DMUL
            else -> -1
        }
        val divOpcode: Int = when (type) {
            PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.IDIV
            PrimitiveType.I64 -> Opcodes.LDIV
            PrimitiveType.F32 -> Opcodes.FDIV
            PrimitiveType.F64 -> Opcodes.DDIV
            else -> -1
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
    ) : Class(
        Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL,
        "$standardPackagePath::PackageYk",
        fromClass(Any::class.java) as Class,
        listOf()
    )

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