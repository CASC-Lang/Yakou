package io.github.chaosunity.casc.parsing.expression.math

import io.github.chaosunity.casc.parsing.`type`.{BuiltInType, Type}
import io.github.chaosunity.casc.parsing.expression.Expression
import io.github.chaosunity.casc.parsing.expression.math.ArithmeticExpression.getCommonType

sealed abstract class ArithmeticExpression(val leftExpression: Expression,
                                           val rightExpression: Expression,
                                           _negative: Boolean) extends Expression {
    override def `type`: Type = getCommonType(leftExpression, rightExpression)

    override def negative: Boolean = _negative
}

object ArithmeticExpression {
    private def getCommonType(leftExpression: Expression, rightExpression: Expression): Type =
        if (rightExpression.`type` == BuiltInType.STRING) BuiltInType.STRING else leftExpression.`type`

    case class Addition(left: Expression, right: Expression, override val negative: Boolean) extends ArithmeticExpression(left, right, negative)

    case class Subtraction(left: Expression, right: Expression, override val negative: Boolean) extends ArithmeticExpression(left, right, negative)

    case class Multiplication(left: Expression, right: Expression, override val negative: Boolean) extends ArithmeticExpression(left, right, negative)

    case class Division(left: Expression, right: Expression, override val negative: Boolean) extends ArithmeticExpression(left, right, negative)

}
