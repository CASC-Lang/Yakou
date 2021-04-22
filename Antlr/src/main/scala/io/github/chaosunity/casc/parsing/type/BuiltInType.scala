package io.github.chaosunity.casc.parsing.`type`

sealed abstract class BuiltInTypes extends Type

case class BuiltInType(private val _name: String,
                       private val _type: Class[_],
                       private val _descriptor: String) extends BuiltInTypes {
    def name: String = _name

    def `type`: Class[_] = _type

    def descriptor: String = _descriptor

    def internalName: String = descriptor
}

object BuiltInType {
    def enumSet: Set[BuiltInType] = Set(
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
}

object BOOLEAN extends BuiltInType("bool", classOf[Boolean], "Z")

object INT extends BuiltInType("int", classOf[Int], "I")

object CHAR extends BuiltInType("char", classOf[Char], "C")

object BYTE extends BuiltInType("byte", classOf[Byte], "B")

object SHORT extends BuiltInType("short", classOf[Short], "S")

object LONG extends BuiltInType("long", classOf[Long], "J")

object FLOAT extends BuiltInType("float", classOf[Float], "F")

object DOUBLE extends BuiltInType("double", classOf[Double], "D")

object STRING extends BuiltInType("string", classOf[String], "Ljava/lang/String;")

object BOOLEAN_ARR extends BuiltInType("bool[]", classOf[Array[Boolean]], "[B")

object INT_ARR extends BuiltInType("int[]", classOf[Array[Int]], "[I")

object CHAR_ARR extends BuiltInType("char[]", classOf[Array[Char]], "[C")

object BYTE_ARR extends BuiltInType("byte[]", classOf[Array[Byte]], "[B")

object SHORT_ARR extends BuiltInType("short[]", classOf[Array[Short]], "[S")

object LONG_ARR extends BuiltInType("long[]", classOf[Array[Long]], "[J")

object FLOAT_ARR extends BuiltInType("float[]", classOf[Array[Float]], "[F")

object DOUBLE_ARR extends BuiltInType("double[]", classOf[Array[Double]], "[D")

object STRING_ARR extends BuiltInType("string[]", classOf[Array[String]], "[Ljava/lang/String;")

object NONE extends BuiltInType("", null, "")

object VOID extends BuiltInType("void", classOf[Unit], "V")