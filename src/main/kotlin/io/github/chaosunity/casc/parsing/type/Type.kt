package io.github.chaosunity.casc.parsing.type

interface Type {
    val typeName: String
    val classType: Class<*>?
    val descriptor: String
    val internalName: String

    val loadVariableOpcode: Int
    val storeVariableOpcode: Int
    val returnOpcode: Int
    val addOpcode: Int
    val subtractOpcode: Int
    val multiplyOpcode: Int
    val divideOpcode: Int
}