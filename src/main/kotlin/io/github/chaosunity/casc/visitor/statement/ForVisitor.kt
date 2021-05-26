package io.github.chaosunity.casc.visitor.statement

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.LocalVariableReference
import io.github.chaosunity.casc.parsing.node.statement.*
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor

class ForVisitor(private val scope: Scope) : CASCBaseVisitor<ForStatement<*>>() {
    override fun visitForStatement(ctx: CASCParser.ForStatementContext): ForStatement<*> =
        if (ctx.findForExpressions() != null) {
            val forExpression = ctx.findForExpressions()!!

            if (forExpression.findForRangedExpression() != null)
                visitForRangedExpression(forExpression.findForRangedExpression()!!)
            else
                visitForLoopExpression(forExpression.findForLoopExpression()!!)
        } else {
            InfiniteForStatement(ctx.findStatement()!!.accept(StatementVisitor(scope)), Scope(scope))
        }

    override fun visitForRangedExpression(ctx: CASCParser.ForRangedExpressionContext): ForStatement<*> {
        val scope = Scope(scope)
        val ev = ExpressionVisitor(scope)
        val sv = StatementVisitor(scope)

        val variableName = ctx.iterator!!.text
        val startExpression = ctx.startExpr!!.accept(ev)
        val arrow = ctx.arrow!!.text
        val endExpression = ctx.endExpr!!.accept(ev)
        val statement = (ctx.readParent()!!.readParent() as CASCParser.ForStatementContext).findStatement()!!

        return if (scope.isLocalVariableExists(variableName)) {
            val iteratorVariable = Assignment(
                variableName,
                LocalVariableReference(scope.getLocalVariable(variableName)),
                if (arrow.endsWith('>')) startExpression else endExpression,
                scope.callingScope
            )

            RangedForStatement(
                iteratorVariable,
                startExpression,
                arrow,
                endExpression,
                statement.accept(sv),
                variableName,
                scope
            )
        } else {
            scope.addLocalVariable(LocalVariable(variableName, startExpression.type))

            val iteratorVariable =
                VariableDeclaration(variableName, if (arrow.endsWith('>')) startExpression else endExpression)

            RangedForStatement(
                iteratorVariable,
                startExpression,
                arrow,
                endExpression,
                statement.accept(sv),
                variableName,
                scope
            )
        }
    }

    override fun visitForLoopExpression(ctx: CASCParser.ForLoopExpressionContext): ForStatement<*> {
        val scope = Scope(scope)
        val ev = ExpressionVisitor(scope)
        val sv = StatementVisitor(scope)

        val initStatement = ctx.initStatement?.accept(sv)
        val conditionExpression = ctx.conditionExpr?.accept(ev)
        val postStatement = ctx.postStatement?.accept(sv)

        val statement = (ctx.readParent()!!.readParent() as CASCParser.ForStatementContext).findStatement()!!.accept(sv)

        return ForLoopStatement(
            initStatement,
            conditionExpression,
            postStatement,
            statement,
            Scope(scope)
        )
    }
}