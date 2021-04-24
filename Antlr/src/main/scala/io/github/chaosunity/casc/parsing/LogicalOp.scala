package io.github.chaosunity.casc.parsing

object LogicalOp extends Enumeration {
    type LogicalOp = LogicalOps

    case class LogicalOps(literal: String,
                          opCode: Int) extends Val(literal)

    final val EQ = LogicalOp("=", 160)

    final val NOT_EQ = LogicalOp("!=", 159)

    final val LESS = LogicalOp("<", 162)

    final val GREATER = LogicalOp(">", 164)

    final val LESS_EQ = LogicalOp("<=", 163)

    final val GREATER_EQ = LogicalOp(">=", 161)

    def enumSet(): Array[LogicalOp] = Array(
        EQ,
        NOT_EQ,
        LESS,
        GREATER,
        LESS_EQ,
        GREATER_EQ
    )

    def LogicalOp(literal: String, opCode: Int): LogicalOp =
        new LogicalOp(literal, opCode)
}
