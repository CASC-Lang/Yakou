package org.yakou.lang.optimizer

import chaos.unity.nenggao.Span
import org.yakou.lang.ast.*
import org.yakou.lang.bind.ClassMember
import org.yakou.lang.bind.TypeInfo
import org.yakou.lang.bind.Variable
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
        when (statement) {
            is Statement.VariableDeclaration -> optimizeVariableDeclaration(statement)
            is Statement.Return -> optimizeReturn(statement)
            is Statement.ExpressionStatement -> {
                statement.expression = optimizeExpression(statement.expression)
            }
        }
    }

    private fun optimizeVariableDeclaration(statement: Statement.VariableDeclaration) {
        statement.expression = optimizeExpression(statement.expression)

        if (statement.expression is Expression.LiteralExpression) {
            // can be propagated to other expressions
            statement.variableInstance.propagatable = true
            statement.variableInstance.propagateExpression = statement.expression
        }
    }

    private fun optimizeReturn(statement: Statement.Return) {
        statement.expression = optimizeExpression(statement.expression)
    }

    private fun optimizeExpression(expression: Expression): Expression = when (expression) {
        is Expression.BinaryExpression -> optimizeBinaryExpression(expression)
        is Expression.Identifier -> optimizeIdentifier(expression)
        is Expression.As -> optimizeAs(expression)
        is Expression.NumberLiteral -> expression
        is Expression.Empty -> expression
        Expression.Undefined -> expression
    }

    private fun optimizeBinaryExpression(expression: Expression.BinaryExpression): Expression {
        val optimizedLeftExpression = optimizeExpression(expression.leftExpression)
        val optimizedRightExpression = optimizeExpression(expression.rightExpression)

        return if (optimizedLeftExpression is Expression.NumberLiteral && optimizedRightExpression is Expression.NumberLiteral) {
            when (expression.operation) {
                Expression.BinaryExpression.BinaryOperation.Addition -> {
                    syntheticNumberLiteral(
                        optimizedLeftExpression.value + optimizedRightExpression.value,
                        expression.span,
                        expression.finalType
                    )
                }
                Expression.BinaryExpression.BinaryOperation.Subtraction -> {
                    syntheticNumberLiteral(
                        optimizedLeftExpression.value - optimizedRightExpression.value,
                        expression.span,
                        expression.finalType
                    )
                }
                Expression.BinaryExpression.BinaryOperation.Multiplication -> {
                    syntheticNumberLiteral(
                        optimizedLeftExpression.value * optimizedRightExpression.value,
                        expression.span,
                        expression.finalType
                    )
                }
                Expression.BinaryExpression.BinaryOperation.Division -> {
                    syntheticNumberLiteral(
                        optimizedLeftExpression.value / optimizedRightExpression.value,
                        expression.span,
                        expression.finalType
                    )
                }
                Expression.BinaryExpression.BinaryOperation.Modulo -> {
                    syntheticNumberLiteral(
                        optimizedLeftExpression.value % optimizedRightExpression.value,
                        expression.span,
                        expression.finalType
                    )
                }
                Expression.BinaryExpression.BinaryOperation.UnsignedRightShift -> {
                    syntheticNumberLiteral(
                        (optimizedLeftExpression.value.toLong() ushr optimizedRightExpression.value.toInt()).toDouble(),
                        expression.span,
                        expression.finalType
                    )
                }
                Expression.BinaryExpression.BinaryOperation.RightShift -> {
                    syntheticNumberLiteral(
                        (optimizedLeftExpression.value.toLong() shr optimizedRightExpression.value.toInt()).toDouble(),
                        expression.span,
                        expression.finalType
                    )
                }
                Expression.BinaryExpression.BinaryOperation.LeftShift -> {
                    syntheticNumberLiteral(
                        (optimizedLeftExpression.value.toLong() shl optimizedRightExpression.value.toInt()).toDouble(),
                        expression.span,
                        expression.finalType
                    )
                }
            }
        } else {
            expression.leftExpression = optimizedLeftExpression
            expression.rightExpression = optimizedRightExpression

            expression
        }
    }

    private fun optimizeIdentifier(expression: Expression.Identifier): Expression {
        val symbolInstance = expression.symbolInstance

        return if (symbolInstance is Variable) {
            if (symbolInstance.propagatable) {
                symbolInstance.dereference()

                symbolInstance.propagateExpression
            } else expression
        } else if (symbolInstance is ClassMember.Field) {
            // Propagate expression when applicable

            if (symbolInstance.isConst) {
                // Inline value without side effect
                symbolInstance.propagateExpression!!
            } else if (symbolInstance.isStatic && symbolInstance.inline) {
                // Inline when conditions met
                if (symbolInstance.propagateExpression is Expression.LiteralExpression) {
                    symbolInstance.propagateExpression
                } else expression
            } else expression
        } else expression
    }

    private fun optimizeAs(expression: Expression.As): Expression {
        expression.expression = optimizeExpression(expression.expression)

        val innerExpression = expression.expression

        if (innerExpression is Expression.LiteralExpression && expression.finalType !is TypeInfo.Class) {
            // Do not optimize boxing process!
            return when (innerExpression) {
                is Expression.NumberLiteral -> syntheticNumberLiteral(
                    innerExpression.value,
                    expression.span,
                    expression.finalType
                )
            }
        }

        return expression // TODO: Optimize?
    }

    private fun syntheticNumberLiteral(value: Double, span: Span, finalType: TypeInfo): Expression.NumberLiteral {
        val syntheticNumberLiteral =
            Expression.NumberLiteral(null, null, null, null, span)

        syntheticNumberLiteral.value = value
        syntheticNumberLiteral.specifiedTypeInfo = finalType as TypeInfo.Primitive
        syntheticNumberLiteral.originalType = finalType
        syntheticNumberLiteral.finalType = finalType

        return syntheticNumberLiteral
    }
}