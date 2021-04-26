package io.github.chaosunity.casc.parsing.`type`

import org.objectweb.asm.Opcodes._

object TypeSpecificOpcode extends Enumeration {
    type TypeSpecificOpcode = TypeSpecificOpcodes

    sealed class TypeSpecificOpcodes(val load: Int,
                                     val store: Int,
                                     val ret: Int,
                                     val add: Int,
                                     val sub: Int,
                                     val mul: Int,
                                     val div: Int) extends Val()

    final val INT = TypeSpecificOpcode(ILOAD, ISTORE, IRETURN, IADD, ISUB, IMUL, IDIV)
    final val LONG = TypeSpecificOpcode(LLOAD, LSTORE, LRETURN, LADD, LSUB, LMUL, LDIV)
    final val FLOAT = TypeSpecificOpcode(FLOAD, FSTORE, FRETURN, FADD, FSUB, FMUL, FDIV)
    final val DOUBLE = TypeSpecificOpcode(DLOAD, DSTORE, DRETURN, DADD, DSUB, DMUL, DDIV)
    final val VOID = TypeSpecificOpcode(ALOAD, ASTORE, RETURN, 0, 0, 0, 0)
    final val OBJECT = TypeSpecificOpcode(ALOAD, ASTORE, ARETURN, 0, 0, 0, 0)

    protected def TypeSpecificOpcode(load: Int, store: Int, ret: Int, add: Int, sub: Int, mul: Int, div: Int): TypeSpecificOpcodes =
        new TypeSpecificOpcodes(load, store, ret, add, sub, mul, div)
}
