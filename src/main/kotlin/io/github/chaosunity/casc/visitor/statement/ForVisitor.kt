package io.github.chaosunity.casc.visitor.statement

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.statement.*
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor

class ForVisitor(scope: Scope) : CASCBaseVisitor<ForStatement<*>>() {
    private val scope = Scope(scope)
    private val ev = ExpressionVisitor(this.scope)
    private val sv = StatementVisitor(this.scope)

    override fun visitForRangedExpression(ctx: CASCParser.ForRangedExpressionContext): ForStatement<*> {
        val variableName = ctx.iterator!!.text
        val startExpression = ctx.startExpr!!.accept(ev)
        val endExpression = ctx.endExpr!!.accept(ev)
        val statement = (ctx.readParent() as CASCParser.ForStatementContext).findStatement()!!.accept(sv)
        val stopAt = StopAt.valueOf(ctx.range?.text!!)

        return if (scope.isLocalVariableExists(variableName)) {
            val iteratorVariable = Assignment(variableName, startExpression)

            RangedForStatement(
                iteratorVariable,
                startExpression,
                false,
                stopAt,
                endExpression,
                statement,
                variableName,
                scope
            )
        } else {
            scope.addLocalVariable(LocalVariable(variableName, startExpression.type))

            val iteratorVariable = VariableDeclaration(variableName, startExpression)

            RangedForStatement(
                iteratorVariable,
                startExpression,
                false,
                stopAt,
                endExpression,
                statement,
                variableName,
                scope
            )
        }
    }
}