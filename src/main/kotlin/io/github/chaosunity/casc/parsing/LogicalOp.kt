package io.github.chaosunity.casc.parsing

import jdk.internal.org.objectweb.asm.Opcodes.*

enum class LogicalOp(val sign: String, val opcode: Int) {
    EQ("==", IF_ICMPEQ),
    NOT_EQ("!=", IF_ICMPNE),
    LESS("<", IF_ICMPLT),
    GREATER(">", IF_ICMPGT),
    LESS_EQ("<=", IF_ICMPLE),
    GRATER_EQ(">=", IF_ICMPGE);

    companion object {
        fun fromString(literal: String?): LogicalOp =
            values().find { it.alias(literal) }
                ?: throw RuntimeException("Operator '$literal' is not a valid compare operator.")
    }

    fun alias(literal: String?): Boolean =
        sign == literal
}