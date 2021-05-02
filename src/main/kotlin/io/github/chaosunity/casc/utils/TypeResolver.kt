package io.github.chaosunity.casc.utils

import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.parsing.type.Type
import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.math.NumberUtils

object TypeResolver {
    fun getFromTypeName(ctx: CASCParser.TypeContext?): Type {
        if (ctx == null) return BuiltInType.VOID

        val typeName = ctx.text

        if (typeName == "java.lang.String") return BuiltInType.STRING

        val builtInType = getBuiltInType(typeName)

        if (builtInType != null) return builtInType

        return ClassType(typeName)
    }

    fun getTypeByValue(value: String): Type {
        if (value.isEmpty()) return BuiltInType.VOID

        if (NumberUtils.isCreatable(value)) {
            when {
                value.toIntOrNull() != null -> return BuiltInType.INT
                value.toFloatOrNull() != null -> return BuiltInType.FLOAT
                value.toDoubleOrNull() != null -> return BuiltInType.DOUBLE
            }
        } else if (BooleanUtils.toBoolean(value)) return BuiltInType.BOOLEAN

        return BuiltInType.STRING
    }

    fun getValueByString(value: String, type: Type): Any =
        when {
            type.isInt() -> value.toInt()
            type.isFloat() -> value.toFloat()
            type.isDouble() -> value.toDouble()
            type.isBool() -> value.toBoolean()
            type.isString() -> value.removeSurrounding("\"", "\"")
            else -> throw RuntimeException("Object is not implemented yet.")
        }

    fun getBuiltInType(typeName: String): BuiltInType? =
        BuiltInType.values().find { it.typeName == typeName }
}