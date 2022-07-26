package org.yakou.lang.checker

import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.ast.*
import org.yakou.lang.bind.PrimitiveType
import org.yakou.lang.bind.TypeInfo
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.util.SpanHelper
import org.yakou.lang.util.colorize

class Checker(private val compilationUnit: CompilationUnit) {
    private var currentFunction: Item.Function? = null

    fun check() {
        checkYkFile(compilationUnit.ykFile!!)
    }

    private fun checkYkFile(ykFile: YkFile) {
        for (item in ykFile.items)
            checkItem(item)
    }

    private fun checkItem(item: Item) {
        when (item) {
            is Item.Package -> {
                if (item.items != null)
                    for (innerItem in item.items)
                        checkItem(innerItem)
            }
            is Item.Const -> checkConst(item)
            is Item.StaticField -> checkStaticField(item)
            is Item.Class -> checkClass(item)
            is Item.Function -> checkFunction(item)
        }
    }

    private fun checkConst(const: Item.Const) {
        // Check constant has illegal modifiers
        if (const.modifiers.hasModifier(Modifier.Mut)) {
            reportIllegalMut(const.modifiers[Modifier.Mut]!!, "Constant cannot be mutable")
        }
        // Check expression
        checkExpression(const.expression)
        // Check expression's type explicitly matches constant's explicit type
        if (const.typeInfo != const.expression.finalType) {
            val explicitTypeLiteral = colorize(const.typeInfo.toString(), compilationUnit, Attribute.CYAN_TEXT())
            val expressionTypeLiteral =
                colorize(const.expression.finalType.toString(), compilationUnit, Attribute.RED_TEXT())

            compilationUnit.reportBuilder
                .error(
                    SpanHelper.expandView(const.span, compilationUnit.maxLineCount),
                    "Cannot implicitly cast constant's expression"
                )
                .label(const.explicitType.span, "Expression type should explicitly be `$explicitTypeLiteral`")
                .color(Attribute.CYAN_TEXT())
                .build()
                .label(const.expression.span, "Expression has type `$expressionTypeLiteral`")
                .color(Attribute.RED_TEXT())
                .build().build()
        }

        if (!(const.expression.originalType canImplicitCast const.typeInfo)) {
            reportUnableToImplicitlyCast(
                const.span,
                const.expression.span,
                const.expression.originalType.toString(),
                const.explicitType.span,
                const.typeInfo.toString()
            )
        }
    }

    private fun checkStaticField(staticField: Item.StaticField) {
        // Check expression
        checkExpression(staticField.expression)
        // Check if expression is not castable to explicit type
        if (staticField.expression.finalType is TypeInfo.Primitive) {
            // Check if it's applicable to convert static field into constant
            if (staticField.expression.finalType is TypeInfo.Primitive &&
                staticField.expression is Expression.LiteralExpression &&
                !staticField.modifiers.hasModifier(Modifier.Mut)
            ) {
                val staticLiteral = colorize("static", compilationUnit, Attribute.CYAN_TEXT())
                val constLiteral = colorize("const", compilationUnit, Attribute.CYAN_TEXT())
                val expressionTypeLiteral =
                    colorize(staticField.expression.finalType.toString(), compilationUnit, Attribute.GREEN_TEXT())

                compilationUnit.reportBuilder
                    .warning(
                        SpanHelper.expandView(staticField.span, compilationUnit.maxLineCount),
                        "Static field is sufficient to convert into constant"
                    )
                    .label(staticField.static.span, "Consider replace `$staticLiteral` with `$constLiteral`")
                    .color(Attribute.YELLOW_TEXT())
                    .build()
                    .label(staticField.expression.span, "Expression has primitive type `$expressionTypeLiteral`")
                    .color(Attribute.CYAN_TEXT())
                    .build().build()
            }
        }

        if (!(staticField.expression.originalType canImplicitCast staticField.typeInfo)) {
            reportUnableToImplicitlyCast(
                staticField.span,
                staticField.expression.span,
                staticField.expression.originalType.toString(),
                staticField.explicitType.span,
                staticField.typeInfo.toString()
            )
        }
    }

    private fun checkClass(clazz: Item.Class) {
        if (clazz.classItems != null)
            for (classItem in clazz.classItems)
                checkClassItem(classItem)

        if (clazz.modifiers.hasModifier(Modifier.Inline)) {
            reportIllegalInline(clazz.modifiers[Modifier.Inline]!!, "Class cannot be inlined")
        }
    }

    private fun checkClassItem(classItem: ClassItem) {
        when (classItem) {
            is ClassItem.Field -> checkField(classItem)
        }
    }

    private fun checkField(field: ClassItem.Field) {
        if (field.expression != null) {
            checkExpression(field.expression!!)

            if (!(field.expression!!.originalType canImplicitCast field.typeInfo)) {
                reportUnableToImplicitlyCast(
                    field.span,
                    field.expression!!.span,
                    field.expression!!.originalType.toString(),
                    field.explicitType.span,
                    field.typeInfo.toString()
                )
            }
        }
    }

