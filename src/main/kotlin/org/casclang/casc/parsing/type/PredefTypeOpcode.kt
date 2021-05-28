package org.casclang.casc.parsing.type

import jdk.internal.org.objectweb.asm.Opcodes.*

enum class PredefTypeOpcode(
    val load: Int,
    val arrayLoad: Int,
    val store: Int,
    val arrayStore: Int,
    val type: Int,
    val ret: Int,
    val add: Int,
    val sub: Int,
    val mul: Int,
    val div: Int,
    val neg: Int
) {
    BOOL(ILOAD, IALOAD, ISTORE, IASTORE, T_BOOLEAN, IRETURN, 0, 0, 0, 0, 0),
    CHAR(ILOAD, IALOAD, ISTORE, IASTORE, T_CHAR, IRETURN, 0 , 0, 0, 0, 0),
    BYTE(ILOAD, IALOAD, ISTORE, IASTORE, T_BYTE, IRETURN, IADD, ISUB, IMUL, IDIV, INEG),
    SHORT(ILOAD, IALOAD, ISTORE, IASTORE, T_SHORT, IRETURN, IADD, ISUB, IMUL, IDIV, INEG),
    INT(ILOAD, IALOAD, ISTORE, IASTORE, T_INT, IRETURN, IADD, ISUB, IMUL, IDIV, INEG),
    LONG(LLOAD, LALOAD, LSTORE, LASTORE, T_LONG, LRETURN, LADD, LSUB, LMUL, LDIV, LNEG),
    FLOAT(FLOAD, FALOAD, FSTORE, FASTORE, T_FLOAT, FRETURN, FADD, FSUB, FMUL, FDIV, FNEG),
    DOUBLE(DLOAD, DALOAD, DSTORE, DASTORE, T_DOUBLE, DRETURN, DADD, DSUB, DMUL, DDIV, DNEG),
    VOID(ALOAD, AALOAD, ASTORE, AASTORE, 0, RETURN, 0, 0, 0, 0, 0),
    OBJECT(ALOAD, AALOAD, ASTORE, AASTORE, 0, ARETURN, 0, 0, 0, 0, 0)
}
