package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.ArithmeticExpression

class ArithmeticVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<ArithmeticExpression<*>>() {
    override fun visitAdd(ctx: CASCParser.AddContext): ArithmeticExpression<*> {
        val leftExpression = ctx.findExpression(0)!!
        val rightExpression = ctx.findExpression(1)!!

        return ArithmeticExpression.Addition(leftExpression.accept(ev), rightExpression.accept(ev))
    }

    override fun visitSubtract(ctx: CASCParser.SubtractContext): ArithmeticExpression<*> {
        val leftExpression = ctx.findExpression(0)!!
        val rightExpression = ctx.findExpression(1)!!

        return ArithmeticExpression.Addition(leftExpression.accept(ev), rightExpression.accept(ev))
    }

    override fun visitMultiply(ctx: CASCParser.MultiplyContext): ArithmeticExpression<*> {
        val leftExpression = ctx.findExpression(0)!!
        val rightExpression = ctx.findExpression(1)!!

        return ArithmeticExpression.Addition(leftExpression.accept(ev), rightExpression.accept(ev))
    }

    override fun visitDivide(ctx: CASCParser.DivideContext): ArithmeticExpression<*> {
        val leftExpression = ctx.findExpression(0)!!
        val rightExpression = ctx.findExpression(1)!!

        return ArithmeticExpression.Addition(leftExpression.accept(ev), rightExpression.accept(ev))
    }
}