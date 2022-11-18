package org.yakou.lang.checker

import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Attribute
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
import org.yakou.lang.ast.GenericDeclarationParameters
import org.yakou.lang.ast.Impl
import org.yakou.lang.ast.ImplItem
import org.yakou.lang.ast.Item
import org.yakou.lang.ast.Modifier
import org.yakou.lang.ast.Package
import org.yakou.lang.ast.PrimaryConstructor
import org.yakou.lang.ast.Return
import org.yakou.lang.ast.Statement
import org.yakou.lang.ast.StaticField
import org.yakou.lang.ast.Token
import org.yakou.lang.ast.Type
import org.yakou.lang.ast.VariableDeclaration
import org.yakou.lang.ast.YkFile
import org.yakou.lang.bind.PrimitiveType
import org.yakou.lang.bind.Table
import org.yakou.lang.bind.TypeChecker
import org.yakou.lang.bind.TypeInfo
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.util.SpanHelper
import org.yakou.lang.util.colorize

class Checker(private val compilationUnit: CompilationUnit) {
    private val table: Table = compilationUnit.session.table
    private var currentFunction: Func? = null

    fun check() {
        checkYkFile(compilationUnit.ykFile!!)
    }

    private fun checkYkFile(ykFile: YkFile) {
        for (item in ykFile.items) {
            checkItem(item)
        }
    }

    private fun checkItem(item: Item) {
        when (item) {
            is Package -> item.items?.forEach(::checkItem)
            is Const -> checkConst(item)
            is StaticField -> checkStaticField(item)
            is Class -> checkClass(item)
            is Func -> checkFunction(item)
            is Impl -> checkImpl(item)
        }
    }

    private fun checkConst(const: Const) {
        // Check if constant has illegal modifiers
        checkConstModifiers(const)

        // Check expression
        checkExpression(const.expression)

        // Check expression's type explicitly matches constant's explicit type
        if (const.typeInfo != const.expression.finalType) {
            reportImplicitTypeConversionOnConstant(const)
        }

        if (!(const.expression.finalType.canImplicitCast(const.typeInfo))) {
            reportUnableToImplicitlyCast(
                const.span,
                const.expression.span,
                const.expression.originalType.toString(),
                const.explicitType.span,
                const.typeInfo.toString()
            )
        }
    }

    private fun checkConstModifiers(const: Const) {
        val modifiers = const.modifiers

        for ((modifier, span) in modifiers) {
            when (modifier) {
                Modifier.Mut -> reportIllegalMut(
                    span,
                    "Const cannot be mutable"
                )

                Modifier.Inner -> reportIllegalInner(
                    span,
                    "Const cannot be inner"
                )

                else -> {}
            }
        }
    }

    private fun checkStaticField(staticField: StaticField) {
        // Check if static field has illegal modifiers
        checkStaticFieldModifiers(staticField)

        // Check expression
        checkExpression(staticField.expression)

        // Check if expression is not castable to explicit type
        if (staticField.expression.finalType is TypeInfo.Primitive) {
            // Check if it's applicable to convert static field into constant
            if (staticField.expression.finalType is TypeInfo.Primitive &&
                staticField.expression is Expression.LiteralExpression &&
                !staticField.modifiers.containsKey(Modifier.Mut)
            ) {
                reportStaticFieldAsConstant(staticField)
            }
        }

        if (!(staticField.expression.finalType.canImplicitCast(staticField.typeInfo))) {
            reportUnableToImplicitlyCast(
                staticField.span,
                staticField.expression.span,
                staticField.expression.originalType.toString(),
                staticField.explicitType.span,
                staticField.typeInfo.toString()
            )
        }
    }

    private fun checkStaticFieldModifiers(staticField: StaticField) {
        val modifiers = staticField.modifiers

        for ((modifier, span) in modifiers) {
            when (modifier) {
                Modifier.Inner -> reportIllegalInner(
                    span,
                    "Static field cannot be inner"
                )

                else -> {}
            }
        }

        // Check if static field is both mutable and inline-able
        if (modifiers.containsKey(Modifier.Mut) && modifiers.containsKey(Modifier.Inline)) {
            reportMutableStaticFieldIsInlined(staticField)
        }
    }

