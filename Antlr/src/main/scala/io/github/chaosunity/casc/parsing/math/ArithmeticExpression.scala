package io.github.chaosunity.casc.parsing.math

import io.github.chaosunity.casc.exceptions.UnsupportedArithmeticOperationException
import io.github.chaosunity.casc.parsing.`type`.{BuiltInType, Type}
import io.github.chaosunity.casc.parsing.expression.Expression

sealed abstract class ArithmeticExpression(`type`: Type,
                                           private val _leftExpression: Expression,
                                           private val _rightExpression: Expression) extends Expression(`type`) {
    if (`type` != BuiltInType.INT) {
        throw new UnsupportedArithmeticOperationException(this)
    }

    def leftExpression: Expression = _leftExpression

    def rightExpression: Expression = _rightExpression
}

case class Addition(left: Expression, right: Expression) extends ArithmeticExpression(left.`type`, left, right)

case class Subtraction(left: Expression, right: Expression) extends ArithmeticExpression(left.`type`, left, right)

case class Multiplication(left: Expression, right: Expression) extends ArithmeticExpression(left.`type`, left, right)

case class Division(left: Expression, right: Expression) extends ArithmeticExpression(left.`type`, left, right)
