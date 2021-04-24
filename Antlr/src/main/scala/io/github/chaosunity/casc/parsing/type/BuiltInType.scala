package io.github.chaosunity.casc.parsing.`type`

import scala.collection.mutable

object BuiltInType extends Enumeration {
    type BuiltInType = BuiltInTypes

    case class BuiltInTypes(private val _name: String,
                            private val _typeClass: Class[_],
                            private val _descriptor: String) extends Val(_name) with Type {
        override var name: String = _name
        override var `type`: Class[_] = _typeClass
        override var descriptor: String = _descriptor
        override var internalName: String = _descriptor
    }

    final val BOOLEAN = BuiltInType("bool", classOf[Boolean], "Z")
    final val INT = BuiltInType("int", classOf[Int], "I")
    final val CHAR = BuiltInType("char", classOf[Char], "C")
    final val BYTE = BuiltInType("byte", classOf[Byte], "B")
    final val SHORT = BuiltInType("short", classOf[Short], "S")
    final val LONG = BuiltInType("long", classOf[Long], "J")
    final val FLOAT = BuiltInType("float", classOf[Float], "F")
    final val DOUBLE = BuiltInType("double", classOf[Double], "D")
    final val STRING = BuiltInType("string", classOf[String], "Ljava/lang/String;")
    final val BOOLEAN_ARR = BuiltInType("bool[]", classOf[Array[Boolean]], "[B")
    final val INT_ARR = BuiltInType("int[]", classOf[Array[Int]], "[I")
    final val CHAR_ARR = BuiltInType("char[]", classOf[Array[Char]], "[C")
    final val BYTE_ARR = BuiltInType("byte[]", classOf[Array[Byte]], "[B")
    final val SHORT_ARR = BuiltInType("short[]", classOf[Array[Short]], "[S")
    final val LONG_ARR = BuiltInType("long[]", classOf[Array[Long]], "[J")
    final val FLOAT_ARR = BuiltInType("float[]", classOf[Array[Float]], "[F")
    final val DOUBLE_ARR = BuiltInType("double[]", classOf[Array[Double]], "[D")
    final val STRING_ARR = BuiltInType("string[]", classOf[Array[String]], "[Ljava/lang/String;")
    final val NONE = BuiltInType("", null, "")
    final val VOID = BuiltInType("void", classOf[Unit], "V")

    final val enumSet = List(
        BOOLEAN,
        INT,
        CHAR,
        BYTE,
        SHORT,
        LONG,
        FLOAT,
        DOUBLE,
        STRING,
        BOOLEAN_ARR,
        INT_ARR,
        CHAR_ARR,
        BYTE_ARR,
        SHORT_ARR,
        LONG_ARR,
        FLOAT_ARR,
        DOUBLE_ARR,
        STRING_ARR,
        NONE,
        VOID
    )

    protected def BuiltInType(name: String, typeClass: Class[_], descriptor: String): BuiltInType =
        new BuiltInType(name, typeClass, descriptor)
}