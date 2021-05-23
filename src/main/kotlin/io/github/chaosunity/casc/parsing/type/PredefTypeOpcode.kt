package io.github.chaosunity.casc.parsing.type

import jdk.internal.org.objectweb.asm.Opcodes.*

enum class PredefTypeOpcode(
    val load: Int,
    val store: Int,
    val type: Int,
    val ret: Int,
    val add: Int,
    val sub: Int,
    val mul: Int,
    val div: Int
) {
    BOOL(ILOAD, ISTORE, T_BOOLEAN, IRETURN, 0, 0, 0, 0),
    CHAR(ILOAD, ISTORE, T_CHAR, IRETURN, 0 , 0, 0, 0),
    BYTE(ILOAD, ISTORE, T_BYTE, IRETURN, IADD, ISUB, IMUL, IDIV),
    SHORT(ILOAD, ISTORE, T_SHORT, IRETURN, IADD, ISUB, IMUL, IDIV),
    INT(ILOAD, ISTORE, T_INT, IRETURN, IADD, ISUB, IMUL, IDIV),
    LONG(LLOAD, LSTORE, T_LONG, LRETURN, LADD, LSUB, LMUL, LDIV),
    FLOAT(FLOAD, FSTORE, T_FLOAT, FRETURN, FADD, FSUB, FMUL, FDIV),
    DOUBLE(DLOAD, DSTORE, T_DOUBLE, DRETURN, DADD, DSUB, DMUL, DDIV),
    VOID(ALOAD, ASTORE, 0, RETURN, 0, 0, 0, 0),
    OBJECT(ALOAD, ASTORE, 0, ARETURN, 0, 0, 0, 0)
}
