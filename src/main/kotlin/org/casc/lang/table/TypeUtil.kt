package org.casc.lang.table

import org.casc.lang.compilation.Preference
import org.objectweb.asm.Opcodes

object TypeUtil {
    /**
     * asType takes qualified type name and globe scope to lookup types
     */
    fun asType(name: String): Type? =
        asArrayType(name)
            ?: getLoadedType(name)
            ?: PrimitiveType.values.find { it.typeName == name }
    //      ?: TODO: Cache un-compiled local classes so all the types are properly handled

    fun asType(reference: Reference?): Type? =
        if (reference == null) null
        else asType(reference.toString())

    private fun asArrayType(name: String): ArrayType? =
        if (name.substring(name.length - 2 until name.length) == "[]") {
            val baseType = asType(name.substring(0 until name.length - 2))

            if (baseType == null) null
            else ArrayType(baseType)
        } else null

    private fun getLoadedType(name: String): Type? = try {
        val clazz = Preference.classLoader?.loadClass(name)

        if (clazz == null) null
        else ClassType(clazz)
    } catch (e: Exception) {
        null
    }

    fun checkType(name: String): Boolean =
        asType(name) != null

    fun checkType(reference: Reference?): Boolean =
        asType(reference) != null

    fun canCast(from: Type?, to: Type?): Boolean =
        if (from == null || to == null) false
        else if (from == to) true
        else if (from is PrimitiveType) {
            if (to is PrimitiveType) {
                if (from == PrimitiveType.I32 && to == PrimitiveType.Char) true // Special case
                else if (!from.isNumericType() || !to.isNumericType()) false
                else PrimitiveType.promotionTable[from]!! < PrimitiveType.promotionTable[to]!!
            } else if (to.type() != from.type()) false // TODO: Investigate should unboxed type able to auto-cast
            else false
        } else false // TODO: Support Inheritance Checking

    fun findPrimitiveCastOpcode(from: PrimitiveType, to: PrimitiveType): Int? =
        when (from) {
            PrimitiveType.F64 -> when (to) {
                PrimitiveType.F32 -> Opcodes.D2F
                PrimitiveType.I64 -> Opcodes.D2L
                PrimitiveType.I32, PrimitiveType.I16, PrimitiveType.I8 -> Opcodes.D2I
                else -> null
            }
            PrimitiveType.F32 -> when (to) {
                PrimitiveType.F64 -> Opcodes.F2D
                PrimitiveType.I64 -> Opcodes.F2L
                PrimitiveType.I32, PrimitiveType.I16, PrimitiveType.I8 -> Opcodes.F2I
                else -> null
            }
            PrimitiveType.I64 -> when (to) {
                PrimitiveType.F64 -> Opcodes.L2D
                PrimitiveType.F32 -> Opcodes.L2F
                PrimitiveType.I32, PrimitiveType.I16, PrimitiveType.I8 -> Opcodes.L2I
                else -> null
            }
            PrimitiveType.I32, PrimitiveType.I16, PrimitiveType.I8 -> when (to) {
                PrimitiveType.F64 -> Opcodes.I2D
                PrimitiveType.F32 -> Opcodes.I2F
                PrimitiveType.I64 -> Opcodes.I2L
                PrimitiveType.I16 -> Opcodes.I2S
                PrimitiveType.I8 -> Opcodes.I2B
                PrimitiveType.Char -> Opcodes.I2C
                else -> null
            }
            else -> null
        }
}