    private fun checkFunction(function: Item.Function) {
        currentFunction = function

        // Check top-level function has illegal modifiers
        if (function.functionInstance.ownerTypeInfo is TypeInfo.PackageClass) {
            if (function.modifiers.hasModifier(Modifier.Mut)) {
                reportIllegalMut(function.modifiers[Modifier.Mut]!!, "Top-level function cannot be mutable")
            }
            if (function.self != null) {
                val coloredSelf = colorize("self", compilationUnit, Attribute.CYAN_TEXT())

                reportIllegalSelf(
                    function.self.span.expand(function.selfComma?.span),
                    "Top-level function cannot access owner class from value-parameter `$coloredSelf`"
                )
            }
        }

        if (function.body != null)
            checkFunctionBody(function.body)

        checkControlFlow(function)

        currentFunction = null
    }

    private fun checkControlFlow(function: Item.Function) {
        if (function.functionInstance.returnTypeInfo != TypeInfo.Primitive.UNIT_TYPE_INFO) {
            // Expected last statement should be return statement
            when (val body = function.body) {
                is FunctionBody.BlockExpression -> {
                    if (body.statements.lastOrNull() !is Statement.Return) {
                        reportMissingReturn(function, body.closeBrace, function.returnTypeInfo)
                    }
                }
                is FunctionBody.SingleExpression -> {

                }
                else -> {}
            }
        }
    }

    private fun checkFunctionBody(functionBody: FunctionBody) {
        when (functionBody) {
            is FunctionBody.BlockExpression -> {
                for (statement in functionBody.statements)
                    checkStatement(statement)
            }
            is FunctionBody.SingleExpression -> checkExpression(functionBody.expression)
        }
    }

    private fun checkStatement(statement: Statement) {
        when (statement) {
            is Statement.VariableDeclaration -> checkVariableDeclaration(statement)
            is Statement.Return -> checkReturn(statement)
            is Statement.ExpressionStatement -> checkExpression(statement.expression)
        }
    }

    private fun checkVariableDeclaration(variableDeclaration: Statement.VariableDeclaration) {
        if (variableDeclaration.name.literal == "_") {
            when (variableDeclaration.expression) {
                is Expression.BinaryExpression -> {}
                is Expression.Identifier -> {}
                is Expression.NumberLiteral -> {
                    // No effect variable declaration
                    reportIgnoredVariableHasNoEffect(variableDeclaration.name.span, variableDeclaration.expression.span)
                }
                is Expression.Empty -> {}
                Expression.Undefined -> TODO("UNREACHABLE")
            }
        }
    }

    private fun checkReturn(`return`: Statement.Return) {
        checkExpression(`return`.expression)

        if (!(`return`.expression.originalType canImplicitCast currentFunction!!.returnTypeInfo)) {
            reportUnableToImplicitlyCast(
                currentFunction!!.span,
                `return`.expression.span,
                `return`.expression.originalType.toString(),
                currentFunction!!.returnType?.span ?: currentFunction!!.identifier.span,
                currentFunction!!.functionInstance.returnTypeInfo.toString()
            )
        }
    }

    private fun checkExpression(expression: Expression) {
        when (expression) {
            is Expression.BinaryExpression -> checkBinaryExpression(expression)
            is Expression.Identifier -> checkIdentifier(expression)
            is Expression.NumberLiteral -> checkNumberLiteral(expression)
            is Expression.Empty -> {}
            Expression.Undefined -> TODO("UNREACHABLE")
        }
    }

    private fun checkBinaryExpression(binaryExpression: Expression.BinaryExpression) {
        checkExpression(binaryExpression.leftExpression)
        checkExpression(binaryExpression.rightExpression)
    }

    private fun checkIdentifier(identifier: Expression.Identifier) {
        // ??
    }

