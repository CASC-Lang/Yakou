package org.casc.lang.table

import org.casc.lang.compilation.AbstractPreference
import org.objectweb.asm.Opcodes

object TypeUtil {
    /**
     * By given full qualified type name, which might either be an array class type, a CASC-defined primitive type
     * , or a class type, will return either ArrayType, PrimitiveType, or ClassType.
     */
    fun asType(reference: Reference?, preference: AbstractPreference): Type? =
        reference?.let {
            asArrayType(reference, preference)
                ?: PrimitiveType.values.find { it.typeName == reference.fullQualifiedPath }
                ?: Table.findType(reference)
                ?: try {
                    val clazz = preference.classLoader?.loadClass(reference.fullQualifiedPath)

                    if (clazz == null) null
                    else ClassType(clazz)
                } catch (_: java.lang.Exception) {
                    null
                } catch (_: java.lang.Error) {
                    null
                }
        }

    fun asType(clazz: Class<*>?, preference: AbstractPreference): Type? = when {
        clazz == null -> null
        clazz.isArray -> asArrayType(Reference(clazz), preference)
        else -> PrimitiveType.values.find {
            it.type(preference) == clazz
        } ?: asType(Reference(clazz), preference)
    }

    private fun asArrayType(reference: Reference, preference: AbstractPreference): ArrayType? {
        val name = reference.fullQualifiedPath
        return when {
            name.length < 2 -> null
            name.substring(name.length - 2) == "[]" -> {
                val baseType = asType(Reference(name.substring(0 until name.length - 2)), preference)

                if (baseType == null) null
                else ArrayType(baseType)
            }
            else -> null
        }
    }

    fun canCast(from: Type?, to: Type?, preference: AbstractPreference): Boolean =
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
            } else if (from.type(preference) == to.type(preference)) true
            else canCast(from, PrimitiveType.fromClass(to.type(preference)), preference)
        } else if (from is ClassType && to is PrimitiveType) {
            if (to == PrimitiveType.Null) true
            else if (from.type(preference) == to.type(preference)) true
            else canCast(PrimitiveType.fromClass(from.type(preference)), to, preference)
        } else if (from is ClassType && to is ClassType) {
            if (from.parentClassName == null) false
            else canCast(asType(Reference(from.parentClassName), preference), to, preference)
        } else false

    fun getCommonType(type1: Type?, type2: Type?, preference: AbstractPreference): Type? {
        return if (type1 == null || type2 == null) null
        else if (type1 == type2) type1
        else if (type1.descriptor == type2.descriptor) type1
        else if (type1 == PrimitiveType.Str || type2 == PrimitiveType.Str) {
            if (type1 == PrimitiveType.Str && type2 is ClassType) ClassType(Any::class.java)
            else if (type2 == PrimitiveType.Str && type1 is ClassType) ClassType(Any::class.java)
            else null
        } else if (type1 is PrimitiveType && type2 is PrimitiveType) {
            if (type1 == PrimitiveType.Str || type2 == PrimitiveType.Str) null
            else if (PrimitiveType.promotionTable[type1]!! > PrimitiveType.promotionTable[type2]!!) type1
            else type2
        } else if (type1 is ClassType && type2 is PrimitiveType) {
            if (type1.type(preference) == type2.type(preference)) type2
            else getCommonType(PrimitiveType.fromClass(type1.type(preference)), type2, preference)
        } else if (type1 is ClassType && type2 is ClassType) {
            if (type1.parentClassName == null || type2.parentClassName == null) ClassType(Any::class.java)
            else {
                var commonClazz = type1.type(preference)
                val clazz2 = type2.type(preference)
                while (commonClazz?.isAssignableFrom(clazz2) != true)
                    commonClazz = commonClazz?.superclass
                return commonClazz?.let(::ClassType)
            }
        } else null
    }

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