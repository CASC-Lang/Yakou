package org.casclang.casc.visitor.statement

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.Reference
import org.casclang.casc.parsing.node.statement.Assignment
import org.casclang.casc.parsing.scope.Scope
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.visitor.expression.ExpressionVisitor

class AssignmentVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<Assignment>() {
    override fun visitAssignment(ctx: CASCParser.AssignmentContext): Assignment {
        val leftExpression = ctx.ref!!.accept(ev)
        val rightExpression = ctx.toAssign!!.accept(ev)

        return Assignment(
            if (leftExpression is Reference<*>) leftExpression.name else null,
            leftExpression,
            rightExpression,
            false,
            scope.callingScope
        ).also {
            if (it.variableName != null) {
                if (scope.isLocalVariableExists(it.variableName) &&
                    scope.getLocalVariable(it.variableName)?.type == BuiltInType.NULL
                ) {
                    scope.getLocalVariable(it.variableName)?.type = it.expression.type
                } // Clarify local variable's actual type.
            }
        }
    }
}