    private fun checkNumberLiteral(numberLiteral: Expression.NumberLiteral) {
        val originalType = numberLiteral.originalType
        val isIntegerValue = numberLiteral.value.toInt().toDouble() != numberLiteral.value

        if (numberLiteral.dot != null && numberLiteral.floatPart != null && numberLiteral.specifiedType != null) {
            if (isIntegerValue) {
                // Number literal represents a float number but specified with an integer type
                val specifiedTypeLiteral =
                    colorize(numberLiteral.specifiedType.standardizeType(), compilationUnit, Attribute.RED_TEXT())

                compilationUnit.reportBuilder
                    .error(
                        SpanHelper.expandView(numberLiteral.span, compilationUnit.maxLineCount),
                        "Illegal type suffix `$specifiedTypeLiteral` for float number literal"
                    )
                    .label(numberLiteral.floatPart.span, "Float number literal here")
                    .color(Attribute.CYAN_TEXT())
                    .build()
                    .label(numberLiteral.specifiedType.span, "Illegal type suffix here")
                    .color(Attribute.RED_TEXT())
                    .build().build()
            } else if (originalType is TypeInfo.Primitive) {
                if (numberLiteral.specifiedTypeInfo?.type == PrimitiveType.F64 && originalType.type == PrimitiveType.F64) {
                    // Redundant type suffix `f64` for float number literal
                    val specifiedTypeLiteral =
                        colorize(numberLiteral.specifiedType.standardizeType(), compilationUnit, Attribute.CYAN_TEXT())

                    compilationUnit.reportBuilder
                        .warning(
                            SpanHelper.expandView(numberLiteral.span, compilationUnit.maxLineCount),
                            "Redundant type suffix `$specifiedTypeLiteral` for float number literal"
                        )
                        .label(numberLiteral.floatPart.span, "Float number literal here")
                        .color(Attribute.CYAN_TEXT())
                        .build()
                        .label(numberLiteral.specifiedType.span, "Redundant type suffix here")
                        .color(Attribute.YELLOW_TEXT())
                        .build().build()
                } else if (numberLiteral.specifiedTypeInfo?.type == PrimitiveType.I32 && originalType.type == PrimitiveType.I32) {
                    // Redundant type suffix `i32` for integer number literal
                    val specifiedTypeLiteral =
                        colorize(numberLiteral.specifiedType.standardizeType(), compilationUnit, Attribute.CYAN_TEXT())

                    compilationUnit.reportBuilder
                        .warning(
                            SpanHelper.expandView(numberLiteral.span, compilationUnit.maxLineCount),
                            "Redundant type suffix `$specifiedTypeLiteral` for integer number literal"
                        )
                        .label(numberLiteral.floatPart.span, "Integer number literal here")
                        .color(Attribute.CYAN_TEXT())
                        .build()
                        .label(numberLiteral.specifiedType.span, "Redundant type suffix here")
                        .color(Attribute.YELLOW_TEXT())
                        .build().build()
                }
            }
        }
    }

    private fun reportIgnoredVariableHasNoEffect(variableSpan: Span, expressionSpan: Span) {
        compilationUnit.reportBuilder
            .warning(
                SpanHelper.expandView(variableSpan.expand(expressionSpan), compilationUnit.maxLineCount),
                "Expression of ignored variable has no side effect"
            )
            .label(variableSpan, "Variable ignored here")
            .hint("It's safe to remove this variable declaration")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(expressionSpan, "Expression has no side effect")
            .color(Attribute.YELLOW_TEXT())
            .build().build()
    }

    private fun reportIllegalInline(span: Span, labelMessage: String) {
        val inlineLiteral = colorize("inline", compilationUnit, Attribute.RED_TEXT())

        compilationUnit.reportBuilder
            .error(SpanHelper.expandView(span, compilationUnit.maxLineCount), "Illegal modifier `$inlineLiteral`")
            .label(span, labelMessage)
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportIllegalMut(span: Span, labelMessage: String) {
        val mutLiteral = colorize("mut", compilationUnit, Attribute.RED_TEXT())

        compilationUnit.reportBuilder
            .error(SpanHelper.expandView(span, compilationUnit.maxLineCount), "Illegal modifier `$mutLiteral`")
            .label(span, labelMessage)
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportIllegalSelf(span: Span, labelMessage: String) {
        val selfLiteral = colorize("self", compilationUnit, Attribute.RED_TEXT())

        compilationUnit.reportBuilder
            .error(SpanHelper.expandView(span, compilationUnit.maxLineCount), "Illegal value-parameter `$selfLiteral`")
            .label(span, labelMessage)
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportMissingReturn(
        function: Item.Function,
        closeBrace: Token,
        returnTypeInfo: TypeInfo
    ) {
        val functionString = colorize(function.functionInstance.toString(), compilationUnit, Attribute.CYAN_TEXT())
        val returnLiteral = colorize("return", compilationUnit, Attribute.CYAN_TEXT())
        val returnType = colorize(returnTypeInfo.toString(), compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(function.span, compilationUnit.maxLineCount),
                "Missing `$returnLiteral` statement at the end of function `$functionString`"
            )
            .label(function.returnType!!.span, "Function needs to return `$returnType`")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(closeBrace.span, "Missing `$returnLiteral` statement at the end of function")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportUnableToImplicitlyCast(
        commonSpan: Span,
        fromTypeSpan: Span,
        fromTypeLiteral: String,
        toTypeSpan: Span,
        toTypeLiteral: String
    ) {
        val fromType = colorize(fromTypeLiteral, compilationUnit, Attribute.RED_TEXT())
        val toType = colorize(toTypeLiteral, compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(commonSpan, compilationUnit.maxLineCount),
                "Unable to implicitly cast `$fromType` into `$toType`"
            )
            .label(toTypeSpan, "Expected `$toType`")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(fromTypeSpan, "Got `$fromType`")
            .color(Attribute.RED_TEXT())
            .build().build()
    }
}