package io.github.chaosunity.casc.parsing.type

import jdk.internal.org.objectweb.asm.Opcodes.*

enum class PredefTypeOpcode(
    val load: Int,
    val store: Int,
    val ret: Int,
    val add: Int,
    val sub: Int,
    val mul: Int,
    val div: Int
) {
    INT(ILOAD, ISTORE, IRETURN, IADD, ISUB, IMUL, IDIV),
    LONG(LLOAD, LSTORE, LRETURN, LADD, LSUB, LMUL, LDIV),
    FLOAT(FLOAD, FSTORE, FRETURN, FADD, FSUB, FMUL, FDIV),
    DOUBLE(DLOAD, DSTORE, DRETURN, DADD, DSUB, DMUL, DDIV),
    VOID(ALOAD, ASTORE, RETURN, 0, 0, 0, 0),
    OBJECT(ALOAD, ASTORE, ARETURN, 0, 0, 0, 0)
}
