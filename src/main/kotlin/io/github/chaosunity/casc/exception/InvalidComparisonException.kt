package io.github.chaosunity.casc.exception

import io.github.chaosunity.casc.parsing.LogicalOp
import io.github.chaosunity.casc.parsing.expression.Expression
import java.lang.RuntimeException

class InvalidComparisonException(left: Expression, right: Expression, logicalOp: LogicalOp.LogicalOps) :
    RuntimeException("Type '${left.type()}' and '${right.type()}' cannot compare with '${logicalOp.literal()}' operator.") {
}