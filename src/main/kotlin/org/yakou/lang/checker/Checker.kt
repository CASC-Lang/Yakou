package org.yakou.lang.checker

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
import org.yakou.lang.ast.VariableDeclaration
import org.yakou.lang.ast.YkFile
import org.yakou.lang.bind.PrimitiveType
import org.yakou.lang.bind.Table
import org.yakou.lang.bind.TypeChecker
import org.yakou.lang.bind.TypeInfo
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.compilation.UnitReporter

class Checker(private val compilationUnit: CompilationUnit) : CheckerReporter, UnitReporter by compilationUnit {
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
                val coloredSelf = colorize("self", Attribute.CYAN_TEXT())

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
            is Expression.New -> checkNew(expression)
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
    
    private fun checkNew(new: Expression.New) {
        TODO()
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
}
