package org.yakou.lang.bind

import org.objectweb.asm.Opcodes
import java.lang.reflect.Modifier
import java.lang.reflect.TypeVariable

sealed class TypeInfo {
    companion object {
        fun fromClass(clazz: java.lang.Class<*>): TypeInfo = when {
            clazz.isArray -> Array(fromClass(clazz.componentType()))
            clazz.isPrimitive -> PrimitiveType.fromClass(clazz)!!
            else -> Class(
                clazz.modifiers,
                standardizeTypeName(clazz.typeName),
                clazz.typeParameters.map { GenericConstraint.fromTypeVariable(clazz.typeParameters, it) },
                clazz.superclass?.let { fromClass(it) as Class },
                clazz.interfaces.map(::fromClass).map { it as Class }
            )
        }

        private fun standardizeTypeName(typeName: String): String =
            typeName.replace(".", "::")
    }

    abstract val internalName: String?
    abstract val descriptor: String
    abstract val storeOpcode: Int
    abstract val loadOpcode: Int
    abstract val returnOpcode: Int

    infix fun promote(otherTypeInfo: TypeInfo): Primitive? {
        val leftPrimitiveType =
            PrimitiveType.primitiveTypes.find { it.jvmClazz.descriptorString() == descriptor || it.wrappedJvmClazz.descriptorString() == descriptor }
        val rightPrimitiveType =
            PrimitiveType.primitiveTypes.find { it.jvmClazz.descriptorString() == otherTypeInfo.descriptor || it.wrappedJvmClazz.descriptorString() == otherTypeInfo.descriptor }

        if (leftPrimitiveType == null || rightPrimitiveType == null) {
            return null
        }

        if (leftPrimitiveType.precedence == -1 || rightPrimitiveType.precedence == -1) {
            return null
        }

        return if (leftPrimitiveType.precedence > rightPrimitiveType.precedence) {
            Primitive(leftPrimitiveType)
        } else if (leftPrimitiveType.precedence < rightPrimitiveType.precedence) {
            Primitive(rightPrimitiveType)
        } else {
            Primitive(leftPrimitiveType)
        }
    }

    fun canImplicitCast(otherTypeInfo: TypeInfo): Boolean {
        val boundResult = TypeChecker.canImplicitCast(this, otherTypeInfo)

        return boundResult == TypeChecker.BoundResult.SAME || boundResult == TypeChecker.BoundResult.SUBCLASS
    }

    fun canExplicitCast(otherTypeInfo: TypeInfo): Boolean =
        TypeChecker.canExplicitCast(this, otherTypeInfo) != TypeChecker.BoundResult.FAIL

    fun asPrimitive(): Primitive? =
        PrimitiveType.primitiveTypes
            .find { it.jvmClazz.descriptorString() == descriptor || it.wrappedJvmClazz.descriptorString() == descriptor }
            ?.let(::Primitive)

    class Primitive(val type: PrimitiveType) : TypeInfo() {
        companion object {
            val UNIT_TYPE_INFO = Primitive(PrimitiveType.Unit)
            val BOOL_TYPE_INFO = Primitive(PrimitiveType.Bool)
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
        val remOpcode: Int = when (type) {
            PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.IREM
            PrimitiveType.I64 -> Opcodes.LREM
            PrimitiveType.F32 -> Opcodes.FREM
            PrimitiveType.F64 -> Opcodes.DREM
            else -> -1
        }
        val ushrOpcode: Int = when (type) {
            PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.IUSHR
            PrimitiveType.I64 -> Opcodes.LUSHR
            else -> -1
        }
        val shrOpcode: Int = when (type) {
            PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.ISHR
            PrimitiveType.I64 -> Opcodes.LSHR
            else -> -1
        }
        val shlOpcode: Int = when (type) {
            PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.ISHL
            PrimitiveType.I64 -> Opcodes.LSHL
            else -> -1
        }
        val eqOpcode: Int = when (type) {
            PrimitiveType.Bool, PrimitiveType.Char, PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.IF_ICMPEQ
            PrimitiveType.I64 -> Opcodes.LCMP
            PrimitiveType.F32 -> Opcodes.FCMPG
            PrimitiveType.F64 -> Opcodes.DCMPG
            else -> -1
        }
        val neOpcode: Int = when (type) {
            PrimitiveType.Bool, PrimitiveType.Char, PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.IF_ICMPNE
            PrimitiveType.I64 -> Opcodes.LCMP
            PrimitiveType.F32 -> Opcodes.FCMPG
            PrimitiveType.F64 -> Opcodes.DCMPG
            else -> -1
        }
        override val storeOpcode: Int = when (type) {
            PrimitiveType.Bool, PrimitiveType.Char, PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.ISTORE
            PrimitiveType.I64 -> Opcodes.LSTORE
            PrimitiveType.F32 -> Opcodes.FSTORE
            PrimitiveType.F64 -> Opcodes.DSTORE
            else -> -1
        }
        override val loadOpcode: Int = when (type) {
            PrimitiveType.Bool, PrimitiveType.Char, PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.ILOAD
            PrimitiveType.I64 -> Opcodes.LLOAD
            PrimitiveType.F32 -> Opcodes.FLOAD
            PrimitiveType.F64 -> Opcodes.DLOAD
            else -> -1
        }
        override val returnOpcode: Int = when (type) {
            PrimitiveType.Unit -> Opcodes.RETURN
            PrimitiveType.Bool, PrimitiveType.Char, PrimitiveType.I8, PrimitiveType.I16, PrimitiveType.I32 -> Opcodes.IRETURN
            PrimitiveType.I64 -> Opcodes.LRETURN
            PrimitiveType.F32 -> Opcodes.FRETURN
            PrimitiveType.F64 -> Opcodes.DRETURN
        }

        override val internalName: String? = null
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
        listOf(),
        fromClass(Any::class.java) as Class,
        listOf()
    )

