package org.yakou.lang.bind

enum class PrimitiveType(val typeLiteral: String, val jvmClazz: Class<*>, val descriptor: String) {
    Unit("unit", Void.TYPE, "V"),
    Bool("bool", java.lang.Boolean.TYPE, "Z"),
    Char("char", Character.TYPE, "C"),
    I8("i8", java.lang.Byte.TYPE, "B"),
    I16("i16", java.lang.Short.TYPE, "S"),
    I32("i32", Integer.TYPE, "I"),
    I64("i64", java.lang.Long.TYPE, "J"),
    F32("f32", java.lang.Float.TYPE, "F"),
    F64("f64", java.lang.Double.TYPE, "D"),
    Str("str", java.lang.String::class.java, "Ljava/lang/String;"),
    ;

    companion object {
        private val values: Array<PrimitiveType> = values()
        private val primitiveTypes: Array<PrimitiveType> = arrayOf(Unit, Bool, Char, I8, I16, I32, I64, F32, F64)
        private val numberTypes: Array<PrimitiveType> = arrayOf(I8, I16, I32, I64, F32, F64)
        private val integerTypes: Array<PrimitiveType> = arrayOf(I8, I16, I32, I64)
        private val floatTypes: Array<PrimitiveType> = arrayOf(F32, F64)

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
            clazz.isPrimitive -> TypeInfo.Primitive(primitiveTypes.find { it.jvmClazz == clazz }!!)
            clazz.typeName == "java.lang.String" -> TypeInfo.Primitive(Str)
            else -> null
        }
    }
}