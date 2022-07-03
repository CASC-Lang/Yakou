package org.yakou.lang.optimizer

import chaos.unity.nenggao.Span
import org.yakou.lang.ast.*
import org.yakou.lang.bind.TypeInfo
import org.yakou.lang.compilation.CompilationUnit

class Optimizer(val compilationUnit: CompilationUnit) {
    fun optimize() {
        optimizeYkFile(compilationUnit.ykFile!!)
    }

    private fun optimizeYkFile(ykFile: YkFile) {
        for (item in ykFile.items)
            optimizeItem(item)
    }

    private fun optimizeItem(item: Item) {
        when (item) {
            is Item.Class -> optimizeClass(item)
            is Item.Const -> optimizeConst(item)
            is Item.Function -> optimizeFunction(item)
            is Item.Package -> {
                if (item.items != null)
                    for (innerItem in item.items)
                        optimizeItem(innerItem)
            }
            is Item.StaticField -> optimizeStaticField(item)
        }
    }

    private fun optimizeClass(clazz: Item.Class) {
        if (clazz.classItems != null)
            for (classItem in clazz.classItems)
                optimizeClassItem(classItem)
    }

    private fun optimizeClassItem(classItem: ClassItem) {
        when (classItem) {
            is ClassItem.Field -> {
                if (classItem.expression != null)
                    classItem.expression = optimizeExpression(classItem.expression!!)
            }
        }
    }

    private fun optimizeConst(const: Item.Const) {
        const.expression = optimizeExpression(const.expression)
    }

    private fun optimizeFunction(function: Item.Function) {
        if (function.body != null)
            optimizeFunctionBody(function.body)
    }

    private fun optimizeFunctionBody(functionBody: FunctionBody) {
        when (functionBody) {
            is FunctionBody.BlockExpression -> {
                for (statement in functionBody.statements)
                    optimizeStatement(statement)
            }
            is FunctionBody.SingleExpression -> {
                functionBody.expression = optimizeExpression(functionBody.expression)
            }
        }
    }

    private fun optimizeStaticField(staticField: Item.StaticField) {
        staticField.expression = optimizeExpression(staticField.expression)
    }

    private fun optimizeStatement(statement: Statement) {

    }

    private fun optimizeExpression(expression: Expression): Expression {
        fun syntheticNumberLiteral(value: Double): Expression.NumberLiteral {
            val syntheticNumberLiteral =
                Expression.NumberLiteral(null, null, null, null, expression.span)

            syntheticNumberLiteral.value = value
            syntheticNumberLiteral.specifiedTypeInfo = expression.finalType as TypeInfo.Primitive
            syntheticNumberLiteral.originalType = expression.originalType
            syntheticNumberLiteral.finalType = expression.finalType

            return syntheticNumberLiteral
        }

        return when (expression) {
            is Expression.BinaryExpression -> {
                val optimizedLeftExpression = optimizeExpression(expression.leftExpression)
                val optimizedRightExpression = optimizeExpression(expression.rightExpression)

                if (optimizedLeftExpression is Expression.NumberLiteral && optimizedRightExpression is Expression.NumberLiteral) {
                    when (expression.operator.type) {
                        TokenType.Plus -> syntheticNumberLiteral(optimizedLeftExpression.value + optimizedRightExpression.value)
                        TokenType.Minus -> syntheticNumberLiteral(optimizedLeftExpression.value - optimizedRightExpression.value)
                        TokenType.Star -> syntheticNumberLiteral(optimizedLeftExpression.value * optimizedRightExpression.value)
                        TokenType.Slash -> syntheticNumberLiteral(optimizedLeftExpression.value / optimizedRightExpression.value)
                        else -> {
                            expression.leftExpression = optimizedLeftExpression
                            expression.rightExpression = optimizedRightExpression

                            expression
                        }
                    }
                } else {
                    expression.leftExpression = optimizedLeftExpression
                    expression.rightExpression = optimizedRightExpression

                    expression
                }
            }
            is Expression.NumberLiteral -> expression
            Expression.Undefined -> expression
        }
    }
}