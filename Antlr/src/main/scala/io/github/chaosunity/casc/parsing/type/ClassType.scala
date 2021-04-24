package io.github.chaosunity.casc.parsing.`type`

class ClassType(private var _name: String) extends Type {
    def name: String = _name

    def name_=(name: String): Unit = {}

    def `type`: Class[_] = Class.forName(_name)

    def type_=(`class`: Class[_]): Unit = {}

    def descriptor: String = "L" + internalName + ";"

    def descriptor_=(name: String): Unit = {}

    def internalName: String = name.replace(".", "/")

    def internalName_=(name: String): Unit = {}
}