    private fun checkClass(clazz: Class) {
        // Check if class has illegal modifiers
        checkClassModifiers(clazz)

        clazz.classItems?.forEach(::checkClassItem)
        clazz.primaryConstructor?.let(::checkPrimaryConstructor)
        clazz.superClassConstructorCall?.let(::checkSuperClassConstructorCall)
    }

    private fun checkClassModifiers(clazz: Class) {
        val modifiers = clazz.modifiers

        for ((modifier, span) in modifiers) {
            when (modifier) {
                Modifier.Inline -> reportIllegalInline(
                    span,
                    "Class cannot be inlined"
                )

                else -> {}
            }
        }
    }

    private fun checkPrimaryConstructor(primaryConstructor: PrimaryConstructor) {
        checkPrimaryConstructorModifiers(primaryConstructor)

        for (parameter in primaryConstructor.parameters) {
            checkConstructorParameter(parameter)
        }
    }

    private fun checkPrimaryConstructorModifiers(primaryConstructor: PrimaryConstructor) {
        val modifiers = primaryConstructor.modifiers

        for ((modifier, span) in modifiers) {
            when (modifier) {
                Modifier.Inline -> reportIllegalInline(
                    span,
                    "Constructor cannot be inlined"
                )

                Modifier.Mut -> reportIllegalMut(
                    span,
                    "Constructor cannot be mutable"
                )

                Modifier.Inner -> reportIllegalInner(
                    span,
                    "Constructor cannot be inner"
                )

                else -> {}
            }
        }
    }

    private fun checkConstructorParameter(constructorParameter: PrimaryConstructor.ConstructorParameter) {
        checkConstructorParameterModifiers(constructorParameter)
    }

    private fun checkConstructorParameterModifiers(constructorParameter: PrimaryConstructor.ConstructorParameter) {
        val modifiers = constructorParameter.modifiers

        for ((modifier, span) in modifiers) {
            when (modifier) {
                Modifier.Inline -> reportIllegalInline(
                    span,
                    "Constructor field parameter cannot be inlined"
                )

                Modifier.Inner -> reportIllegalInner(
                    span,
                    "Constructor field parameter cannot be inner"
                )

                else -> {}
            }
        }
    }

    private fun checkSuperClassConstructorCall(superClassConstructorCall: Class.SuperClassConstructorCall) {
        if (java.lang.reflect.Modifier.isFinal(superClassConstructorCall.superClassTypeInfo.access)) {
            reportInheritsFromImmutableClass(superClassConstructorCall)
        }
    }

    private fun checkClassItem(classItem: ClassItem) {
        when (classItem) {
            is Field -> checkField(classItem)
        }
    }

