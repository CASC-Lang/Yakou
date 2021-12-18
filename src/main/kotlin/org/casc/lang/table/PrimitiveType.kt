package org.casc.lang.table

import org.objectweb.asm.Opcodes

enum class PrimitiveType(
    override val typeName: String,
    override val descriptor: String,
    override val internalName: String = descriptor,
    val clazzType: Class<*>?,
    private val opcodeSet: OpcodeSets?
) : Type {
    Unit("unit", "V", "void", Void::class.java, null),
    Bool("bool", "Z", "boolean", Boolean::class.java, OpcodeSets.Integer),
    Char("char", "C", "char", kotlin.Char::class.java, OpcodeSets.Integer),
    I8("i8", "B", "byte", Byte::class.java, OpcodeSets.Integer),
    I16("i16", "S", "short", Short::class.java, OpcodeSets.Integer),
    I32("i32", "I", "int", Int::class.java, OpcodeSets.Integer),
    I64("i64", "J", "long", Long::class.java, OpcodeSets.Long),
    F32("f32", "F", "float", Float::class.java, OpcodeSets.Float),
    F64("f64", "D", "double", Double::class.java, OpcodeSets.Double),
    Str("str", "Ljava/lang/String;", "java.lang.String", String::class.java, OpcodeSets.Object);

    companion object {
        val values = values()
        val promotionTable = linkedMapOf(
            F64 to 4,
            F32 to 3,
            I64 to 2,
            I32 to 1,
            I16 to 0,
            I8 to 0,
        )
    }

    override fun type(): Class<*>? =
        clazzType

    fun isNumericType(): Boolean =
        promotionTable.containsKey(this)

    override val loadOpcode: Int = opcodeSet?.loadOpcode ?: -1
    override val storeOpcode: Int = opcodeSet?.storeOpcode ?: -1

    private enum class OpcodeSets(val loadOpcode: Int, val storeOpcode: Int) {
        Integer(Opcodes.ILOAD, Opcodes.ISTORE),
        Long(Opcodes.LLOAD, Opcodes.LSTORE),
        Float(Opcodes.FLOAD, Opcodes.FSTORE),
        Double(Opcodes.DLOAD, Opcodes.DSTORE),
        Object(Opcodes.ALOAD, Opcodes.ASTORE);
    }
}
