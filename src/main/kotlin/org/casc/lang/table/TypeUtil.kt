package org.casc.lang.table

import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.compilation.Compilation
import org.objectweb.asm.Opcodes
import java.net.URLClassLoader

object TypeUtil {
    /**
     * asType takes qualified type name and globe scope to lookup types
     */
    fun asType(name: String, preference: AbstractPreference): Type? =
        asArrayType(name, preference)
            ?: PrimitiveType.values.find { it.typeName == name }
            ?: getLoadedType(name, preference)

    fun asType(reference: Reference?, preference: AbstractPreference): Type? =
        if (reference == null) null
        else asType(reference.toString(), preference)

    fun asType(clazz: Class<*>?, preference: AbstractPreference): Type? =
        if (clazz == null) null
        else if (clazz.isArray) asArrayType(clazz.typeName, preference)
        else PrimitiveType.values.find {
            it.type() == clazz
        } ?: asType(clazz.name, preference)

    private fun asArrayType(name: String, preference: AbstractPreference): ArrayType? =
        if (name.length < 2) null
        else if (name.substring(name.length - 2) == "[]") {
            val baseType = asType(name.substring(0 until name.length - 2), preference)

            if (baseType == null) null
            else ArrayType(baseType)
        } else null

    private fun getLoadedType(name: String, preference: AbstractPreference): Type? = try {
        Compilation.compileClass(preference, name)

        val clazz = preference.classLoader?.loadClass(name)

        if (clazz == null) null
        else ClassType(clazz)
    } catch (e: NoClassDefFoundError) {
        null
    } catch (e: ClassNotFoundException) {
        null
    }

    fun checkType(name: String, preference: AbstractPreference): Boolean =
        asType(name, preference) != null

    fun checkType(reference: Reference?, preference: AbstractPreference): Boolean =
        asType(reference, preference) != null

    fun canCast(from: Type?, to: Type?): Boolean =
        if (from == null || to == null) false
        else if (from == to) true
        else if (to.descriptor == to.descriptor) true // Prevent unconverted type being mismatched
        else if (from is PrimitiveType) {
            if (from == PrimitiveType.Null) {
                when (to) {
                    PrimitiveType.Str, PrimitiveType.Null -> true
                    else -> to !is PrimitiveType
                }
            } else if (to is PrimitiveType) {
                if (from == PrimitiveType.I32 && to == PrimitiveType.Char) true // Special case
                else if (!from.isNumericType() || !to.isNumericType()) false
                else PrimitiveType.promotionTable[from]!! <= PrimitiveType.promotionTable[to]!!
            } else if (from.type() == to.type()) true
            else canCast(from, PrimitiveType.fromClass(to.type()))
        } else if (from is ClassType && to is PrimitiveType) {
            if (to == PrimitiveType.Null) true
            else if (from.type() == to.type()) true
            else canCast(PrimitiveType.fromClass(from.type()), to)
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