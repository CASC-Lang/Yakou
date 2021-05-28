package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.Type

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
