package org.casc.lang.table

import org.objectweb.asm.Opcodes

enum class PrimitiveType(
    override val typeName: String,
    override val descriptor: String,
    override val internalName: String = descriptor,
    private val clazzType: Class<*>?,
    private val wrapperClass: Class<*>?,
    opcodeSet: OpcodeSets?
) : Type {
    // Null is only used for type check, never used in Emitter unit
    Null("null", "", "", null, null, OpcodeSets.Object),
    Unit("unit", "V", "void", Void::class.javaPrimitiveType, Void::class.java, null),
    Bool("bool", "Z", "boolean", Boolean::class.java, Boolean::class.javaObjectType, OpcodeSets.Integer),
    Char("char", "C", "char", kotlin.Char::class.java, kotlin.Char::class.javaObjectType, OpcodeSets.Integer),
    I8("i8", "B", "byte", Byte::class.java, Byte::class.javaObjectType, OpcodeSets.Integer),
    I16("i16", "S", "short", Short::class.java, Short::class.javaObjectType, OpcodeSets.Integer),
    I32("i32", "I", "int", Int::class.java, Int::class.javaObjectType, OpcodeSets.Integer),
    I64("i64", "J", "long", Long::class.java, Long::class.javaObjectType, OpcodeSets.Long),
    F32("f32", "F", "float", Float::class.java, Float::class.javaObjectType, OpcodeSets.Float),
    F64("f64", "D", "double", Double::class.java, Double::class.javaObjectType, OpcodeSets.Double),
    Str("str", "Ljava/lang/String;", "java/lang/String", String::class.java, null, OpcodeSets.Object);

    companion object {
        val values = values()
        val promotionTable = linkedMapOf(
            F64 to 3,
            F32 to 2,
            I64 to 1,
            I32 to 0,
            I16 to 0,
            I8 to 0,
        )

        fun fromClass(clazz: Class<*>?): PrimitiveType? =
            if (clazz == null) null
            else values.find {
                it.wrapperClass == clazz || it.clazzType == clazz
            }

        fun isJvmPrimitive(type: Type?): Boolean = when (type) {
            is PrimitiveType -> {
                type.isNumericType() || type == Bool || type == Char
            }
            is ClassType, is ArrayType -> false
            null -> false
        }
    }

    override fun type(): Class<*>? =
        clazzType

    override fun asCASCStyle(): String =
        typeName

    fun isNumericType(): Boolean =
        promotionTable.containsKey(this)

    override val loadOpcode: Int = opcodeSet?.loadOpcode ?: -1
    override val storeOpcode: Int = opcodeSet?.storeOpcode ?: -1
    override val returnOpcode: Int = opcodeSet?.returnOpcode ?: -1
    val addOpcode: Int = opcodeSet?.addOpcode ?: -1
    val subOpcode: Int = opcodeSet?.subOpcode ?: -1
    val mulOpcode: Int = opcodeSet?.mulOpcode ?: -1
    val divOpcode: Int = opcodeSet?.divOpcode ?: -1
    val remOpcode: Int = opcodeSet?.remOpcode ?: -1
    val negOpcode: Int = opcodeSet?.negOpcode ?: -1
    val typeOpcode: Int
        get() = when (this) {
            Bool -> Opcodes.T_BOOLEAN
            Char -> Opcodes.T_CHAR
            I8 -> Opcodes.T_BYTE
            I16 -> Opcodes.T_SHORT
            I32 -> Opcodes.T_INT
            I64 -> Opcodes.T_LONG
            F32 -> Opcodes.T_FLOAT
            F64 -> Opcodes.T_DOUBLE
            else -> -1
        }

    private enum class OpcodeSets(
        val loadOpcode: Int,
        val storeOpcode: Int,
        val returnOpcode: Int,
        val addOpcode: Int?,
        val subOpcode: Int?,
        val mulOpcode: Int?,
        val divOpcode: Int?,
        val remOpcode: Int?,
        val negOpcode: Int?
    ) {
        Integer(
            Opcodes.ILOAD,
            Opcodes.ISTORE,
            Opcodes.IRETURN,
            Opcodes.IADD,
            Opcodes.ISUB,
            Opcodes.IMUL,
            Opcodes.IDIV,
            Opcodes.IREM,
            Opcodes.INEG
        ),
        Long(
            Opcodes.LLOAD,
            Opcodes.LSTORE,
            Opcodes.LRETURN,
            Opcodes.LADD,
            Opcodes.LSUB,
            Opcodes.LMUL,
            Opcodes.LDIV,
            Opcodes.LREM,
            Opcodes.LNEG
        ),
        Float(
            Opcodes.FLOAD,
            Opcodes.FSTORE,
            Opcodes.FRETURN,
            Opcodes.FADD,
            Opcodes.FSUB,
            Opcodes.FMUL,
            Opcodes.FDIV,
            Opcodes.FREM,
            Opcodes.FNEG
        ),
        Double(
            Opcodes.DLOAD,
            Opcodes.DSTORE,
            Opcodes.DRETURN,
            Opcodes.DADD,
            Opcodes.DSUB,
            Opcodes.DMUL,
            Opcodes.DDIV,
            Opcodes.DREM,
            Opcodes.DNEG
        ),
        Object(Opcodes.ALOAD, Opcodes.ASTORE, Opcodes.ARETURN, null, null, null, null, null, null);
    }
}
