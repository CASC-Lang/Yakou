package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.*

class ForStatementVisitor(scope: Scope) : CASCBaseVisitor<ForStatement>() {
    private val scope = Scope(scope)
    private val ev = ExpressionVisitor(this.scope)
    private val sv = StatementVisitor(this.scope)

    override fun visitForStatement(ctx: CASCParser.ForStatementContext?): ForStatement {
        val forExpressionCtx = ctx?.forExpression()
        val startExpression = forExpressionCtx?.startExpr?.accept(ev)
        val forType = if (forExpressionCtx?.TO() != null) ForType.TO() else ForType.UNTIL()
        val endExpression = forExpressionCtx?.endExpr?.accept(ev)
        val iteratorCtx = forExpressionCtx?.iterator
        val variableName = iteratorCtx?.text

        return if (scope.localVariableExists(variableName)) {
            val iteratorVariable = AssignmentStatement(variableName, startExpression)
            val statement = ctx?.statement()?.accept(sv)

            RangedForStatement(
                iteratorVariable,
                startExpression,
                forType,
                endExpression,
                statement,
                variableName,
                scope
            )
        } else {
            scope.addLocalVariable(LocalVariable(startExpression?.type(), variableName))

            val iteratorVariable = VariableDeclaration(variableName, startExpression)
            val statement = ctx?.statement()?.accept(sv)

            RangedForStatement(
                iteratorVariable,
                startExpression,
                forType,
                endExpression,
                statement,
                variableName,
                scope
            )
        }
    }
}