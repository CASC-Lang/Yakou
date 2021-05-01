package io.github.chaosunity.casc.parsing.type

import jdk.internal.org.objectweb.asm.Opcodes.*

class ClassType(override val typeName: String) : Type {
    override val classType: Class<*> = Class.forName(typeName)
    override val descriptor: String = "L$classType;"
    override val internalName: String = typeName.replace('.', '/')
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
}