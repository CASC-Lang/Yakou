package org.yakou.lang.bind

import org.yakou.lang.ast.*

data class Fn(
    val access: Int,
    override val packagePath: String,
    override val classPath: String,
    val name: String,
    val parameterTypeInfos: List<TypeInfo>,
    val returnTypeInfo: TypeInfo
) : Declaration {
    companion object {
        fun fromFunction(
            packageSimplePath: Path.SimplePath,
            classSimplePath: Path.SimplePath,
            function: Item.Function,
            vararg additionalAccessFlags: Int
        ): Fn {
            val packagePath = packageSimplePath.toString()
            val classPath = classSimplePath.toString()

            return Fn(
                function.modifiers.sum(*additionalAccessFlags),
                packagePath,
                classPath.ifBlank { "PackageYk" },
                function.name.literal,
                function.parameters.map(Parameter::typeInfo),
                function.returnTypeInfo
            )
        }
    }
    val descriptor: String = "(${parameterTypeInfos.map(TypeInfo::descriptor).joinToString(separator = "")})${returnTypeInfo.descriptor}"
    lateinit var ownerTypeInfo: TypeInfo.Class

    val qualifiedOwnerPath: String by lazy {
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