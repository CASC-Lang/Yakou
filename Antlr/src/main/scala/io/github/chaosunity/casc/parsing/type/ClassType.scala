package io.github.chaosunity.casc.parsing.`type`

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes._

class ClassType(val _name: String) extends Type with Opcodes {
    override def name: String = _name

    override def classType: Class[_] = Class.forName(_name)

    override def internalName: String = name.replace(".", "/")

    override def descriptor: String = s"L$internalName;"

    override def loadVariableOpcode: Int = ALOAD

    override def storeVariableOpcode: Int = ASTORE

    override def returnOpcode: Int = ARETURN

    override def addOpcode: Int = throw new RuntimeException(s"Unknown addition operation for class '$name'")

    override def subtractOpcode: Int = throw new RuntimeException(s"Unknown subtraction operation for class '$name'")

    override def multiplyOpcode: Int = throw new RuntimeException(s"Unknown multiplication operation for class '$name'")

    override def divideOpcode: Int = throw new RuntimeException(s"Unknown division operation for class '$name'")
}
