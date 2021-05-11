package io.github.chaosunity.casc.parsing.type

import jdk.internal.org.objectweb.asm.Opcodes.*

class ClassType(override val typeName: String) : Type {
    override fun classType(): Class<*> = try {
        Class.forName(typeName)
    } catch (e: ClassNotFoundException) {
        throw RuntimeException()
    }

    override val internalName: String = typeName.replace('.', '/')
    override val descriptor: String = "L$internalName;"
    override val loadVariableOpcode: Int = ALOAD
    override val storeVariableOpcode: Int = ASTORE
    override val returnOpcode: Int = ARETURN
    override val addOpcode: Int
        get() = throw RuntimeException("Addition operation is not supported for custom objects")
    override val subtractOpcode: Int
        get() = throw RuntimeException("Subtraction operation is not supported for custom objects")
    override val multiplyOpcode: Int
        get() = throw RuntimeException("Multiplication operation is not supported for custom objects")
    override val divideOpcode: Int
        get() = throw RuntimeException("Division operation is not supported for custom objects")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val classType = other as ClassType
        return typeName == classType.typeName
    }

    override fun hashCode(): Int {
        return typeName.hashCode()
    }
}