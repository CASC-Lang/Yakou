package io.github.chaosunity.casc.parsing.type

enum class BuiltInType(
    override val typeName: String,
    val classType: Class<*>?,
    override val descriptor: String,
    opcodes: PredefTypeOpcode
) : Type {
    BOOLEAN("bool", Boolean::class.java, "Z", PredefTypeOpcode.INT),
    INT("i32", Int::class.java, "I", PredefTypeOpcode.INT),
    CHAR("char", Char::class.java, "C", PredefTypeOpcode.INT),
    BYTE("i8", Byte::class.java, "B", PredefTypeOpcode.INT),
    SHORT("i16", Short::class.java, "S", PredefTypeOpcode.INT),
    LONG("i64", Long::class.java, "J", PredefTypeOpcode.LONG),
    FLOAT("f32", Float::class.java, "F", PredefTypeOpcode.FLOAT),
    DOUBLE("f64", Double::class.java, "D", PredefTypeOpcode.DOUBLE),
    STRING("str", String::class.java, "Ljava/lang/String;", PredefTypeOpcode.OBJECT),
    BOOLEAN_ARR("bool[]", BooleanArray::class.java, "[B", PredefTypeOpcode.OBJECT),
    INT_ARR("i32[]", IntArray::class.java, "[I", PredefTypeOpcode.OBJECT),
    CHAR_ARR("char[]", CharArray::class.java, "[C", PredefTypeOpcode.OBJECT),
    BYTE_ARR("i8[]", ByteArray::class.java, "[B", PredefTypeOpcode.OBJECT),
    SHORT_ARR("i16[]", ShortArray::class.java, "[S", PredefTypeOpcode.OBJECT),
    LONG_ARR("i64[]", LongArray::class.java, "[J", PredefTypeOpcode.OBJECT),
    FLOAT_ARR("f32[]", FloatArray::class.java, "[F", PredefTypeOpcode.OBJECT),
    DOUBLE_ARR("f64[]", DoubleArray::class.java, "[D", PredefTypeOpcode.OBJECT),
    STRING_ARR("str[]", Array<String>::class.java, "[Ljava/lang/String;", PredefTypeOpcode.OBJECT),
    NULL("null", null, "", PredefTypeOpcode.OBJECT),
    VOID("unit", Void.TYPE, "V", PredefTypeOpcode.VOID);

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