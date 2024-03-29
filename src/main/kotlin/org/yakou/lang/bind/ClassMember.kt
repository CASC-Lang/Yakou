package org.yakou.lang.bind

import java.lang.reflect.Method
import java.lang.reflect.Modifier
import org.objectweb.asm.Opcodes
import org.yakou.lang.ast.Const
import org.yakou.lang.ast.Expression
import org.yakou.lang.ast.Func
import org.yakou.lang.ast.GenericDeclarationParameters
import org.yakou.lang.ast.Parameter
import org.yakou.lang.ast.Path
import org.yakou.lang.ast.PrimaryConstructor
import org.yakou.lang.ast.StaticField

sealed class ClassMember(val memberType: MemberType) : Symbol() {
    abstract val descriptor: String
    abstract val access: Int
    abstract val packagePath: String
    abstract val classPath: String
    abstract val name: String

    abstract val qualifiedOwnerPath: String
    abstract var ownerTypeInfo: TypeInfo.Class

    abstract val inline: Boolean

    abstract override val static: Boolean
    abstract override val mutable: Boolean
    abstract override val typeInfo: TypeInfo

    data class Constructor(
        override val access: Int,
        override val packagePath: String,
        override val classPath: String,
        val parameterTypeInfos: List<TypeInfo>,
    ) : ClassMember(MemberType.CONSTRUCTOR) {
        companion object {
            fun fromConstructor(
                constructor: java.lang.reflect.Constructor<*>,
            ): Constructor {
                val ctor = Constructor(
                    constructor.modifiers,
                    constructor.declaringClass.packageName.replace(".", "::"),
                    constructor.declaringClass.typeName.split('.').last().replace("$", "::"),
                    constructor.parameterTypes.map(TypeInfo.Companion::fromClass),
                )
                
                val ownerType = TypeInfo.fromClass(constructor.declaringClass) as TypeInfo.Class

                ctor.typeInfo = ownerType
                ctor.ownerTypeInfo = ownerType

                return ctor
            }

            fun fromPrimaryConstructor(
                table: Table,
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                primaryConstructor: PrimaryConstructor,
            ): Constructor {
                val constructor = Constructor(
                    primaryConstructor.modifiers.sum(-Opcodes.ACC_FINAL),
                    packageSimplePath.toString(),
                    classSimplePath.toString(),
                    primaryConstructor.parameters.map(Parameter::typeInfo),
                )

                val ownerType = table.findType(constructor.qualifiedOwnerPath)!!

                constructor.typeInfo = ownerType
                constructor.ownerTypeInfo = ownerType

                return constructor
            }
        }

        override val descriptor: String =
            "(${parameterTypeInfos.map(TypeInfo::descriptor).joinToString(separator = "")})V"

        override val name: String = "ctor"
        override lateinit var typeInfo: TypeInfo
        override lateinit var ownerTypeInfo: TypeInfo.Class
        override val inline: Boolean = false
        override val static: Boolean = true
        override val mutable: Boolean = false

        override val qualifiedOwnerPath: String by lazy {
            (if (packagePath.isBlank()) "" else "$packagePath::") + classPath
        }

        override fun toString(): String =
            "Self(self, ${parameterTypeInfos.joinToString(transform = TypeInfo::toString)}) -> $ownerTypeInfo"
    }

    data class Field(
        override val access: Int,
        override val packagePath: String,
        override val classPath: String,
        override val name: String,
        override val typeInfo: TypeInfo,
        override val static: Boolean,
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
                    inline = false,
                )

                fieldMember.ownerTypeInfo = TypeInfo.fromClass(field.declaringClass) as TypeInfo.Class

