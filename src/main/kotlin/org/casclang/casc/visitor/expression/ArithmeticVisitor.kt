package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.ArithmeticExpression

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