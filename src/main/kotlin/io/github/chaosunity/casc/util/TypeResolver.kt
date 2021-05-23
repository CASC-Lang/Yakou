package io.github.chaosunity.casc.util

import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.type.ArrayType
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.parsing.type.Type
import jdk.internal.org.objectweb.asm.Opcodes
import org.apache.commons.lang3.math.NumberUtils

object TypeResolver {
    fun getFromTypeReferenceContext(ctx: CASCParser.TypeReferenceContext?): Type {
        if (ctx == null) return BuiltInType.VOID

        return getTypeByName(ctx.text)
    }

    fun getFromTypeContext(ctx: CASCParser.TypeContext?): Type {
        if (ctx == null) return BuiltInType.VOID

        return getTypeByName(ctx.text)
    }

    fun getTypeByName(typeName: String): Type {
        if (typeName.endsWith("[]")) {
            val baseTypeName = typeName.replace("[]", "")
            val baseType = getTypeByName(baseTypeName)
            val dimension = typeName.removePrefix(baseTypeName).length / 2

            return ArrayType(baseType, dimension)
        }

        if (typeName == "java::lang::String") return BuiltInType.STRING

        val builtInType = getBuiltInType(typeName)

        if (builtInType != null) return builtInType

        val obfuscatedType = obfuscateBuiltInType(typeName)

        if (obfuscatedType != null) return obfuscatedType

        val classType = ClassType(typeName.replace("::", "."))

        try {
            classType.classType()
        } catch (e: Exception) {
            throw RuntimeException("Type '$typeName' does not exist.")
        }

        return classType
    }

    fun getTypeByValue(value: String): Type {
        if (value == "null") return BuiltInType.NULL

        if (value.isEmpty()) return BuiltInType.VOID

        if (NumberUtils.isCreatable(value)) {
            if (value.endsWith("L") || value.endsWith("l")) return BuiltInType.LONG
            if (value.endsWith("F") || value.endsWith("f")) return BuiltInType.FLOAT

            when {
                value.toIntOrNull() != null -> return BuiltInType.INT
                value.toDoubleOrNull() != null -> return BuiltInType.DOUBLE
            }
        } else if (value == "true" || value == "false") return BuiltInType.BOOLEAN

        return BuiltInType.STRING
    }

    fun getValueByString(value: String, type: Type): Any? =
        when {
            type.isInt() -> value.toInt()
            type.isLong() -> value.toLong()
            type.isFloat() -> value.toFloat()
            type.isDouble() -> value.toDouble()
            type.isBool() -> value.toBoolean()
            type.isString() -> value.removeSurrounding("\"", "\"")
            type == BuiltInType.NULL -> null
            else -> throw RuntimeException("Object is not implemented yet.")
        }

    fun getBuiltInType(typeName: String): BuiltInType? =
        BuiltInType.values().find { it.typeName == typeName }

    fun obfuscateBuiltInType(canonicalName: String): BuiltInType? =
        BuiltInType.values().find { it.originalName == canonicalName }
}