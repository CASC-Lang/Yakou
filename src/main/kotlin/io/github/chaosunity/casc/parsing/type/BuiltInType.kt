package io.github.chaosunity.casc.parsing.type

enum class BuiltInType(
    override val typeName: String,
    val originalName: String,
    val classType: Class<*>?,
    override val descriptor: String,
    opcodes: PredefTypeOpcode
) : Type {
    BOOLEAN("bool", "boolean", Boolean::class.java, "Z", PredefTypeOpcode.INT),
    INT("i32", "int", Int::class.java, "I", PredefTypeOpcode.INT),
    CHAR("char", "char", Char::class.java, "C", PredefTypeOpcode.INT),
    BYTE("i8", "byte", Byte::class.java, "B", PredefTypeOpcode.INT),
    SHORT("i16", "short", Short::class.java, "S", PredefTypeOpcode.INT),
    LONG("i64", "long", Long::class.java, "J", PredefTypeOpcode.LONG),
    FLOAT("f32", "float", Float::class.java, "F", PredefTypeOpcode.FLOAT),
    DOUBLE("f64", "double", Double::class.java, "D", PredefTypeOpcode.DOUBLE),
    STRING("str", "java.lang.String", String::class.java, "Ljava/lang/String;", PredefTypeOpcode.OBJECT),
    BOOLEAN_ARR("bool[]", "boolean[]", BooleanArray::class.java, "[B", PredefTypeOpcode.OBJECT),
    INT_ARR("i32[]", "int[]", IntArray::class.java, "[I", PredefTypeOpcode.OBJECT),
    CHAR_ARR("char[]", "char[]", CharArray::class.java, "[C", PredefTypeOpcode.OBJECT),
    BYTE_ARR("i8[]", "byte[]", ByteArray::class.java, "[B", PredefTypeOpcode.OBJECT),
    SHORT_ARR("i16[]", "short[]", ShortArray::class.java, "[S", PredefTypeOpcode.OBJECT),
    LONG_ARR("i64[]", "long[]", LongArray::class.java, "[J", PredefTypeOpcode.OBJECT),
    FLOAT_ARR("f32[]", "float[]", FloatArray::class.java, "[F", PredefTypeOpcode.OBJECT),
    DOUBLE_ARR("f64[]", "double[]", DoubleArray::class.java, "[D", PredefTypeOpcode.OBJECT),
    STRING_ARR("str[]", "java.lang.String[]", Array<String>::class.java, "[Ljava/lang/String;", PredefTypeOpcode.OBJECT),
    NULL("null", "null", null, "", PredefTypeOpcode.OBJECT),
    VOID("unit", "null", Void.TYPE, "V", PredefTypeOpcode.VOID);

    override fun classType(): Class<*>? =
        classType

    override val internalName: String =
        descriptor.removePrefix("[").removePrefix("L").removeSuffix(";") // Need a better way to fix this issue.
    override val loadVariableOpcode: Int = opcodes.load
    override val storeVariableOpcode: Int = opcodes.store
    override val returnOpcode: Int = opcodes.ret
    override val addOpcode: Int = opcodes.add
    override val subtractOpcode: Int = opcodes.sub
    override val multiplyOpcode: Int = opcodes.mul
    override val divideOpcode: Int = opcodes.div
}