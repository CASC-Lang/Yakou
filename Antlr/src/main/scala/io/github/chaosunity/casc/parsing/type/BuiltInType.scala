package io.github.chaosunity.casc.parsing.`type`

import io.github.chaosunity.casc.parsing.`type`.TypeSpecificOpcode.TypeSpecificOpcode

object BuiltInType extends Enumeration {
    type BuiltInType = BuiltInTypes

    case class BuiltInTypes(_name: String,
                            mandarinAlias: String,
                            _classType: Class[_],
                            _descriptor: String,
                            opcodes: TypeSpecificOpcode) extends Val(_name) with Type {
        def isAlias(literal: String): Boolean =
            name.equals(literal) || mandarinAlias.equals(literal)

        override def name: String = _name
        override def classType: Class[_] = _classType
        override def descriptor: String = _descriptor
        override def internalName: String = _descriptor
        override def loadVariableOpcode: Int = opcodes.load
        override def storeVariableOpcode: Int = opcodes.store
        override def returnOpcode: Int = opcodes.ret
        override def addOpcode: Int = opcodes.add
        override def subtractOpcode: Int = opcodes.sub
        override def multiplyOpcode: Int = opcodes.mul
        override def divideOpcode: Int = opcodes.div
    }

    final val BOOLEAN = BuiltInType("bool", "布林", classOf[Boolean], "Z", TypeSpecificOpcode.INT)
    final val INT = BuiltInType("int", "整數", classOf[Int], "I", TypeSpecificOpcode.INT)
    final val CHAR = BuiltInType("char", "字元", classOf[Char], "C", TypeSpecificOpcode.INT)
    final val BYTE = BuiltInType("byte", "位元", classOf[Byte], "B", TypeSpecificOpcode.INT)
    final val SHORT = BuiltInType("short", "短整數", classOf[Short], "S", TypeSpecificOpcode.INT)
    final val LONG = BuiltInType("long", "長整數", classOf[Long], "J", TypeSpecificOpcode.LONG)
    final val FLOAT = BuiltInType("float", "浮點數", classOf[Float], "F", TypeSpecificOpcode.FLOAT)
    final val DOUBLE = BuiltInType("double", "倍浮點數", classOf[Double], "D", TypeSpecificOpcode.DOUBLE)
    final val STRING = BuiltInType("string", "字串", classOf[String], "Ljava/lang/String;", TypeSpecificOpcode.OBJECT)
    final val BOOLEAN_ARR = BuiltInType("bool[]", "布林", classOf[Array[Boolean]], "[B", TypeSpecificOpcode.OBJECT)
    final val INT_ARR = BuiltInType("int[]", "整數[]", classOf[Array[Int]], "[I", TypeSpecificOpcode.OBJECT)
    final val CHAR_ARR = BuiltInType("char[]", "字元[]", classOf[Array[Char]], "[C", TypeSpecificOpcode.OBJECT)
    final val BYTE_ARR = BuiltInType("byte[]", "位元[]", classOf[Array[Byte]], "[B", TypeSpecificOpcode.OBJECT)
    final val SHORT_ARR = BuiltInType("short[]", "短整數[]", classOf[Array[Short]], "[S", TypeSpecificOpcode.OBJECT)
    final val LONG_ARR = BuiltInType("long[]", "長整數[]", classOf[Array[Long]], "[J", TypeSpecificOpcode.OBJECT)
    final val FLOAT_ARR = BuiltInType("float[]", "浮點數[]", classOf[Array[Float]], "[F", TypeSpecificOpcode.OBJECT)
    final val DOUBLE_ARR = BuiltInType("double[]", "倍浮點數[]", classOf[Array[Double]], "[D", TypeSpecificOpcode.OBJECT)
    final val STRING_ARR = BuiltInType("string[]", "字串[]", classOf[Array[String]], "[Ljava/lang/String;", TypeSpecificOpcode.OBJECT)
    final val NONE = BuiltInType("", "空值", null, "", TypeSpecificOpcode.OBJECT)
    final val VOID = BuiltInType("void", "空", classOf[Unit], "V", TypeSpecificOpcode.VOID)

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

    protected def BuiltInType(name: String, mandarinAlias: String, typeClass: Class[_], descriptor: String, opcodes: TypeSpecificOpcode): BuiltInType =
        new BuiltInType(name, mandarinAlias, typeClass, descriptor, opcodes)
}