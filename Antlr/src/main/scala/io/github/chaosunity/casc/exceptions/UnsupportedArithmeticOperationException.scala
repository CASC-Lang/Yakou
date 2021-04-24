package io.github.chaosunity.casc.exceptions

import io.github.chaosunity.casc.parsing.math.ArithmeticExpression

class UnsupportedArithmeticOperationException(arithmeticExpression: ArithmeticExpression)
    extends RuntimeException(s"Unsupported arithmetic operation between ${arithmeticExpression.leftExpression} and ${arithmeticExpression.rightExpression}") {
}
