package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.*
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.Type
import org.casclang.casc.util.*

class ArithmeticVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Expression<*>>() {
    override fun visitAdd(ctx: CASCParser.AddContext): Expression<*> {
        val (leftExpression, rightExpression) = parseExpressions(ctx.findExpression(0), ctx.findExpression(1))

        return if (leftExpression is Value && rightExpression is Value) {
            if (leftExpression.type.isString() || rightExpression.type.isString()) {
                Value(BuiltInType.STRING, leftExpression.value + rightExpression.value)
            } else if (leftExpression.type.isNumeric() && rightExpression.type.isNumeric()) {
                val (t1, t2) = leftExpression.type to rightExpression.type
                val targetType = isEvaluable(t1, t2)
                val (ev1, ev2) = leftExpression.evaluate(targetType) to rightExpression.evaluate(targetType)

                Value(targetType, "${ev1 + ev2}")
            } else {
                addError(ctx, "Cannot apply addition on type ${leftExpression.type} and type ${rightExpression.type}")

                EmptyExpression(BuiltInType.VOID)
            }
        } else ArithmeticExpression.Addition(leftExpression, rightExpression)
    }

    override fun visitSubtract(ctx: CASCParser.SubtractContext): Expression<*> {
        val (leftExpression, rightExpression) = parseExpressions(ctx.findExpression(0), ctx.findExpression(1))

        return if (leftExpression is Value && rightExpression is Value) {
            if (leftExpression.type.isNumeric() && rightExpression.type.isNumeric()) {
                val (t1, t2) = leftExpression.type to rightExpression.type
                val targetType = isEvaluable(t1, t2)
                val (ev1, ev2) = leftExpression.evaluate(targetType) to rightExpression.evaluate(targetType)

                Value(targetType, "${ev1 - ev2}")
            } else {
                addError(ctx, "Cannot apply subtraction on type ${leftExpression.type} and type ${rightExpression.type}")

                EmptyExpression(BuiltInType.VOID)
            }
        } else ArithmeticExpression.Addition(leftExpression, rightExpression)
    }

    override fun visitMultiply(ctx: CASCParser.MultiplyContext): Expression<*> {
        val (leftExpression, rightExpression) = parseExpressions(ctx.findExpression(0), ctx.findExpression(1))

        return if (leftExpression is Value && rightExpression is Value) {
            if (leftExpression.type.isNumeric() && rightExpression.type.isNumeric()) {
                val (t1, t2) = leftExpression.type to rightExpression.type
                val targetType = isEvaluable(t1, t2)
                val (ev1, ev2) = leftExpression.evaluate(targetType) to rightExpression.evaluate(targetType)

                Value(targetType, "${ev1 * ev2}")
            } else {
                addError(ctx, "Cannot apply multiplication on type ${leftExpression.type} and type ${rightExpression.type}")

                EmptyExpression(BuiltInType.VOID)
            }
        } else ArithmeticExpression.Addition(leftExpression, rightExpression)
    }

    override fun visitDivide(ctx: CASCParser.DivideContext): Expression<*> {
        val (leftExpression, rightExpression) = parseExpressions(ctx.findExpression(0), ctx.findExpression(1))

        return if (leftExpression is Value && rightExpression is Value) {
            if (leftExpression.type.isNumeric() && rightExpression.type.isNumeric()) {
                val (t1, t2) = leftExpression.type to rightExpression.type
                val targetType = isEvaluable(t1, t2)
                val (ev1, ev2) = leftExpression.evaluate(targetType) to rightExpression.evaluate(targetType)

                Value(targetType, "${ev1 / ev2}")
            } else {
                addError(ctx, "Cannot apply division on type ${leftExpression.type} and type ${rightExpression.type}")

                EmptyExpression(BuiltInType.VOID)
            }
        } else ArithmeticExpression.Addition(leftExpression, rightExpression)
    }

    private fun parseExpressions(
        leftExpression: CASCParser.ExpressionContext?,
        rightExpression: CASCParser.ExpressionContext?
    ) = leftExpression!!.accept(ev) to rightExpression!!.accept(ev)

    private fun isEvaluable(t1: Type, t2: Type): BuiltInType =
        if (!t1.isNumeric() || !t2.isNumeric()) throw IllegalArgumentException()
        else if (t1.isDouble() || t2.isDouble()) BuiltInType.DOUBLE
        else if (t1.isFloat() || t2.isFloat()) BuiltInType.FLOAT
        else if (t1.isLong() || t2.isLong()) BuiltInType.LONG
        else BuiltInType.INT
}