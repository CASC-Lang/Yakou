package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.expression.Expression
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.PrintStatement
import io.github.chaosunity.casc.parsing.statement.PrintlnStatement
import io.github.chaosunity.casc.parsing.statement.Statement
import io.github.chaosunity.casc.parsing.statement.VariableDeclaration

class StatementVisitor(private val scope: Scope) : CASCBaseVisitor<Statement>() {
    override fun visitPrintStatement(ctx: CASCParser.PrintStatementContext?): Statement {
        val expression = getExpression(ctx?.expression())

        return PrintStatement(expression)
    }

    override fun visitPrintlnStatement(ctx: CASCParser.PrintlnStatementContext?): Statement {
        val expression = getExpression(ctx?.expression())

        return PrintlnStatement(expression)
    }

    override fun visitVariableDeclaration(ctx: CASCParser.VariableDeclarationContext?): Statement {
        val variableName = ctx?.name()?.text
        val expression = getExpression(ctx?.expression())

        scope.addLocalVariable(LocalVariable(expression?.type(), variableName))

        return VariableDeclaration(variableName, expression)
    }

    private fun getExpression(ctx: CASCParser.ExpressionContext?): Expression? {
        val expressionVisitor = ExpressionVisitor(scope)
        return ctx?.accept(expressionVisitor)
    }
}