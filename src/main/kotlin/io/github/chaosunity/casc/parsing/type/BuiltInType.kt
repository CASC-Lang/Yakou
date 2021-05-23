package io.github.chaosunity.casc.parsing.type

enum class BuiltInType(
    override val typeName: String,
    val originalName: String,
    val classType: Class<*>?,
    override val descriptor: String,
    opcodes: PredefTypeOpcode
) : Type {
    BOOLEAN("bool", "boolean", Boolean::class.java, "Z", PredefTypeOpcode.BOOL),
    INT("i32", "int", Int::class.java, "I", PredefTypeOpcode.INT),
    CHAR("char", "char", Char::class.java, "C", PredefTypeOpcode.CHAR),
    BYTE("i8", "byte", Byte::class.java, "B", PredefTypeOpcode.BYTE),
    SHORT("i16", "short", Short::class.java, "S", PredefTypeOpcode.SHORT),
    LONG("i64", "long", Long::class.java, "J", PredefTypeOpcode.LONG),
    FLOAT("f32", "float", Float::class.java, "F", PredefTypeOpcode.FLOAT),
    DOUBLE("f64", "double", Double::class.java, "D", PredefTypeOpcode.DOUBLE),
    STRING("str", "java.lang.String", String::class.java, "Ljava/lang/String;", PredefTypeOpcode.OBJECT),
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

    val typeOpcode = opcodes.type
}