package org.yakou.lang.optimizer

import chaos.unity.nenggao.Span
import org.yakou.lang.ast.Block
import org.yakou.lang.ast.Class
import org.yakou.lang.ast.ClassItem
import org.yakou.lang.ast.Const
import org.yakou.lang.ast.Expression
import org.yakou.lang.ast.ExpressionStatement
import org.yakou.lang.ast.Field
import org.yakou.lang.ast.For
import org.yakou.lang.ast.Func
import org.yakou.lang.ast.FunctionBody
import org.yakou.lang.ast.Impl
import org.yakou.lang.ast.ImplItem
import org.yakou.lang.ast.Item
import org.yakou.lang.ast.Package
import org.yakou.lang.ast.Return
import org.yakou.lang.ast.Statement
import org.yakou.lang.ast.StaticField
import org.yakou.lang.ast.VariableDeclaration
import org.yakou.lang.ast.YkFile
import org.yakou.lang.bind.ClassMember
import org.yakou.lang.bind.TypeInfo
import org.yakou.lang.bind.Variable
import org.yakou.lang.compilation.CompilationUnit

class Optimizer(private val compilationUnit: CompilationUnit) {
    fun optimize() {
        optimizeYkFile(compilationUnit.ykFile!!)
    }

    private fun optimizeYkFile(ykFile: YkFile) {
        for (item in ykFile.items)
            optimizeItem(item)
    }

    private fun optimizeItem(item: Item) {
        when (item) {
            is Class -> optimizeClass(item)
            is Const -> optimizeConst(item)
            is Package -> {
                if (item.items != null) {
                    for (innerItem in item.items)
                        optimizeItem(innerItem)
                }
            }
            is StaticField -> optimizeStaticField(item)
            is Func -> optimizeFunction(item)
            is Impl -> optimizeImpl(item)
        }
    }

    private fun optimizeClass(clazz: Class) {
        if (clazz.classItems != null) {
            for (classItem in clazz.classItems)
                optimizeClassItem(classItem)
        }

        // primary constructor does not need to optimize
    }

    private fun optimizeClassItem(classItem: ClassItem) {
        when (classItem) {
            is Field -> {
                if (classItem.expression != null) {
                    classItem.expression = optimizeExpression(classItem.expression!!)
                }
            }
        }
    }

    private fun optimizeConst(const: Const) {
        const.expression = optimizeExpression(const.expression)
    }

    private fun optimizeFunction(function: Func) {
        if (function.body != null) {
            optimizeFunctionBody(function.body)
        }
    }

    private fun optimizeImpl(impl: Impl) {
        if (impl.implItems != null) {
            for (item in impl.implItems) {
                optimizeImplItem(item)
            }
        }
    }

