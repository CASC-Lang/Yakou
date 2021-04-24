package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.LogicalOp.LogicalOp
import io.github.chaosunity.casc.parsing.`type`.BuiltInType

class ConditionalExpression(private val _left: Expression,
                            private val _right: Expression,
                            private val _opCode: LogicalOp) extends Expression(BuiltInType.BOOLEAN) {
    def left: Expression = _left

    def right: Expression = _right

    def opCode: LogicalOp = _opCode
}
