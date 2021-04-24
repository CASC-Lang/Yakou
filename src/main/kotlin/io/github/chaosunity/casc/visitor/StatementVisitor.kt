package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.expression.EmptyExpression
import io.github.chaosunity.casc.parsing.expression.Expression
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.*
import io.github.chaosunity.casc.parsing.type.BuiltInType

class StatementVisitor(private val scope: Scope) : CASCBaseVisitor<Statement>() {
    private val ev: ExpressionVisitor = ExpressionVisitor(scope)

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

    override fun visitFuncCall(ctx: CASCParser.FuncCallContext?): Statement =
        ctx?.accept(ExpressionVisitor(scope)) as Statement

    override fun visitReturnVoid(ctx: CASCParser.ReturnVoidContext?): Statement =
        ReturnStatement(EmptyExpression(BuiltInType.VOID()))

    override fun visitReturnWithValue(ctx: CASCParser.ReturnWithValueContext?): Statement {
        val expression = ctx?.expression()?.accept(ev)

        return ReturnStatement(expression)
    }

    override fun visitBlock(ctx: CASCParser.BlockContext?): Statement {
        val blockStatementCtx = ctx?.statement()
        val innerScope = Scope(scope)
        val statementVisitor = StatementVisitor(innerScope)
        val statements = blockStatementCtx?.map { it.accept(statementVisitor) }

        return BlockStatement(innerScope, statements)
    }

    override fun visitIfStatement(ctx: CASCParser.IfStatementContext?): Statement {
        val conditionalExpressionCtx = ctx?.expression()
        val condition = conditionalExpressionCtx?.accept(ev)
        val trueStatement = ctx?.trueStatement?.accept(ev)
        val falseStatement = ctx?.falseStatement?.accept(ev)

        return IfStatement(condition, trueStatement, falseStatement)
    }

    private fun getExpression(ctx: CASCParser.ExpressionContext?): Expression? {
        val expressionVisitor = ExpressionVisitor(scope)
        return ctx?.accept(expressionVisitor)
    }
}