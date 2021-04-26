package io.github.chaosunity.casc.parsing.`type`

trait Type {
    val name: String

    val classType: Class[_]

    val descriptor: String

    val internalName: String

    def loadVariableOpcode: Int

    def storeVariableOpcode: Int

    def returnOpcode: Int

    def addOpcode: Int

    def subtractOpcode: Int

    def multiplyOpcode: Int

    def divideOpcode: Int
}
