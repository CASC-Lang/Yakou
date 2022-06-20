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
    }

    lateinit var ownerTypeInfo: TypeInfo.Class

    val qualifiedOwnerPath: String by lazy {
        (if (packagePath.isBlank()) "" else "$packagePath::") + classPath
    }

    override fun toString(): String =
        "const $name: $type"

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