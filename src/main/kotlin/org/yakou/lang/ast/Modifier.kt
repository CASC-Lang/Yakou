package org.yakou.lang.ast

import org.objectweb.asm.Opcodes

enum class Modifier(val flag: Int) {
    PUB(Opcodes.ACC_PUBLIC),
    PROT(Opcodes.ACC_PROTECTED),
    PUB_PKG(0),
    PRIV(Opcodes.ACC_PRIVATE),
    MUT(-Opcodes.ACC_FINAL);
}