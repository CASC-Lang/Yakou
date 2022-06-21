package org.yakou.lang.bind

import org.yakou.lang.ast.Item
import org.yakou.lang.ast.Path

data class Field(
    val access: Int,
    val packagePath: String,
    val classPath: String,
    val name: String,
    val type: TypeInfo
) {
    companion object {
        fun fromField(field: java.lang.reflect.Field): Field =
            Field(
                field.modifiers,
                field.declaringClass.packageName.replace(".", "::"),
                field.declaringClass.typeName.split('.').last().replace("$", "::"),
                field.name,
                TypeInfo.fromClass(field.type)
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
            const.typeInfo
        )

        fun fromField(
            packageSimplePath: Path.SimplePath,
            classSimplePath: Path.SimplePath,
            staticField: Item.StaticField,
            vararg additionalAccessFlags: Int
        ): Field = Field(
            staticField.modifiers.sum(*additionalAccessFlags),
            packageSimplePath.toString(),
            classSimplePath.toString().ifBlank { "PackageYk" },
            staticField.identifier.literal,
            staticField.typeInfo
        )
    }

    val descriptor: String = type.descriptor
    lateinit var ownerTypeInfo: TypeInfo.Class

    val qualifiedOwnerPath: String by lazy {
        (if (packagePath.isBlank()) "" else "$packagePath::") + classPath
    }

    fun constToString(): String =
        "const ${toString()}"

    fun staticFieldToString(): String =
        "static ${toString()}"

    fun fieldToString(): String =
        toString()

    override fun toString(): String =
        "$name: $type"

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