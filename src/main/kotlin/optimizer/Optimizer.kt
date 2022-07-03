package optimizer

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
            is Item.Const -> TODO()
            is Item.Function -> TODO()
            is Item.Package -> TODO()
            is Item.StaticField -> TODO()
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

    private fun optimizeExpression(expression: Expression): Expression = when (expression) {
        is Expression.BinaryExpression -> {
            val optimizedLeftExpression = optimizeExpression(expression.leftExpression)
            val optimizedRightExpression = optimizeExpression(expression.rightExpression)

            if (optimizedLeftExpression is Expression.NumberLiteral && optimizedRightExpression is Expression.NumberLiteral) {
                when (expression.operator.type) {
                    TokenType.Plus -> {
                        val syntheticNumberLiteral = Expression.NumberLiteral(null, null, null, null, expression.span)

                        syntheticNumberLiteral.value = optimizedLeftExpression.value + optimizedRightExpression.value
                        syntheticNumberLiteral.specifiedTypeInfo = expression.finalType as TypeInfo.Primitive

                        syntheticNumberLiteral
                    }
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