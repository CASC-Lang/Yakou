package io.github.chaosunity.casc.parsing

import jdk.internal.org.objectweb.asm.Opcodes.*

enum class LogicalOp(val sign: String, val mandarinAlias: String, val opcode: Int) {
    EQ("==", "是", IF_ICMPEQ),
    NOT_EQ("!=", "不是", IF_ICMPNE),
    LESS("<", "小於", IF_ICMPLT),
    GREATER(">", "大於", IF_ICMPGT),
    LESS_EQ("<=", "小等於", IF_ICMPLE),
    GRATER_EQ(">=", "大等於", IF_ICMPGE);

    fun alias(literal: String): Boolean =
        sign == literal || mandarinAlias == literal
}