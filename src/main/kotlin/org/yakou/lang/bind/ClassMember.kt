package org.yakou.lang.bind

import org.objectweb.asm.Opcodes
import org.yakou.lang.ast.ClassItem
import org.yakou.lang.ast.Item
import org.yakou.lang.ast.Parameter
import org.yakou.lang.ast.Path
import java.lang.reflect.Method

sealed class ClassMember(val memberType: MemberType): Symbol() {
    abstract val access: Int
    abstract val packagePath: String
    abstract val classPath: String
    abstract val name: String

    abstract val qualifiedOwnerPath: String
    abstract var ownerTypeInfo: TypeInfo.Class

    data class Field(
        override val access: Int,
        override val packagePath: String,
        override val classPath: String,
        override val name: String,
        override val typeInfo: TypeInfo,
        val isConst: Boolean,
    ) : ClassMember(MemberType.FIELD) {
        companion object {
            fun fromField(field: java.lang.reflect.Field): Field =
                Field(
                    field.modifiers,
                    field.declaringClass.packageName.replace(".", "::"),
                    field.declaringClass.typeName.split('.').last().replace("$", "::"),
                    field.name,
                    TypeInfo.fromClass(field.type),
                    false
                )

            fun fromConst(
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                const: Item.Const,
                vararg additionalAccessFlags: Int
            ): Field = Field(
                const.modifiers.sum(*additionalAccessFlags),
                packageSimplePath.toString(),
                classSimplePath.toString().ifBlank { "PackageYk" },
                const.identifier.literal,
                const.typeInfo,
                true
            )

            fun fromField(
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                staticField: Item.StaticField
            ): Field = Field(
                staticField.modifiers.sum(Opcodes.ACC_STATIC),
                packageSimplePath.toString(),
                classSimplePath.toString().ifBlank { "PackageYk" },
                staticField.identifier.literal,
                staticField.typeInfo,
                false
            )

            fun fromField(
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                field: ClassItem.Field
            ): Field = Field(
                field.modifiers.sum(),
                packageSimplePath.toString(),
                classSimplePath.toString().ifBlank { "PackageYk" },
                field.identifier.literal,
                field.typeInfo,
                false
            )
        }

        val descriptor: String = typeInfo.descriptor
        override lateinit var ownerTypeInfo: TypeInfo.Class

        override val qualifiedOwnerPath: String by lazy {
            (if (packagePath.isBlank()) "" else "$packagePath::") + classPath
        }

        fun constToString(): String =
            "const ${toString()}"

        fun staticFieldToString(): String =
            "static ${toString()}"

        fun fieldToString(): String =
            toString()

        override fun toString(): String =
            "$name: $typeInfo"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Field

            if (packagePath != other.packagePath) return false
            if (classPath != other.classPath) return false
            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            var result = packagePath.hashCode()
            result = 31 * result + classPath.hashCode()
            result = 31 * result + name.hashCode()
            return result
        }
    }

    data class Fn(
        override val access: Int,
        override val packagePath: String,
        override val classPath: String,
        override val name: String,
        val parameterTypeInfos: List<TypeInfo>,
        val returnTypeInfo: TypeInfo
    ) : ClassMember(MemberType.FUNCTION) {
        companion object {
            fun fromMethod(method: Method): Fn =
                Fn(
                    method.modifiers,
                    method.declaringClass.packageName.replace(".", "::"),
                    method.declaringClass.typeName.split('.').last().replace("$", "::"),
                    method.name,
                    method.parameters.map { TypeInfo.fromClass(it.type) },
                    TypeInfo.fromClass(method.returnType)
                )

            fun fromFunction(
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                function: Item.Function,
                vararg additionalAccessFlags: Int
            ): Fn = Fn(
                function.modifiers.sum(*additionalAccessFlags),
                packageSimplePath.toString(),
                classSimplePath.toString().ifBlank { "PackageYk" },
                function.identifier.literal,
                function.parameters.map(Parameter::typeInfo),
                function.returnTypeInfo
            )
        }

        val descriptor: String =
            "(${parameterTypeInfos.map(TypeInfo::descriptor).joinToString(separator = "")})${returnTypeInfo.descriptor}"
        override val typeInfo: TypeInfo = returnTypeInfo
        override lateinit var ownerTypeInfo: TypeInfo.Class

        override val qualifiedOwnerPath: String by lazy {
            (if (packagePath.isBlank()) "" else "$packagePath::") + classPath
        }

        override fun toString(): String =
            "$name(${parameterTypeInfos.joinToString(transform = TypeInfo::toString)}) -> $returnTypeInfo"

        override fun equals(other: Any?): Boolean {
            if (other is Fn) {
                if (packagePath != other.packagePath)
                    return false
                if (classPath != other.classPath)
                    return false
                if (name != other.name)
                    return false
                if (parameterTypeInfos.size != other.parameterTypeInfos.size)
                    return false
                if (!parameterTypeInfos.zip(other.parameterTypeInfos).all { (l, r) -> l == r })
                    return false
                if (returnTypeInfo != other.returnTypeInfo)
                    return false
                return true
            }
            return false
        }

        override fun hashCode(): Int {
            var result = packagePath.hashCode()
            result = 31 * result + classPath.hashCode()
            result = 31 * result + name.hashCode()
            result = 31 * result + parameterTypeInfos.hashCode()
            result = 31 * result + returnTypeInfo.hashCode()
            return result
        }
    }

    enum class MemberType {
        FIELD,
        FUNCTION;

        companion object {
            val values = values()
        }
    }
}
