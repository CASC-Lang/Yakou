package io.github.chaosunity.casc.parsing.`type`

trait Type {
    def name: String

    def classType: Class[_]

    def descriptor: String

    def internalName: String

    def loadVariableOpcode: Int

    def storeVariableOpcode: Int

    def returnOpcode: Int

    def addOpcode: Int

    def subtractOpcode: Int

    def multiplyOpcode: Int

    def divideOpcode: Int
}
