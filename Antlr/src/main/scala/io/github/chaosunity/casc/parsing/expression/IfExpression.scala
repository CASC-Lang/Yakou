package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.exceptions.TypeUnmatchedException

class IfExpression(val condition: Expression,
                   val trueExpression: Expression,
                   val falseExpression: Expression)
    extends Expression(if (trueExpression.`type` == falseExpression.`type`) trueExpression.`type`
                       else throw new TypeUnmatchedException(trueExpression.`type`, falseExpression.`type`)) {
}
