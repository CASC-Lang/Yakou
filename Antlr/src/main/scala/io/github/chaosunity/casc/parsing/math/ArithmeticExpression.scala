package io.github.chaosunity.casc.parsing.math

import io.github.chaosunity.casc.exceptions.UnsupportedArithmeticOperationException
import io.github.chaosunity.casc.parsing.`type`.{BuiltInType, Type}
import io.github.chaosunity.casc.parsing.expression.Expression

sealed abstract class ArithmeticExpression(`type`: Type,
                                           val leftExpression: Expression,
                                           val rightExpression: Expression,
                                           negative: Boolean) extends Expression(`type`, negative) {
    if (`type` != BuiltInType.INT && `type` != BuiltInType.STRING) {
        throw new UnsupportedArithmeticOperationException(this)
    }
}

case class Addition(left: Expression, right: Expression, override val negative: Boolean) extends ArithmeticExpression(left.`type`, left, right, negative)

case class Subtraction(left: Expression, right: Expression, override val negative: Boolean) extends ArithmeticExpression(left.`type`, left, right, negative)

case class Multiplication(left: Expression, right: Expression, override val negative: Boolean) extends ArithmeticExpression(left.`type`, left, right, negative)

case class Division(left: Expression, right: Expression, override val negative: Boolean) extends ArithmeticExpression(left.`type`, left, right, negative)
