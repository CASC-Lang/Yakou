package org.casc.lang.table

enum class PrimitiveType(
    override val typeName: String,
    override val descriptor: String,
    override val internalName: String = descriptor,
    val clazzType: Class<*>?,
) : Type {
    Unit("unit", "V", "void", Void::class.java),
    Bool("bool", "Z", "boolean", Boolean::class.java),
    Char("char", "C", "char", kotlin.Char::class.java),
    I8("i8", "B", "byte", Byte::class.java),
    I16("i16", "S", "short", Short::class.java),
    I32("i32", "I", "int", Int::class.java),
    I64("i64", "L", "long", Long::class.java),
    F32("f32", "F", "float", Float::class.java),
    F64("f64", "D", "double", Double::class.java),
    Str("str", "Ljava/lang/String;", "java.lang.String", String::class.java);

    companion object {
        val values = values()
    }

    override fun type(): Class<*>? =
        clazzType
}
