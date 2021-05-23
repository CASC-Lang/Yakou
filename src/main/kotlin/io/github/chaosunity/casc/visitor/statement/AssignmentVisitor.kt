package io.github.chaosunity.casc.visitor.statement

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.statement.Assignment
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor

class AssignmentVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<Assignment>() {
    override fun visitAssignment(ctx: CASCParser.AssignmentContext): Assignment =
        if (ctx.findExpression().size > 1) {
            val expressions = ctx.findExpression().toMutableList()
            val expressionToAssign = expressions.removeLast().accept(ev)
            val dimensionExpressions = expressions.map {
                it.accept(ev)
            }.onEach {
                if (!it.type.isInt() && !it.type.isLong())
                    throw RuntimeException("Cannot assign a value into an array by indexing with type ${it.type}.")
            }

            Assignment(ctx.findName()!!.text, expressionToAssign, scope.callingScope, dimensionExpressions)
        } else Assignment(ctx.findName()!!.text, ctx.findExpression().last().accept(ev), scope.callingScope).also {
            if (scope.isLocalVariableExists(it.variableName) &&
                scope.getLocalVariable(it.variableName).type == BuiltInType.NULL
            ) {
                scope.getLocalVariable(it.variableName).type = it.expression.type
            } // Clarify local variable's actual type.
        }
}