package org.yakou.lang.bind

import org.objectweb.asm.Opcodes
import org.yakou.lang.ast.*
import java.lang.reflect.Method
import java.lang.reflect.Modifier

sealed class ClassMember(val memberType: MemberType) : Symbol() {
    abstract val access: Int
    abstract val packagePath: String
    abstract val classPath: String
    abstract val name: String

    abstract val qualifiedOwnerPath: String
    abstract var ownerTypeInfo: TypeInfo.Class

    abstract val inline: Boolean

    abstract override val mutable: Boolean

    data class Constructor(
        override val access: Int,
        override val packagePath: String,
        override val classPath: String,
        val parameterTypeInfos: List<TypeInfo>,
    ) : ClassMember(MemberType.CONSTRUCTOR) {
        companion object {
            fun fromPrimaryConstructor(
                table: Table,
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                primaryConstructor: PrimaryConstructor
            ): Constructor {
                val constructor = Constructor(
                    primaryConstructor.modifiers.sum(),
                    packageSimplePath.toString(),
                    classSimplePath.toString(),
                    primaryConstructor.parameters.map(Parameter::typeInfo)
                )

                val ownerType = table.findType(constructor.packagePath, constructor.classPath)!!

                constructor.typeInfo = ownerType
                constructor.ownerTypeInfo = ownerType

                return constructor
            }
        }
        override val name: String = "ctor"
        override lateinit var typeInfo: TypeInfo
        override lateinit var ownerTypeInfo: TypeInfo.Class
        override val inline: Boolean = false
        override val mutable: Boolean = false

        override val qualifiedOwnerPath: String by lazy {
            (if (packagePath.isBlank()) "" else "$packagePath::") + classPath
        }

        override fun toString(): String =
            "ctor(self, ${parameterTypeInfos.joinToString(transform = TypeInfo::toString)}) -> $ownerTypeInfo"
    }

    data class Field(
        override val access: Int,
        override val packagePath: String,
        override val classPath: String,
        override val name: String,
        override val typeInfo: TypeInfo,
        val isStatic: Boolean,
        val isConst: Boolean,
        override val inline: Boolean,
        val propagateExpression: Expression? = null,
    ) : ClassMember(MemberType.FIELD) {
        companion object {
            fun fromField(field: java.lang.reflect.Field): Field {
                val fieldMember = Field(
                    field.modifiers,
                    field.declaringClass.packageName.replace(".", "::"),
                    field.declaringClass.typeName.split('.').last().replace("$", "::"),
                    field.name,
                    TypeInfo.fromClass(field.type),
                    Modifier.isStatic(field.modifiers),
                    isConst = false,
                    inline = false
                )

                fieldMember.ownerTypeInfo = TypeInfo.fromClass(field.declaringClass) as TypeInfo.Class

                return fieldMember
            }

            fun fromConstructorParameter(
                table: Table,
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                constructorParameter: PrimaryConstructor.ConstructorParameter
            ): Field {
                val field = Field(
                    constructorParameter.modifiers.sum(),
                    packageSimplePath.toString(),
                    classSimplePath.toString(),
                    constructorParameter.name.literal,
                    constructorParameter.typeInfo,
                    isStatic = false,
                    isConst = false,
                    inline = false,
                    propagateExpression = null
                )

                field.ownerTypeInfo = table.findType(field.classPath, field.packagePath)!!

                return field
            }

            fun fromConst(
                table: Table,
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                const: Item.Const,
                vararg additionalAccessFlags: Int
            ): Field {
                val field = Field(
                    const.modifiers.sum(*additionalAccessFlags),
                    packageSimplePath.toString(),
                    classSimplePath.toString().ifBlank { "PackageYk" },
                    const.identifier.literal,
                    const.typeInfo,
                    isStatic = true,
                    isConst = true,
                    const.modifiers.hasModifier(org.yakou.lang.ast.Modifier.Inline),
                    const.expression
                )

                field.ownerTypeInfo = table.findType(field.classPath, field.packagePath)!!

                return field
            }

            fun fromField(
                table: Table,
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                staticField: Item.StaticField
            ): Field {
                val field = Field(
                    staticField.modifiers.sum(Opcodes.ACC_STATIC),
                    packageSimplePath.toString(),
                    classSimplePath.toString().ifBlank { "PackageYk" },
                    staticField.identifier.literal,
                    staticField.typeInfo,
                    isStatic = true,
                    isConst = false,
                    staticField.modifiers.hasModifier(org.yakou.lang.ast.Modifier.Inline),
                    staticField.expression
                )

                field.ownerTypeInfo = table.findType(field.classPath, field.packagePath)!!

                return field
            }

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
                isStatic = false,
                isConst = false,
                field.modifiers.hasModifier(org.yakou.lang.ast.Modifier.Inline),
                field.expression
            )
        }

        val descriptor: String = typeInfo.descriptor
        override lateinit var ownerTypeInfo: TypeInfo.Class
        override val mutable: Boolean = !Modifier.isFinal(access)

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
        val returnTypeInfo: TypeInfo,
        override val inline: Boolean,
    ) : ClassMember(MemberType.FUNCTION) {
        companion object {
            fun fromMethod(method: Method): Fn {
                val fn = Fn(
                    method.modifiers,
                    method.declaringClass.packageName.replace(".", "::"),
                    method.declaringClass.typeName.split('.').last().replace("$", "::"),
                    method.name,
                    method.parameters.map { TypeInfo.fromClass(it.type) },
                    TypeInfo.fromClass(method.returnType),
                    false
                )

                fn.ownerTypeInfo = TypeInfo.fromClass(method.declaringClass) as TypeInfo.Class

                return fn
            }

            fun fromFunction(
                table: Table,
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                function: Item.Function,
                vararg additionalAccessFlags: Int
            ): Fn {
                val fn = Fn(
                    function.modifiers.sum(*additionalAccessFlags),
                    packageSimplePath.toString(),
                    classSimplePath.toString().ifBlank { "PackageYk" },
                    function.identifier.literal,
                    function.parameters.map(Parameter::typeInfo),
                    function.returnTypeInfo,
                    function.modifiers.hasModifier(org.yakou.lang.ast.Modifier.Inline)
                )

                fn.ownerTypeInfo = table.findType(fn.packagePath, fn.classPath)!!

                return fn
            }
        }

        val descriptor: String =
            "(${parameterTypeInfos.map(TypeInfo::descriptor).joinToString(separator = "")})${returnTypeInfo.descriptor}"
        override val typeInfo: TypeInfo = returnTypeInfo
        override lateinit var ownerTypeInfo: TypeInfo.Class
        override val mutable: Boolean = !Modifier.isFinal(access)

        override val qualifiedOwnerPath: String by lazy {
            (if (packagePath.isBlank()) "" else "$packagePath::") + classPath
        }

        override fun toString(): String =
            "$name(${if (!Modifier.isStatic(access)) "self, " else ""}${parameterTypeInfos.joinToString(transform = TypeInfo::toString)}) -> $returnTypeInfo"

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
        CONSTRUCTOR,
        FIELD,
        FUNCTION;

        companion object {
            val values = values()
        }
    }
}
