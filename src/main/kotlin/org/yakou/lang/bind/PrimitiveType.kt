package org.yakou.lang.bind

enum class PrimitiveType(
    val typeLiteral: String,
    val originalName: String,
    val jvmClazz: Class<*>,
    val wrappedJvmClazz: Class<*>,
    val descriptor: String,
    val precedence: Int
) {
    Unit("unit", "void", Void.TYPE, Void::class.java, "V", -1),
    Bool("bool", "boolean", java.lang.Boolean.TYPE, java.lang.Boolean::class.java, "Z", 0),
    Char("char", "char", Character.TYPE, Character::class.java, "C", 1),
    I8("i8", "byte", java.lang.Byte.TYPE, java.lang.Byte::class.java, "B", 2),
    I16("i16", "short", java.lang.Short.TYPE, java.lang.Short::class.java, "S", 2),
    I32("i32", "int", Integer.TYPE, Integer::class.java, "I", 2),
    I64("i64", "long", java.lang.Long.TYPE, java.lang.Long::class.java, "J", 3),
    F32("f32", "float", java.lang.Float.TYPE, java.lang.Float::class.java, "F", 4),
    F64("f64", "double", java.lang.Double.TYPE, java.lang.Double::class.java, "D", 5)
    ;

    companion object {
        private val values: Array<PrimitiveType> = values()
        val primitiveTypes: Array<PrimitiveType> = arrayOf(Unit, Bool, Char, I8, I16, I32, I64, F32, F64)
        val convertableTypes: Array<PrimitiveType> = arrayOf(Bool, Char, I8, I16, I32, I64, F32, F64)
        val numberTypes: Array<PrimitiveType> = arrayOf(I8, I16, I32, I64, F32, F64)
        val integerTypes: Array<PrimitiveType> = arrayOf(I8, I16, I32, I64)
        val intTypes: Array<PrimitiveType> = arrayOf(I8, I16, I32)
        val floatTypes: Array<PrimitiveType> = arrayOf(F32, F64)

        fun isPrimitiveType(typeLiteral: String): Boolean =
            values.any { it.typeLiteral == typeLiteral }

        fun findPrimitiveType(typeLiteral: String): PrimitiveType? =
            values.find { it.typeLiteral == typeLiteral }

        fun isNumberType(typeLiteral: String): Boolean =
            numberTypes.any { it.typeLiteral == typeLiteral }

        fun isIntegerType(typeLiteral: String): Boolean =
            integerTypes.any { it.typeLiteral == typeLiteral }

        fun isFloatType(typeLiteral: String): Boolean =
            floatTypes.any { it.typeLiteral == typeLiteral }

        fun fromClass(clazz: Class<*>): TypeInfo? = when {
            clazz.isPrimitive -> TypeInfo.Primitive(primitiveTypes.find { it.jvmClazz == clazz || it.wrappedJvmClazz == clazz }!!)
            else -> null
        }
    }

    fun isPrimitiveType(): Boolean =
        primitiveTypes.any { it == this }

    fun convertable(): Boolean =
        convertableTypes.any { it == this }

    fun isNumberType(): Boolean =
        numberTypes.any { it == this }

    fun isIntegerType(): Boolean =
        integerTypes.any { it == this }

    fun isIntType(): Boolean =
        integerTypes.any { it == this }

    fun isFloatType(): Boolean =
        floatTypes.any { it == this }
}
