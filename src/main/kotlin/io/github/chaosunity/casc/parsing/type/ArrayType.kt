package io.github.chaosunity.casc.parsing.type

import jdk.internal.org.objectweb.asm.Opcodes.*

data class ArrayType(val baseType: Type, val dimension: Int) : Type {
    override val typeName: String = if (baseType.isBuiltInType()) baseType.internalName else baseType.typeName

    override fun classType(): Class<*>? = try {
        Class.forName(internalName)
    } catch (e: Exception) {
        throw RuntimeException()
    }

    override val descriptor: String = "${"[".repeat(dimension)}${baseType.descriptor}"
    override val internalName: String = "${typeName.replace('.', '/')}${"[]".repeat(dimension)}"
    override val loadVariableOpcode: Int = ALOAD
    override val storeVariableOpcode: Int = ASTORE
    override val returnOpcode: Int = ARETURN
    override val addOpcode: Int
        get() = throw RuntimeException("Addition operation is not supported for array")
    override val subtractOpcode: Int
        get() = throw RuntimeException("Subtraction operation is not supported for array")
    override val multiplyOpcode: Int
        get() = throw RuntimeException("Multiplication operation is not supported for array")
    override val divideOpcode: Int
        get() = throw RuntimeException("Division operation is not supported for array")
    override val negativeOpcode: Int
        get() = throw RuntimeException("Negate operation is not supported for array")
    override val typeOpcode: Int
        get() = throw RuntimeException("Type operation is not supported for array")
    override val arrayLoadOpcode: Int = AALOAD
    override val arrayStoreOpcode: Int = AASTORE
}
