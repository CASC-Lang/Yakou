package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.exceptions.TypeUnmatchedException
import io.github.chaosunity.casc.parsing.`type`.Type

class IfExpression(val condition: Expression,
                   val trueExpression: Expression,
                   val falseExpression: Expression) extends Expression {
    override def `type`: Type = if (trueExpression.`type` == falseExpression.`type`) trueExpression.`type` else
        throw new TypeUnmatchedException(trueExpression.`type`, falseExpression.`type`)

    override def negative: Boolean = false
}
