package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.ArithmeticExpression
import org.casclang.casc.parsing.node.expression.FoldableExpression
import org.casclang.casc.parsing.node.expression.Value
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.Type
import org.casclang.casc.util.div
import org.casclang.casc.util.minus
import org.casclang.casc.util.plus
import org.casclang.casc.util.times

class ArithmeticVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<FoldableExpression<*>>() {
    override fun visitAdd(ctx: CASCParser.AddContext): FoldableExpression<*> {
        val (leftExpression, rightExpression) = parseExpressions(ctx.findExpression(0), ctx.findExpression(1))

        return if (leftExpression is Value && rightExpression is Value) {
            if (leftExpression.type.isString() || rightExpression.type.isString()) {
                Value(BuiltInType.STRING, leftExpression.value + rightExpression.value)
            } else if (leftExpression.type.isNumeric() && rightExpression.type.isNumeric()) {
                val (t1, t2) = leftExpression.type to rightExpression.type
                val (v1, v2) = leftExpression.value to rightExpression.value
                val targetType = isEvaluable(t1, t2)
                val (ev1, ev2) = evaluate(targetType, v1, v2)

                Value(targetType, ev1 + ev2)
            } else {
                throw RuntimeException("Cannot apply addition on type ${leftExpression.type} and type ${rightExpression.type}")
            }
        } else ArithmeticExpression.Addition(leftExpression, rightExpression)
    }

    override fun visitSubtract(ctx: CASCParser.SubtractContext): FoldableExpression<*> {
        val (leftExpression, rightExpression) = parseExpressions(ctx.findExpression(0), ctx.findExpression(1))

        return if (leftExpression is Value && rightExpression is Value) {
            if (leftExpression.type.isNumeric() && rightExpression.type.isNumeric()) {
                val (t1, t2) = leftExpression.type to rightExpression.type
                val (v1, v2) = leftExpression.value to rightExpression.value
                val targetType = isEvaluable(t1, t2)
                val (ev1, ev2) = evaluate(targetType, v1, v2)

                Value(targetType, ev1 - ev2)
            } else {
                throw RuntimeException("Cannot apply subtraction on type ${leftExpression.type} and type ${rightExpression.type}")
            }
        } else ArithmeticExpression.Addition(leftExpression, rightExpression)
    }

    override fun visitMultiply(ctx: CASCParser.MultiplyContext): FoldableExpression<*> {
        val (leftExpression, rightExpression) = parseExpressions(ctx.findExpression(0), ctx.findExpression(1))

        return if (leftExpression is Value && rightExpression is Value) {
            if (leftExpression.type.isNumeric() && rightExpression.type.isNumeric()) {
                val (t1, t2) = leftExpression.type to rightExpression.type
                val (v1, v2) = leftExpression.value to rightExpression.value
                val targetType = isEvaluable(t1, t2)
                val (ev1, ev2) = evaluate(targetType, v1, v2)

                Value(targetType, ev1 * ev2)
            } else {
                throw RuntimeException("Cannot apply multiplication on type ${leftExpression.type} and type ${rightExpression.type}")
            }
        } else ArithmeticExpression.Addition(leftExpression, rightExpression)
    }

    override fun visitDivide(ctx: CASCParser.DivideContext): FoldableExpression<*> {
        val (leftExpression, rightExpression) = parseExpressions(ctx.findExpression(0), ctx.findExpression(1))

        return if (leftExpression is Value && rightExpression is Value) {
            if (leftExpression.type.isNumeric() && rightExpression.type.isNumeric()) {
                val (t1, t2) = leftExpression.type to rightExpression.type
                val (v1, v2) = leftExpression.value to rightExpression.value
                val targetType = isEvaluable(t1, t2)
                val (ev1, ev2) = evaluate(targetType, v1, v2)

                Value(targetType, ev1 / ev2)
            } else {
                throw RuntimeException("Cannot apply division on type ${leftExpression.type} and type ${rightExpression.type}")
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

    private fun evaluate(targetType: BuiltInType, v1: String, v2: String): Pair<Number, Number> =
        evaluate(targetType, v1) to evaluate(targetType, v2)

    private fun evaluate(targetType: BuiltInType, value: String): Number =
        when (targetType) {
            BuiltInType.DOUBLE -> value.toDouble()
            BuiltInType.FLOAT -> value.toFloat()
            BuiltInType.LONG -> value.toLong()
            BuiltInType.INT -> value.toInt()
            else -> throw IllegalArgumentException()
        }
}