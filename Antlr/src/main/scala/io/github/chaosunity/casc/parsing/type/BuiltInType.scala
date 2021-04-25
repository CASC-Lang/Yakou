package io.github.chaosunity.casc.parsing.`type`

import scala.collection.mutable

object BuiltInType extends Enumeration {
    type BuiltInType = BuiltInTypes

    case class BuiltInTypes(private val _name: String,
                            mandarinAlias: String,
                            private val _typeClass: Class[_],
                            private val _descriptor: String) extends Val(_name) with Type {
        override var name: String = _name
        override var `type`: Class[_] = _typeClass
        override var descriptor: String = _descriptor
        override var internalName: String = _descriptor

        def isAlias(literal: String): Boolean =
            name.equals(literal) || mandarinAlias.equals(literal)
    }

    final val BOOLEAN = BuiltInType("bool", "布林", classOf[Boolean], "Z")
    final val INT = BuiltInType("int", "整數", classOf[Int], "I")
    final val CHAR = BuiltInType("char", "字元", classOf[Char], "C")
    final val BYTE = BuiltInType("byte", "位元", classOf[Byte], "B")
    final val SHORT = BuiltInType("short", "短整數", classOf[Short], "S")
    final val LONG = BuiltInType("long", "長整數", classOf[Long], "J")
    final val FLOAT = BuiltInType("float", "浮點數", classOf[Float], "F")
    final val DOUBLE = BuiltInType("double", "倍浮點數", classOf[Double], "D")
    final val STRING = BuiltInType("string", "字串", classOf[String], "Ljava/lang/String;")
    final val BOOLEAN_ARR = BuiltInType("bool[]", "布林", classOf[Array[Boolean]], "[B")
    final val INT_ARR = BuiltInType("int[]", "整數[]", classOf[Array[Int]], "[I")
    final val CHAR_ARR = BuiltInType("char[]", "字元[]", classOf[Array[Char]], "[C")
    final val BYTE_ARR = BuiltInType("byte[]", "位元[]", classOf[Array[Byte]], "[B")
    final val SHORT_ARR = BuiltInType("short[]", "短整數[]", classOf[Array[Short]], "[S")
    final val LONG_ARR = BuiltInType("long[]", "長整數[]", classOf[Array[Long]], "[J")
    final val FLOAT_ARR = BuiltInType("float[]", "浮點數[]", classOf[Array[Float]], "[F")
    final val DOUBLE_ARR = BuiltInType("double[]", "倍浮點數[]", classOf[Array[Double]], "[D")
    final val STRING_ARR = BuiltInType("string[]", "字串[]", classOf[Array[String]], "[Ljava/lang/String;")
    final val NONE = BuiltInType("", "空值", null, "")
    final val VOID = BuiltInType("void", "空", classOf[Unit], "V")

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

    protected def BuiltInType(name: String, mandarinAlias: String, typeClass: Class[_], descriptor: String): BuiltInType =
        new BuiltInType(name, mandarinAlias, typeClass, descriptor)
}