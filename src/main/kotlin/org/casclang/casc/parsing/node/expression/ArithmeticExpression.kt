package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.BuiltInType

sealed class ArithmeticExpression<T>(val leftExpression: Expression<*>, val rightExpression: Expression<*>) :
    FoldableExpression<T>(
        when (rightExpression.type) {
            BuiltInType.STRING -> BuiltInType.STRING
            else -> leftExpression.type
        }
    ) where T : ArithmeticExpression<T> {

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
