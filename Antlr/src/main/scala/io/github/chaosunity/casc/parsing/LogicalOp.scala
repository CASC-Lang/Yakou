package io.github.chaosunity.casc.parsing

object LogicalOp extends Enumeration {
    type LogicalOp = LogicalOps

    case class LogicalOps(literal: String,
                          mandarinAlias: String,
                          opCode: Int) extends Val(literal) {
        def isAlias(literal: String): Boolean =
            this.literal.equals(literal) || mandarinAlias.equals(literal)
    }

    final val EQ = LogicalOp("==", "是", 160)

    final val NOT_EQ = LogicalOp("!=", "不是", 159)

    final val LESS = LogicalOp("<", "小於", 162)

    final val GREATER = LogicalOp(">", "大於", 164)

    final val LESS_EQ = LogicalOp("<=", "小等於", 163)

    final val GREATER_EQ = LogicalOp(">=", "大等於", 161)

    def enumSet(): Array[LogicalOp] = Array(
        EQ,
        NOT_EQ,
        LESS,
        GREATER,
        LESS_EQ,
        GREATER_EQ
    )

    def LogicalOp(literal: String, mandarinAlias: String, opCode: Int): LogicalOp =
        new LogicalOp(literal, mandarinAlias, opCode)
}