                return fieldMember
            }

            fun fromConstructorParameter(
                table: Table,
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                constructorParameter: PrimaryConstructor.ConstructorParameter,
            ): Field {
                val field = Field(
                    constructorParameter.modifiers.sum(),
                    packageSimplePath.toString(),
                    classSimplePath.toString(),
                    constructorParameter.name.literal,
                    constructorParameter.typeInfo,
                    static = false,
                    isConst = false,
                    inline = false,
                    propagateExpression = null,
                )

                field.ownerTypeInfo = table.findType(field.qualifiedOwnerPath)!!

                return field
            }

            fun fromConst(
                table: Table,
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                const: Const,
                vararg additionalAccessFlags: Int,
            ): Field {
                val field = Field(
                    const.modifiers.sum(*additionalAccessFlags),
                    packageSimplePath.toString(),
                    classSimplePath.toString().ifBlank { "PackageYk" },
                    const.identifier.literal,
                    const.typeInfo,
                    static = true,
                    isConst = true,
                    const.modifiers.containsKey(org.yakou.lang.ast.Modifier.Inline),
                    const.expression,
                )

                field.ownerTypeInfo = table.findType(field.qualifiedOwnerPath)!!

                return field
            }

            fun fromField(
                table: Table,
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                staticField: StaticField,
            ): Field {
                val field = Field(
                    staticField.modifiers.sum(Opcodes.ACC_STATIC),
                    packageSimplePath.toString(),
                    classSimplePath.toString().ifBlank { "PackageYk" },
                    staticField.identifier.literal,
                    staticField.typeInfo,
                    static = true,
                    isConst = false,
                    staticField.modifiers.containsKey(org.yakou.lang.ast.Modifier.Inline),
                    staticField.expression,
                )

                field.ownerTypeInfo = table.findType(field.qualifiedOwnerPath)!!

                return field
            }

            fun fromField(
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                field: org.yakou.lang.ast.Field,
            ): Field = Field(
                field.modifiers.sum(),
                packageSimplePath.toString(),
                classSimplePath.toString().ifBlank { "PackageYk" },
                field.identifier.literal,
                field.typeInfo,
                static = false,
                isConst = false,
                field.modifiers.containsKey(org.yakou.lang.ast.Modifier.Inline),
                field.expression,
            )
        }

        override val descriptor: String = typeInfo.descriptor
        override lateinit var ownerTypeInfo: TypeInfo.Class
        override val mutable: Boolean = !Modifier.isFinal(access)

        override val qualifiedOwnerPath: String by lazy {
            (if (packagePath.isBlank()) "" else "$packagePath::") + classPath
        }

        private fun constToString(): String =
            "const ${fieldToString()}"

        private fun staticFieldToString(): String =
            "static ${fieldToString()}"

        private fun fieldToString(): String =
            "$name: $typeInfo"

        override fun toString(): String = when {
            isConst -> constToString()
            static -> staticFieldToString()
            else -> fieldToString()
        }

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
        val genericParameters: List<TypeInfo.GenericConstraint>,
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
                    method.typeParameters.map {
                        TypeInfo.GenericConstraint.fromTypeVariable(
                            method.typeParameters,
                            it,
                        )
                    },
                    method.parameterTypes.map(TypeInfo.Companion::fromClass),
                    TypeInfo.fromClass(method.returnType),
                    false,
                )

                fn.ownerTypeInfo = TypeInfo.fromClass(method.declaringClass) as TypeInfo.Class

                return fn
            }

            fun fromFunction(
                table: Table,
                packageSimplePath: Path.SimplePath,
                classSimplePath: Path.SimplePath,
                function: Func,
                vararg additionalAccessFlags: Int,
            ): Fn {
                val fn = Fn(
                    function.modifiers.sum(*additionalAccessFlags),
                    packageSimplePath.toString(),
                    classSimplePath.toString().ifBlank { "PackageYk" },
                    function.identifier.literal,
                    function.genericDeclarationParameters?.parameters?.map(GenericDeclarationParameters.GenericDeclarationParameter::genericConstraint)
                        ?: listOf(),
                    function.parameters.map(Parameter::typeInfo),
                    function.returnTypeInfo,
                    function.modifiers.containsKey(org.yakou.lang.ast.Modifier.Inline),
                )

                fn.ownerTypeInfo = table.findType(fn.qualifiedOwnerPath)!!

                return fn
            }
        }

        override val descriptor: String =
            "(${parameterTypeInfos.map(TypeInfo::descriptor).joinToString(separator = "")})${returnTypeInfo.descriptor}"
        override val typeInfo: TypeInfo = returnTypeInfo
        override lateinit var ownerTypeInfo: TypeInfo.Class
        override val static: Boolean = Modifier.isStatic(access)
        override val mutable: Boolean = !Modifier.isFinal(access)

        override val qualifiedOwnerPath: String by lazy {
            (if (packagePath.isBlank()) "" else "$packagePath::") + classPath
        }

        override fun toString(): String =
            "$name(${if (!Modifier.isStatic(access)) "self, " else ""}${parameterTypeInfos.joinToString(transform = TypeInfo::toString)}) -> $returnTypeInfo"

        override fun equals(other: Any?): Boolean {
            if (other is Fn) {
                if (packagePath != other.packagePath) {
                    return false
                }
                if (classPath != other.classPath) {
                    return false
                }
                if (name != other.name) {
                    return false
                }
                if (parameterTypeInfos.size != other.parameterTypeInfos.size) {
                    return false
                }
                if (!parameterTypeInfos.zip(other.parameterTypeInfos).all { (l, r) -> l == r }) {
                    return false
                }
                if (returnTypeInfo != other.returnTypeInfo) {
                    return false
                }
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