    open class Class(
        val access: Int,
        val standardTypePath: String,
        val genericParameters: List<GenericConstraint>,
        var superClassType: Class?,
        val interfaceTypes: List<Class>
    ) : TypeInfo(), TypeInfoVariable {
        companion object {
            val OBJECT_TYPE_INFO: Class = fromClass(Any::class.java) as Class
        }

        val isInterface: Boolean = Modifier.isInterface(access)
        val canonicalName: String = standardTypePath.replace("::", ".")
        final override val internalName: String = standardTypePath.replace("::", "/")
        final override val descriptor: String = "L$internalName;"
        override val storeOpcode: Int = Opcodes.ASTORE
        override val loadOpcode: Int = Opcodes.ALOAD
        override val returnOpcode: Int = Opcodes.ARETURN

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
        override val storeOpcode: Int = Opcodes.ASTORE
        override val loadOpcode: Int = Opcodes.ALOAD
        override val returnOpcode: Int = Opcodes.ARETURN

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

    class GenericConstraint(
        val genericParameterName: String,
        val boundType: BoundType,
        val varianceType: VarianceType,
        var bounds: MutableList<TypeInfoVariable> = mutableListOf()
    ) : TypeInfo(), TypeInfoVariable {
        companion object {
            fun fromTypeVariable(
                declaredTypeVariables: kotlin.Array<out TypeVariable<*>>,
                typeVariable: TypeVariable<*>
            ): GenericConstraint =
                GenericConstraint(
                    typeVariable.name,
                    when {
                        typeVariable.bounds.isNotEmpty() -> BoundType.UPPER
                        else -> BoundType.NONE
                    }, // TODO: Investigate how to get bound of type variable
                    VarianceType.INVARIANCE,
                    typeVariable.bounds.map {
                        if (declaredTypeVariables.find { typeVariable -> typeVariable.typeName == it.typeName } != null) {
                            GenericConstraint(
                                it.typeName,
                                when {
                                    typeVariable.bounds.isNotEmpty() -> BoundType.UPPER
                                    else -> BoundType.NONE
                                },
                                VarianceType.INVARIANCE
                            )
                        } else {
                            fromClass(java.lang.Class.forName(it.typeName)) as Class
                        }
                    }.toMutableList()
                )
        }

        override val internalName: String by lazy {
            bounds.firstOrNull()?.internalName ?: Class.OBJECT_TYPE_INFO.internalName
        }
        override val descriptor: String by lazy {
            bounds.firstOrNull()?.descriptor ?: Class.OBJECT_TYPE_INFO.descriptor
        }
        override val storeOpcode: Int = Opcodes.ASTORE
        override val loadOpcode: Int = Opcodes.ALOAD
        override val returnOpcode: Int = Opcodes.ARETURN

        enum class BoundType {
            UPPER,
            LOWER,
            NONE
        }

        enum class VarianceType {
            COVARIANCE,
            CONTRAVARIANCE,
            INVARIANCE
        }
    }

    interface TypeInfoVariable {
        val internalName: String
        val descriptor: String
    }
}
