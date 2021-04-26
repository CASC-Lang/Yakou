package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.expression.EmptyExpression
import io.github.chaosunity.casc.parsing.expression.Expression
import io.github.chaosunity.casc.parsing.expression.math.ArithmeticExpression
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.*
import io.github.chaosunity.casc.parsing.type.BuiltInType
import scala.Option

class StatementVisitor(private val scope: Scope) : CASCBaseVisitor<Statement>() {
    private val ev: ExpressionVisitor = ExpressionVisitor(scope)

    override fun visitPrintStatement(ctx: CASCParser.PrintStatementContext?): Statement {
        val expression = getExpression(ctx?.expression())

        return Print(expression)
    }

    override fun visitPrintlnStatement(ctx: CASCParser.PrintlnStatementContext?): Statement {
        val expression = getExpression(ctx?.expression())

        return Println(expression)
    }

    override fun visitVariableDeclaration(ctx: CASCParser.VariableDeclarationContext?): Statement {
        val variableName = ctx?.name()?.text
        val expression = getExpression(ctx?.expression())

        scope.addLocalVariable(LocalVariable(expression?.type(), variableName))

        return VariableDeclaration(variableName, expression)
    }

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

        return Block(innerScope, statements)
    }

    override fun visitIfStatement(ctx: CASCParser.IfStatementContext?): Statement {
        val conditionalExpressionCtx = ctx?.expression()
        val condition = conditionalExpressionCtx?.accept(ev)
        val trueStatement = ctx?.trueStatement?.accept(this)

        if (ctx?.falseStatement != null) {
            val falseStatement = ctx.falseStatement?.accept(this)

            return IfStatement(condition, trueStatement, Option.apply(falseStatement))
        }

        return IfStatement(condition, trueStatement, Option.empty())
    }

    override fun visitVarRef(ctx: CASCParser.VarRefContext?): Statement =
        ev.visitVarRef(ctx)

    override fun visitVal(ctx: CASCParser.ValContext?): Statement =
        ev.visitVal(ctx)

    override fun visitFunctionCall(ctx: CASCParser.FunctionCallContext?): Statement =
        ev.visitFunctionCall(ctx)

    override fun visitConstructorCall(ctx: CASCParser.ConstructorCallContext?): Statement =
        ev.visitConstructorCall(ctx)

    override fun visitSuperCall(ctx: CASCParser.SuperCallContext?): Statement =
        ev.visitSuperCall(ctx)

    override fun visitModAdd(ctx: CASCParser.ModAddContext?): Statement =
        ev.visitModAdd(ctx)

    override fun visitAdd(ctx: CASCParser.AddContext?): Expression =
        ev.visitAdd(ctx)

    override fun visitModSubtract(ctx: CASCParser.ModSubtractContext?): Expression =
        ev.visitModSubtract(ctx)

    override fun visitSubtract(ctx: CASCParser.SubtractContext?): Expression =
        ev.visitSubtract(ctx)

    override fun visitModMultiply(ctx: CASCParser.ModMultiplyContext?): Expression =
        ev.visitModMultiply(ctx)

    override fun visitMultiply(ctx: CASCParser.MultiplyContext?): Expression =
        ev.visitMultiply(ctx)

    override fun visitModDivide(ctx: CASCParser.ModDivideContext?): Expression =
        ev.visitModDivide(ctx)

    override fun visitDivide(ctx: CASCParser.DivideContext?): Expression =
        ev.visitDivide(ctx)

    override fun visitConditionalExpression(ctx: CASCParser.ConditionalExpressionContext?): Statement =
        ev.visitConditionalExpression(ctx)

    private fun getExpression(ctx: CASCParser.ExpressionContext?): Expression? {
        val expressionVisitor = ExpressionVisitor(scope)
        return ctx?.accept(expressionVisitor)
    }
}