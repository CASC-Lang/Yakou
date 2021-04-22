package io.github.chaosunity.casc.parsing.`type`

class ClassType(private var _name: String) extends Type {
    def name: String = _name

    def `type`: Class[_] = Class.forName(_name)

    def descriptor: String = "L" + internalName + ";"

    def internalName: String = name.replace(".", "/")
}
