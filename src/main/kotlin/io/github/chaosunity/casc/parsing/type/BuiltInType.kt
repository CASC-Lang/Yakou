package io.github.chaosunity.casc.parsing.type

enum class BuiltInType(
    override val typeName: String,
    val classType: Class<*>?,
    override val descriptor: String,
    opcodes: PredefTypeOpcode
) : Type {
    BOOLEAN("boolean", Boolean::class.java, "Z", PredefTypeOpcode.INT),
    INT("int", Int::class.java, "I", PredefTypeOpcode.INT),
    CHAR("char", Char::class.java, "C", PredefTypeOpcode.INT),
    BYTE("byte", Byte::class.java, "B", PredefTypeOpcode.INT),
    SHORT("short", Short::class.java, "S", PredefTypeOpcode.INT),
    LONG("long", Long::class.java, "J", PredefTypeOpcode.LONG),
    FLOAT("float", Float::class.java, "F", PredefTypeOpcode.FLOAT),
    DOUBLE("double", Double::class.java, "D", PredefTypeOpcode.DOUBLE),
    STRING("string", String::class.java, "Ljava/lang/String;", PredefTypeOpcode.OBJECT),
    BOOLEAN_ARR("bool[]", BooleanArray::class.java, "[B", PredefTypeOpcode.OBJECT),
    INT_ARR("int[]", IntArray::class.java, "[I", PredefTypeOpcode.OBJECT),
    CHAR_ARR("char[]", CharArray::class.java, "[C", PredefTypeOpcode.OBJECT),
    BYTE_ARR("byte[]", ByteArray::class.java, "[B", PredefTypeOpcode.OBJECT),
    SHORT_ARR("short[]", ShortArray::class.java, "[S", PredefTypeOpcode.OBJECT),
    LONG_ARR("long[]", LongArray::class.java, "[J", PredefTypeOpcode.OBJECT),
    FLOAT_ARR("float[]", FloatArray::class.java, "[F", PredefTypeOpcode.OBJECT),
    DOUBLE_ARR("double[]", DoubleArray::class.java, "[D", PredefTypeOpcode.OBJECT),
    STRING_ARR("string[]", Array<String>::class.java, "[Ljava/lang/String;", PredefTypeOpcode.OBJECT),
    NONE("", null, "", PredefTypeOpcode.OBJECT),
    VOID("void", Void.TYPE, "V", PredefTypeOpcode.VOID);

    override fun classType(): Class<*>? =
        classType

    override val internalName: String = descriptor.removePrefix("[").removePrefix("L").removeSuffix(";") // Need a better way to fix this issue.
    override val loadVariableOpcode: Int = opcodes.load
    override val storeVariableOpcode: Int = opcodes.store
    override val returnOpcode: Int = opcodes.ret
    override val addOpcode: Int = opcodes.add
    override val subtractOpcode: Int = opcodes.sub
    override val multiplyOpcode: Int = opcodes.mul
    override val divideOpcode: Int = opcodes.div
}