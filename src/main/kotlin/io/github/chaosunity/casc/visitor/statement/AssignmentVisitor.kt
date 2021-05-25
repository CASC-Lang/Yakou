package io.github.chaosunity.casc.visitor.statement

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.Reference
import io.github.chaosunity.casc.parsing.node.statement.Assignment
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor

class AssignmentVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<Assignment>() {
    override fun visitAssignment(ctx: CASCParser.AssignmentContext): Assignment {
        val leftExpression = ctx.ref!!.accept(ev)
        val rightExpression = ctx.toAssign!!.accept(ev)

        return Assignment(
            if (leftExpression is Reference<*>) leftExpression.name else null,
            leftExpression,
            rightExpression,
            scope.callingScope
        ).also {
            if (it.variableName != null) {
                if (scope.isLocalVariableExists(it.variableName) &&
                    scope.getLocalVariable(it.variableName).type == BuiltInType.NULL
                ) {
                    scope.getLocalVariable(it.variableName).type = it.expression.type
                } // Clarify local variable's actual type.
            }
        }
    }
}