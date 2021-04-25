package io.github.chaosunity.casc.parsing.statement

import io.github.chaosunity.casc.exceptions.UnsupportedRangedTypeException
import io.github.chaosunity.casc.parsing.`type`.{BuiltInType, Type}
import io.github.chaosunity.casc.parsing.expression.Expression
import io.github.chaosunity.casc.parsing.scope.Scope


class RangedForStatement(val iteratorVariableStatement: Statement,
                         val startExpression: Expression,
                         val forType: ForType.Value,
                         val endExpression: Expression,
                         val statement: Statement,
                         val iteratorVariableName: String,
                         val scope: Scope) extends Statement {
    if (startExpression.`type` != BuiltInType.INT ||
        endExpression.`type` != BuiltInType.INT)
        throw new UnsupportedRangedTypeException(startExpression.`type`, endExpression.`type`)

    def `type`: Type = startExpression.`type`
}