    private fun optimizeImplItem(item: ImplItem) {
        when (item) {
            is Class -> optimizeClass(item)
            is Const -> optimizeConst(item)
            is Func -> optimizeFunction(item)
            is Impl -> optimizeImpl(item)
            is StaticField -> optimizeStaticField(item)
        }
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

    private fun optimizeStaticField(staticField: StaticField) {
        staticField.expression = optimizeExpression(staticField.expression)
    }

    private fun optimizeStatement(statement: Statement) {
        when (statement) {
            is VariableDeclaration -> optimizeVariableDeclaration(statement)
            is For -> optimizeFor(statement)
            is Block -> optimizeBlock(statement)
            is Return -> optimizeReturn(statement)
            is ExpressionStatement -> {
                statement.expression = optimizeExpression(statement.expression)
            }
        }
    }

    private fun optimizeVariableDeclaration(statement: VariableDeclaration) {
        statement.expression = optimizeExpression(statement.expression)

        if (statement.expression is Expression.LiteralExpression) {
            // can be propagated to other expressions
            statement.variableInstance.propagatable = true
            statement.variableInstance.propagateExpression = statement.expression
        }
    }

    private fun optimizeFor(statement: For) {
        statement.conditionExpression = optimizeExpression(statement.conditionExpression)
        optimizeBlock(statement.block)
    }

    private fun optimizeBlock(statement: Block) {
        for (innerStatement in statement.statements) {
            optimizeStatement(innerStatement)
        }
    }

    private fun optimizeReturn(statement: Return) {
        statement.expression = optimizeExpression(statement.expression)
    }

    private fun optimizeExpression(expression: Expression): Expression = when (expression) {
        is Expression.BinaryExpression -> optimizeBinaryExpression(expression)
        is Expression.Identifier -> optimizeIdentifier(expression)
        is Expression.As -> optimizeAs(expression)
        is Expression.Parenthesized -> optimizeParenthesized(expression)
        is Expression.ConstructorCall -> optimizeConstructorCall(expression)
        is Expression.BoolLiteral -> expression
        is Expression.NumberLiteral -> expression
        is Expression.Empty -> expression
        Expression.Undefined -> expression
    }

    private fun optimizeBinaryExpression(expression: Expression.BinaryExpression): Expression {
        var finalExpression: Expression = expression
        val optimizedLeftExpression = optimizeExpression(expression.leftExpression)
        val optimizedRightExpression = optimizeExpression(expression.rightExpression)

        when (expression.operation) {
            Expression.BinaryExpression.BinaryOperation.Addition,
            Expression.BinaryExpression.BinaryOperation.Subtraction,
            Expression.BinaryExpression.BinaryOperation.Multiplication,
            Expression.BinaryExpression.BinaryOperation.Division,
            Expression.BinaryExpression.BinaryOperation.Modulo -> {
                if (optimizedLeftExpression is Expression.NumberLiteral && optimizedRightExpression is Expression.NumberLiteral) {
                    finalExpression = syntheticNumberLiteral(
                        expression.operation.getArithmeticFunctor()!!(
                            optimizedLeftExpression.value,
                            optimizedRightExpression.value
                        ),
                        expression.span,
                        expression.finalType
                    )
                }
            }

            Expression.BinaryExpression.BinaryOperation.LeftShift,
            Expression.BinaryExpression.BinaryOperation.RightShift,
            Expression.BinaryExpression.BinaryOperation.UnsignedRightShift -> {
                if (optimizedLeftExpression is Expression.NumberLiteral && optimizedRightExpression is Expression.NumberLiteral) {
                    finalExpression = syntheticNumberLiteral(
                        expression.operation.getBitwiseFunctor()!!(
                            optimizedLeftExpression.value.toLong(),
                            optimizedRightExpression.value.toInt()
                        ).toDouble(),
                        expression.span,
                        expression.finalType
                    )
                }
            }

            Expression.BinaryExpression.BinaryOperation.LogicalOr,
            Expression.BinaryExpression.BinaryOperation.LogicalAnd -> {
                if (optimizedLeftExpression is Expression.BoolLiteral && optimizedRightExpression is Expression.BoolLiteral) {
                    finalExpression = syntheticBoolLiteral(
                        expression.operation.getLogicalFunctor()!!(
                            optimizedLeftExpression.value,
                            optimizedRightExpression.value
                        ),
                        expression.span
                    )
                }
            }

            Expression.BinaryExpression.BinaryOperation.Equal,
            Expression.BinaryExpression.BinaryOperation.NotEqual,
            Expression.BinaryExpression.BinaryOperation.ExactEqual,
            Expression.BinaryExpression.BinaryOperation.ExactNotEqual -> {
                if (optimizedLeftExpression.finalType != optimizedRightExpression.finalType) {
                    // Types unmatched
                    finalExpression = syntheticBoolLiteral(
                        false,
                        expression.span
                    )
                } else if (optimizedLeftExpression is Expression.NumberLiteral && optimizedRightExpression is Expression.NumberLiteral) {
                    finalExpression = when (expression.operation) {
                        Expression.BinaryExpression.BinaryOperation.Equal,
                        Expression.BinaryExpression.BinaryOperation.ExactEqual ->
                            syntheticBoolLiteral(
                                optimizedLeftExpression.value == optimizedRightExpression.value,
                                expression.span
                            )

                        Expression.BinaryExpression.BinaryOperation.NotEqual,
                        Expression.BinaryExpression.BinaryOperation.ExactNotEqual ->
                            syntheticBoolLiteral(
                                optimizedLeftExpression.value != optimizedRightExpression.value,
                                expression.span
                            )

                        else -> finalExpression
                    }
                } else if (optimizedLeftExpression is Expression.BoolLiteral && optimizedRightExpression is Expression.BoolLiteral) {
                    finalExpression = when (expression.operation) {
                        Expression.BinaryExpression.BinaryOperation.Equal,
                        Expression.BinaryExpression.BinaryOperation.ExactEqual ->
                            syntheticBoolLiteral(
                                optimizedLeftExpression.value == optimizedRightExpression.value,
                                expression.span
                            )

                        Expression.BinaryExpression.BinaryOperation.NotEqual,
                        Expression.BinaryExpression.BinaryOperation.ExactNotEqual ->
                            syntheticBoolLiteral(
                                optimizedLeftExpression.value != optimizedRightExpression.value,
                                expression.span
                            )

                        else -> finalExpression
                    }
                }
            }
            Expression.BinaryExpression.BinaryOperation.Greater,
            Expression.BinaryExpression.BinaryOperation.Lesser,
            Expression.BinaryExpression.BinaryOperation.GreaterEqual,
            Expression.BinaryExpression.BinaryOperation.LesserEqual -> {
                if (optimizedLeftExpression is Expression.NumberLiteral && optimizedRightExpression is Expression.NumberLiteral) {
                    val flag = expression.operation.getComparisonFunctor()!!(
                        optimizedLeftExpression.value,
                        optimizedRightExpression.value
                    )
                    val comparedResult = when (expression.operation) {
                        Expression.BinaryExpression.BinaryOperation.Greater -> flag > 0
                        Expression.BinaryExpression.BinaryOperation.Lesser -> flag < 0
                        Expression.BinaryExpression.BinaryOperation.GreaterEqual -> flag >= 0
                        Expression.BinaryExpression.BinaryOperation.LesserEqual -> flag <= 0
                        else -> false
                    }

                    finalExpression = syntheticBoolLiteral(
                        comparedResult,
                        expression.span
                    )
                }
            }
        }

        if (finalExpression is Expression.BinaryExpression) {
            // Unoptimized but lhs and rhs might be optimized, thus we have to update lhs and rhs
            finalExpression.leftExpression = optimizedLeftExpression
            finalExpression.rightExpression = optimizedRightExpression
        }

        return finalExpression
    }

    private fun optimizeIdentifier(expression: Expression.Identifier): Expression {
        val symbolInstance = expression.symbolInstance

        return if (symbolInstance is Variable) {
            if (symbolInstance.propagatable) {
                symbolInstance.dereference()

                symbolInstance.propagateExpression
            } else {
                expression
            }
        } else if (symbolInstance is ClassMember.Field) {
            // Propagate expression when applicable

            if (symbolInstance.isConst) {
                // Inline value without side effect
                symbolInstance.propagateExpression!!
            } else if (symbolInstance.static && (symbolInstance.inline || !symbolInstance.mutable)) {
                // Inline when conditions met
                // - static final
                // - or force inline
                if (symbolInstance.propagateExpression is Expression.LiteralExpression) {
                    symbolInstance.propagateExpression
                } else {
                    expression
                }
            } else {
                expression
            }
        } else {
            expression
        }
    }

    private fun optimizeAs(expression: Expression.As): Expression {
        expression.expression = optimizeExpression(expression.expression)

        val innerExpression = expression.expression

        if (innerExpression is Expression.LiteralExpression && expression.finalType is TypeInfo.Primitive) {
            // Do not optimize boxing process!
            return when (innerExpression) {
                is Expression.BoolLiteral -> syntheticBoolLiteral(innerExpression.value, expression.span)
                is Expression.NumberLiteral -> syntheticNumberLiteral(
                    innerExpression.value,
                    expression.span,
                    expression.finalType
                )
            }
        }

        return expression // TODO: Optimize?
    }

    private fun optimizeParenthesized(expression: Expression.Parenthesized): Expression {
        expression.expression = optimizeExpression(expression.expression)

        return expression
    }

    private fun optimizeConstructorCall(expression: Expression.ConstructorCall): Expression.ConstructorCall {
        expression.arguments = expression.arguments.map(::optimizeExpression)
        
        return expression
    }

    private fun syntheticBoolLiteral(value: Boolean, span: Span): Expression.BoolLiteral {
        val syntheticBoolLiteral = Expression.BoolLiteral(null, span)

        syntheticBoolLiteral.value = value

        return syntheticBoolLiteral
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