    private fun checkField(field: Field) {
        if (field.expression != null) {
            checkExpression(field.expression!!)

            if (!field.expression!!.finalType.canImplicitCast(field.typeInfo)) {
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

    private fun checkFunction(function: Func) {
        currentFunction = function

        if (function.functionInstance.ownerTypeInfo is TypeInfo.PackageClass) {
            // Check if top-level function has illegal modifiers
            checkPackageClassFunctionModifiers(function)

            if (function.self != null) {
                val coloredSelf = colorize("self", compilationUnit, Attribute.CYAN_TEXT())

                reportIllegalSelf(
                    function.self.span.expand(function.selfComma?.span),
                    "Top-level function cannot access owner class from value-parameter `$coloredSelf`"
                )
            }
        }

        function.body?.let(::checkFunctionBody)

        checkControlFlow(function)

        currentFunction = null
    }

    private fun checkPackageClassFunctionModifiers(function: Func) {
        val modifiers = function.modifiers

        for ((modifier, span) in modifiers) {
            when (modifier) {
                Modifier.Mut -> reportIllegalMut(
                    span,
                    "Top-level function cannot be mutable"
                )

                else -> {}
            }
        }
    }

    private fun checkControlFlow(function: Func) {
        if (function.functionInstance.returnTypeInfo != TypeInfo.Primitive.UNIT_TYPE_INFO) {
            // Expected last statement should be return statement
            when (val body = function.body) {
                is FunctionBody.BlockExpression -> {
                    if (body.statements.lastOrNull() !is Return) {
                        reportMissingReturn(function, body.closeBrace, function.returnTypeInfo)
                    }
                }

                is FunctionBody.SingleExpression -> {
                    // Single expression only required to have same return type as current function's return type
                }

                else -> {}
            }
        }
    }

    private fun checkFunctionBody(functionBody: FunctionBody) {
        when (functionBody) {
            is FunctionBody.BlockExpression -> {
                for (statement in functionBody.statements) {
                    checkStatement(statement)
                }
            }

            is FunctionBody.SingleExpression -> checkExpression(functionBody.expression)
        }
    }

    private fun checkImpl(impl: Impl) {
        // Check if impl has illegal modifiers
        checkImplModifiers(impl)

        // Check if impl has same generic constraint bounds as the owner class does
        checkImplGenericSameAsOwnerClassGeneric(impl)

        impl.implItems?.forEach(::checkImplItem)
    }

    private fun checkImplGenericSameAsOwnerClassGeneric(impl: Impl) {
        val genericDeclarationParameters = impl.genericDeclarationParameters
        val genericConstraints = impl.ownerClass.genericParameters

        // Check generic declaration parameters are same as target owner class'
        if (genericDeclarationParameters != null) {
            if (genericDeclarationParameters.parameters.size == genericConstraints.size) {
                val zippedConstraints = genericDeclarationParameters.parameters
                    .map(GenericDeclarationParameters.GenericDeclarationParameter::genericConstraint)
                    .zip(genericConstraints)

                for ((implGenericConstraint, classGenericConstraint) in zippedConstraints) {
                    if (implGenericConstraint != classGenericConstraint) {
                        // Unmatched generic declaration parameters
                        // Generic constraint bound does not match
                        reportUnmatchedGenericDeclarationParameters(
                            impl.identifier.span,
                            genericDeclarationParameters,
                            genericConstraints
                        )
                    }
                }
            } else {
                // Unmatched generic declaration parameters
                // Generic constraints size does not match
                reportUnmatchedGenericDeclarationParameters(
                    impl.identifier.span,
                    genericDeclarationParameters,
                    genericConstraints
                )
            }
        } else if (genericConstraints.isNotEmpty()) {
            // Unmatched generic declaration parameters
            // Generic constraints size does not match
            reportUnmatchedGenericDeclarationParameters(
                impl.identifier.span,
                genericDeclarationParameters,
                genericConstraints
            )
        }
    }

    private fun checkImplModifiers(impl: Impl) {
        val modifiers = impl.modifiers

        for ((modifier, span) in modifiers) {
            when (modifier) {
                Modifier.Inline -> reportIllegalInline(
                    span,
                    "Impl cannot be inlined"
                )

                Modifier.Mut -> reportIllegalMut(
                    span,
                    "Impl cannot be mutable"
                )

                Modifier.Inner -> reportIllegalInner(
                    span,
                    "Impl cannot be inner"
                )

                else -> {}
            }
        }
    }

    private fun checkImplItem(item: ImplItem) {
        when (item) {
            is Class -> checkClass(item)
            is Const -> checkConst(item)
            is Func -> checkFunction(item)
            is Impl -> checkImpl(item)
            is StaticField -> checkStaticField(item)
        }
    }

    private fun checkStatement(statement: Statement) {
        when (statement) {
            is VariableDeclaration -> checkVariableDeclaration(statement)
            is For -> checkFor(statement)
            is Block -> checkBlock(statement)
            is Return -> checkReturn(statement)
            is ExpressionStatement -> checkExpression(statement.expression)
        }
    }

    private fun checkVariableDeclaration(variableDeclaration: VariableDeclaration) {
        if (variableDeclaration.name.literal == "_") {
            when (variableDeclaration.expression) {
                is Expression.LiteralExpression -> {
                    // No effect variable declaration
                    reportIgnoredVariableHasNoEffect(variableDeclaration.name.span, variableDeclaration.expression.span)
                }

                Expression.Undefined -> TODO("UNREACHABLE")
                else -> {}
            }
        }
    }

    private fun checkFor(`for`: For) {
        checkExpression(`for`.conditionExpression)

        if (`for`.conditionExpression !is Expression.Empty && !`for`.conditionExpression.finalType.canImplicitCast(
                TypeInfo.Primitive.BOOL_TYPE_INFO
            )
        ) {
            reportUnableToImplicitlyCast(
                `for`.conditionExpression.span,
                `for`.conditionExpression.finalType.toString(),
                TypeInfo.Primitive.BOOL_TYPE_INFO.toString()
            )
        }

        checkBlock(`for`.block)
    }

    private fun checkBlock(block: Block) {
        for (statement in block.statements) {
            checkStatement(statement)
        }
    }

    private fun checkReturn(`return`: Return) {
        checkExpression(`return`.expression)

        if (!(`return`.expression.finalType.canImplicitCast(currentFunction!!.returnTypeInfo))) {
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
            is Expression.As -> checkAs(expression)
            is Expression.Parenthesized -> checkExpression(expression.expression)
            is Expression.BoolLiteral -> {}
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

    private fun checkAs(`as`: Expression.As) {
        when (TypeChecker.canExplicitCast(`as`.originalType, `as`.finalType)) {
            TypeChecker.BoundResult.FAIL, TypeChecker.BoundResult.IMPOSSIBLE -> {
                reportUnableToExplicitlyCast(
                    `as`.span,
                    `as`.expression.span,
                    `as`.originalType.toString(),
                    `as`.type.span,
                    `as`.finalType.toString()
                )
            }

            else -> {}
        }
    }

    private fun checkNumberLiteral(numberLiteral: Expression.NumberLiteral) {
        val originalType = numberLiteral.originalType
        val isIntegerValue = numberLiteral.value.toInt().toDouble() != numberLiteral.value

        if (numberLiteral.dot != null && numberLiteral.floatPart != null && numberLiteral.specifiedType != null) {
            if (isIntegerValue) {
                // Number literal represents a float number but specified with an integer type
                reportNumberLiteralIllegalRepresentation(
                    numberLiteral.specifiedType,
                    numberLiteral,
                    numberLiteral.floatPart
                )
            } else if (originalType is TypeInfo.Primitive) {
                if (numberLiteral.specifiedTypeInfo?.type == PrimitiveType.F64 && originalType.type == PrimitiveType.F64) {
                    // Redundant type suffix `f64` for float number literal
                    reportNumberLiteralRedundantTypeSuffix(
                        numberLiteral.specifiedType,
                        numberLiteral,
                        numberLiteral.floatPart
                    )
                } else if (numberLiteral.specifiedTypeInfo?.type == PrimitiveType.I32 && originalType.type == PrimitiveType.I32) {
                    // Redundant type suffix `i32` for integer number literal
                    reportNumberLiteralRedundantTypeSuffix(
                        numberLiteral.specifiedType,
                        numberLiteral,
                        numberLiteral.floatPart
                    )
                }
            }
        }
    }

    // REPORTS

    private fun reportUnmatchedGenericDeclarationParameters(
        implIdentifierSpan: Span,
        genericDeclarationParameters: GenericDeclarationParameters?,
        classGenericConstraints: List<TypeInfo.GenericConstraint>
    ) {
        val innerImplGenericConstraintsLiteral = genericDeclarationParameters?.parameters
            ?.map(GenericDeclarationParameters.GenericDeclarationParameter::genericConstraint)
            ?.joinToString(transform = TypeInfo.GenericConstraint::toString) ?: ""
        val implGenericConstraintsLiteral =
            colorize("[$innerImplGenericConstraintsLiteral]", compilationUnit, Attribute.RED_TEXT())
        val innerClassGenericConstraintsLiteral =
            classGenericConstraints.joinToString(transform = TypeInfo.GenericConstraint::toString)
        val classGenericConstraintsLiteral =
            colorize("[$innerClassGenericConstraintsLiteral]", compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(implIdentifierSpan, compilationUnit.maxLineCount),
                "Generic declaration parameters mismatched"
            )
            .label(implIdentifierSpan, "Expected: $classGenericConstraintsLiteral, Got: $implGenericConstraintsLiteral")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportImplicitTypeConversionOnConstant(const: Const) {
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

    private fun reportStaticFieldAsConstant(staticField: StaticField) {
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

    private fun reportMutableStaticFieldIsInlined(staticField: StaticField) {
        val inlineLiteral = colorize("inline", compilationUnit, Attribute.YELLOW_TEXT())
        val mutLiteral = colorize("mut", compilationUnit, Attribute.YELLOW_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(staticField.span, compilationUnit.maxLineCount),
                "Static field cannot be mutable and inline-able at the same time"
            )
            .label(staticField.modifiers[Modifier.Inline]!!, "`$inlineLiteral` modifier here")
            .color(Attribute.RED_TEXT())
            .build()
            .label(staticField.modifiers[Modifier.Mut]!!, "`$mutLiteral` modifier here")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportInheritsFromImmutableClass(superClassConstructorCall: Class.SuperClassConstructorCall) {
        val typeLiteral = colorize(
            superClassConstructorCall.superClassType.standardizeType(),
            compilationUnit,
            Attribute.CYAN_TEXT()
        )
        val mutKeyword = colorize("mut", compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(
                    superClassConstructorCall.superClassType.span,
                    compilationUnit.maxLineCount
                ),
                "Unable to inherit immutable class"
            )
            .label(
                superClassConstructorCall.superClassType.span,
                "Consider make class `$typeLiteral` mutable with keyword `$mutKeyword`"
            )
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }

    private fun reportNumberLiteralRedundantTypeSuffix(
        specifiedType: Type,
        numberLiteral: Expression.NumberLiteral,
        floatPart: Token
    ) {
        val specifiedTypeLiteral =
            colorize(specifiedType.standardizeType(), compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .warning(
                SpanHelper.expandView(numberLiteral.span, compilationUnit.maxLineCount),
                "Redundant type suffix `$specifiedTypeLiteral` for float number literal"
            )
            .label(floatPart.span, "Float number literal here")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(specifiedType.span, "Redundant type suffix here")
            .color(Attribute.YELLOW_TEXT())
            .build().build()
    }

    private fun reportNumberLiteralIllegalRepresentation(
        specifiedType: Type,
        numberLiteral: Expression.NumberLiteral,
        floatPart: Token
    ) {
        val specifiedTypeLiteral =
            colorize(specifiedType.standardizeType(), compilationUnit, Attribute.RED_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(numberLiteral.span, compilationUnit.maxLineCount),
                "Illegal type suffix `$specifiedTypeLiteral` for float number literal"
            )
            .label(floatPart.span, "Float number literal here")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(specifiedType.span, "Illegal type suffix here")
            .color(Attribute.RED_TEXT())
            .build().build()
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

    private fun reportIllegalInner(span: Span, labelMessage: String) {
        val innerLiteral = colorize("inner", compilationUnit, Attribute.RED_TEXT())

        compilationUnit.reportBuilder
            .error(SpanHelper.expandView(span, compilationUnit.maxLineCount), "Illegal modifier `$innerLiteral`")
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
        function: Func,
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

    private fun reportUnableToImplicitlyCast(
        fromTypeSpan: Span,
        fromTypeLiteral: String,
        toTypeLiteral: String
    ) {
        val fromType = colorize(fromTypeLiteral, compilationUnit, Attribute.RED_TEXT())
        val toType = colorize(toTypeLiteral, compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(fromTypeSpan, compilationUnit.maxLineCount),
                "Unable to implicitly cast `$fromType` into `$toType`"
            )
            .label(fromTypeSpan, "Expected `$toType`, Got `$fromType`")
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }

    private fun reportUnableToExplicitlyCast(
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
                "Unable to explicitly cast `$fromType` into `$toType`"
            )
            .label(toTypeSpan, "Cast into `$toType` is impossible")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(fromTypeSpan, "Got `$fromType`")
            .color(Attribute.RED_TEXT())
            .build().build()
    }
}
