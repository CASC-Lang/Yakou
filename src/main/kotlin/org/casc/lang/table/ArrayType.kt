package org.casc.lang.table

import org.casc.lang.compilation.Preference
import org.objectweb.asm.Opcodes
import java.lang.reflect.Array

data class ArrayType(
    val baseType: Type,
    override val typeName: String = "${baseType.typeName}[]",
    override val descriptor: String = "[${baseType.descriptor}",
    override val internalName: String = "${baseType.internalName}[]"
) : Type {
    override fun type(): Class<*> =
        Array.newInstance(baseType.type(), 0).javaClass

    override val loadOpcode: Int = Opcodes.ALOAD
    override val storeOpcode: Int = Opcodes.ASTORE
    override val returnOpcode: Int = Opcodes.ARETURN

    fun getContentLoadOpcode(): Int? = when (baseType) {
        is ArrayType, is ClassType -> Opcodes.AALOAD
        is PrimitiveType ->
            if (baseType.isNumericType()) {
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
            if (baseType.isNumericType()) {
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
