package io.github.chaosunity.casc.parsing

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes._

object LogicalOp extends Enumeration with Opcodes {
    type LogicalOp = LogicalOps

    case class LogicalOps(literal: String,
                          mandarinAlias: String,
                          opcode: Int) extends Val(literal) {
        def isAlias(literal: String): Boolean =
            this.literal.equals(literal) || mandarinAlias.equals(literal)
    }

    final val EQ = LogicalOp("==", "是", IF_ICMPEQ)

    final val NOT_EQ = LogicalOp("!=", "不是", IF_ICMPNE)

    final val LESS = LogicalOp("<", "小於", IF_ICMPLT)

    final val GREATER = LogicalOp(">", "大於", IF_ICMPGT)

    final val LESS_EQ = LogicalOp("<=", "小等於", IF_ICMPLE)

    final val GREATER_EQ = LogicalOp(">=", "大等於", IF_ICMPGE)

    def enumSet(): Array[LogicalOp] = Array(
        EQ,
        NOT_EQ,
        LESS,
        GREATER,
        LESS_EQ,
        GREATER_EQ
    )

    protected def LogicalOp(literal: String, mandarinAlias: String, opcode: Int): LogicalOp =
        new LogicalOp(literal, mandarinAlias, opcode)
}
