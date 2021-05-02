package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.Type

sealed class ArithmeticExpression<T>(val leftExpression: Expression<*>, val rightExpression: Expression<*>) :
    Expression<T> where T : ArithmeticExpression<T> {
    companion object {
        fun getCompanionType(leftExpression: Expression<*>, rightExpression: Expression<*>): Type =
            when (rightExpression.type) {
                BuiltInType.STRING -> BuiltInType.STRING
                else -> leftExpression.type
            }
    }

    override val type: Type = getCompanionType(leftExpression, rightExpression)

    class Addition(
        leftExpression: Expression<*>,
        rightExpression: Expression<*>
    ) : ArithmeticExpression<Addition>(leftExpression, rightExpression)

    class Subtraction(
        leftExpression: Expression<*>,
        rightExpression: Expression<*>
    ) : ArithmeticExpression<Subtraction>(leftExpression, rightExpression)

    class Multiplication(
        leftExpression: Expression<*>,
        rightExpression: Expression<*>
    ) : ArithmeticExpression<Multiplication>(leftExpression, rightExpression)

    class Division(
        leftExpression: Expression<*>,
        rightExpression: Expression<*>
    ) : ArithmeticExpression<Division>(leftExpression, rightExpression)
}
