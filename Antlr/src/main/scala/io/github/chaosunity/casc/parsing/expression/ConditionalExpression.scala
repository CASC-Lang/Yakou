package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.LogicalOp.LogicalOp
import io.github.chaosunity.casc.parsing.`type`.{BuiltInType, Type}

class ConditionalExpression(val left: Expression,
                            val right: Expression,
                            val opcode: LogicalOp) extends Expression {
    override def `type`: Type = BuiltInType.VOID

    override def negative: Boolean = false
}
