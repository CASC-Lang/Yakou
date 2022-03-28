package org.casc.lang.table

import org.casc.lang.compilation.AbstractPreference
import org.objectweb.asm.Opcodes
import java.lang.reflect.Array

data class ArrayType(
    var baseType: Type,
    override val typeName: String = "${baseType.typeName}[]",
    override val descriptor: String = "[${baseType.descriptor}",
    override val internalName: String = "[${baseType.internalName}"
) : Type {
    companion object {
        fun fromDimension(baseType: Type, dimension: Int): Type {
            var arrayType = baseType

            for (i in 0 until dimension)
                arrayType = ArrayType(arrayType)

            return arrayType
        }
    }

    val dimension by lazy {
        getDimensionSize()
    }

    override fun type(preference: AbstractPreference): Class<*> =
        Array.newInstance(baseType.type(preference), 0).javaClass

    override val loadOpcode: Int = Opcodes.ALOAD
    override val storeOpcode: Int = Opcodes.ASTORE
    override val returnOpcode: Int = Opcodes.ARETURN

    override fun asCASCStyle(): String =
        "${"[".repeat(dimension)}${getFoundationType().asCASCStyle()}${"]".repeat(dimension)}"

    private fun getDimensionSize(): Int {
        var dim = 1
        var lastType = baseType

        while (lastType is ArrayType) {
            lastType = lastType.baseType
            dim++
        }

        return dim
    }

    // getFoundationType returns basic type of array, e.g. `int[][]` returns `int`
    fun getFoundationType(): Type {
        var lastType = baseType

        while (lastType is ArrayType)
            lastType = lastType.baseType

        return lastType
    }

    fun setFoundationType(type: Type) {
        if (baseType is ArrayType) {
            (baseType as ArrayType).setFoundationType(type)
        } else baseType = type
    }

    fun getContentLoadOpcode(): Int? = when (baseType) {
        is ArrayType, is ClassType -> Opcodes.AALOAD
        is PrimitiveType ->
            if ((baseType as PrimitiveType).isNumericType()) {
                when (baseType) {
                    PrimitiveType.F64 -> Opcodes.DALOAD
                    PrimitiveType.F32 -> Opcodes.FALOAD
                    PrimitiveType.I64 -> Opcodes.LALOAD
                    PrimitiveType.I32 -> Opcodes.IALOAD
                    PrimitiveType.I16 -> Opcodes.SALOAD
                    PrimitiveType.I8 -> Opcodes.BALOAD
                    else -> null
                }
            } else if (baseType == PrimitiveType.Char) Opcodes.IALOAD
            else if (baseType == PrimitiveType.Str) Opcodes.AALOAD
            else null
    }

    fun getContentStoreOpcode(): Int? = when (baseType) {
        is ArrayType, is ClassType -> Opcodes.AASTORE
        is PrimitiveType ->
            if ((baseType as PrimitiveType).isNumericType()) {
                when (baseType) {
                    PrimitiveType.F64 -> Opcodes.DASTORE
                    PrimitiveType.F32 -> Opcodes.FASTORE
                    PrimitiveType.I64 -> Opcodes.LASTORE
                    PrimitiveType.I32 -> Opcodes.IASTORE
                    PrimitiveType.I16 -> Opcodes.SASTORE
                    PrimitiveType.I8 -> Opcodes.BASTORE
                    else -> null
                }
            } else if (baseType == PrimitiveType.Char) Opcodes.IASTORE
            else if (baseType == PrimitiveType.Str) Opcodes.AASTORE
            else null
    }
}
