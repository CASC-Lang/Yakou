package org.yakou.lang.bind

import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.ast.Class
import org.yakou.lang.ast.Const
import org.yakou.lang.ast.Expression
import org.yakou.lang.ast.Func
import org.yakou.lang.ast.GenericDeclarationParameters
import org.yakou.lang.ast.Path
import org.yakou.lang.ast.StaticField
import org.yakou.lang.ast.Token
import org.yakou.lang.compilation.UnitReporter

internal interface BinderReporter : UnitReporter {
    fun reportUnresolvedSymbol(name: String, span: Span) {
        val coloredName = colorize(name, Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(span),
                "Unresolved symbol"
            )
            .label(span, "Unable to find symbol `$coloredName` in current context")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportConstAlreadyDefined(field: ClassMember.Field, const: Const) {
        val span = const.span
        val coloredConstLiteral = colorize(field.constToString(), Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(span),
                "Constant $coloredConstLiteral is already defined at `${field.qualifiedOwnerPath}`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportStaticFieldAlreadyDefined(field: ClassMember.Field, staticField: StaticField) {
        val span = staticField.span
        val coloredStaticFieldLiteral = colorize(field.staticFieldToString(), Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(span),
                "Static field $coloredStaticFieldLiteral is already defined at `${field.qualifiedOwnerPath}`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportClassAlreadyDefined(currentPackagePath: Path, currentClassPath: Path, clazz: Class) {
        val span = clazz.span
        val coloredPackageLiteral = colorize(currentPackagePath.toString(), Attribute.CYAN_TEXT())
        val coloredClassLiteral = colorize(currentClassPath.toString(), Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(span),
                "Class $coloredClassLiteral is already defined at `$coloredPackageLiteral`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportFieldAlreadyDefined(fieldInstance: ClassMember.Field, span: Span) {
        val coloredStaticFieldLiteral =
            colorize(fieldInstance.staticFieldToString(), Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(span),
                "Static field $coloredStaticFieldLiteral is already defined at `${fieldInstance.qualifiedOwnerPath}`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportFunctionAlreadyDefined(fn: ClassMember.Fn, function: Func) {
        val span = function.fn.span.expand(function.span)
        val coloredFnLiteral = colorize(fn.toString(), Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(span),
                "Function $coloredFnLiteral is already defined at `${fn.qualifiedOwnerPath}`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportConstructorAlreadyDefined(constructor: ClassMember.Constructor, span: Span) {
        val coloredCtorLiteral = colorize(constructor.toString(), Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(span),
                "Constructor $coloredCtorLiteral is already defined at `${constructor.qualifiedOwnerPath}`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun checkVariableUnused(scope: Scope) {
        for (variable in scope.variables) {
            if (!variable.isUsed) {
                reportVariableUnused(variable)
            }
        }
    }

    fun reportVariableUnused(variable: Variable) {
        val variableType = if (variable is Variable.ValueParameter) "Value-parameter" else "Variable"
        val coloredVariableName = colorize(variable.name, Attribute.CYAN_TEXT())

        reporter()
            .warning(
                adjustSpan(variable.nameToken.span),
                "$variableType `$coloredVariableName` is never used"
            )
            .label(variable.nameToken.span, "Unused ${variableType.lowercase()} here")
            .color(Attribute.YELLOW_TEXT())
            .build().build()
    }

    fun reportVariableAlreadyDeclared(originalVariable: Variable, duplicatedSpan: Span) {
        val originalSpan = originalVariable.nameToken.span
        val coloredVariableName = colorize(originalVariable.name, Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(originalSpan.expand(duplicatedSpan)),
                "Variable `$coloredVariableName` is already declared"
            )
            .label(originalSpan, "Variable `$coloredVariableName` was declared here first")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(duplicatedSpan, "Variable redeclared here")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportUnresolvedType(typeName: String, span: Span) {
        val colorizedTypeName = colorize(typeName, Attribute.RED_TEXT())

        reporter()
            .error(adjustSpan(span), "Unknown type $colorizedTypeName")
            .label(span, "Unresolvable type here")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportUnresolvedIdentifier(identifier: Token) {
        val colorizedIdentifier = colorize(identifier.literal, Attribute.RED_TEXT())

        reporter()
            .error(adjustSpan(identifier.span), "Unknown identifier $colorizedIdentifier")
            .label(identifier.span, "Unresolved identifier here")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportNonIntegerShiftOperation(binaryExpression: Expression.BinaryExpression) {
        val coloredOperator = colorize(
            binaryExpression.operator.literal,
            Attribute.CYAN_TEXT()
        )
        val coloredLeftTypeLiteral = colorize(
            binaryExpression.leftExpression.finalType.toString(),
            Attribute.CYAN_TEXT()
        )

        reporter()
            .error(
                adjustSpan(binaryExpression.span),
                "Unable to shift type `$coloredLeftTypeLiteral` with `$coloredOperator`"
            )
            .label(
                binaryExpression.leftExpression.span,
                "Expression has non-integer type `$coloredLeftTypeLiteral`"
            )
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(
                binaryExpression.operator.span,
                "Inapplicable operator `$coloredOperator`"
            )
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportNonI32ShiftOperation(binaryExpression: Expression.BinaryExpression) {
        val coloredOperator = colorize(
            binaryExpression.operator.literal,
            Attribute.CYAN_TEXT()
        )
        val coloredRightTypeLiteral =
            colorize(binaryExpression.rightExpression.finalType.toString(), Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(binaryExpression.span),
                "Unable to shift type `$coloredRightTypeLiteral` with `$coloredOperator`"
            )
            .label(
                binaryExpression.rightExpression.span,
                "Expression has non-i32 type `$coloredRightTypeLiteral`"
            )
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(
                binaryExpression.operator.span,
                "Inapplicable operator `$coloredOperator`"
            )
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportInapplicableOperator(
        binaryExpression: Expression.BinaryExpression,
        leftType: TypeInfo,
        rightType: TypeInfo,
        expectedType: TypeInfo? = TypeInfo.Primitive.BOOL_TYPE_INFO
    ) {
        val coloredOperator = colorize(binaryExpression.operator.literal, Attribute.CYAN_TEXT())
        val coloredLeftTypeLiteral = colorize(leftType.toString(), Attribute.CYAN_TEXT())
        val coloredRightTypeLiteral = colorize(rightType.toString(), Attribute.CYAN_TEXT())
        val coloredBoolLiteral = colorize(expectedType.toString(), Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(binaryExpression.span),
                "Unable to apply `$coloredOperator` on `$coloredLeftTypeLiteral` and `$coloredRightTypeLiteral`"
            )
            .label(
                binaryExpression.leftExpression.span,
                "Left expression has type `$coloredLeftTypeLiteral`"
            )
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(binaryExpression.operator.span, "Inapplicable operator `$coloredOperator`")
            .color(Attribute.RED_TEXT())
            .apply {
                if (expectedType != null) {
                    hint("Only type `$coloredBoolLiteral` is applicable")
                }
            }
            .build()
            .label(
                binaryExpression.rightExpression.span,
                "Right expression has type `$coloredRightTypeLiteral`"
            )
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }

    fun reportUnableToSetGenericBound(genericDeclarationParameter: GenericDeclarationParameters.GenericDeclarationParameter) {
        reporter()
            .error(
                adjustSpan(genericDeclarationParameter.span),
                "Unable to set bound to non-class type or non-type-parameter"
            )
            .label(genericDeclarationParameter.span, "Bound type can only be class type or type parameter")
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }

    fun reportIllegalNonClassInheritance(superClassConstructorCall: Class.SuperClassConstructorCall) {
        val typeLiteral = colorize(superClassConstructorCall.superClassType.standardizeType(), Attribute.CYAN_TEXT())

        reporter()
            .error(adjustSpan(superClassConstructorCall.superClassType.span), "Unable to inherit from non-class type")
            .label(superClassConstructorCall.superClassType.span, "Type $typeLiteral is not a class type")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportIllegalGenericInheritance(superClassConstructorCall: Class.SuperClassConstructorCall) {
        reporter()
            .error(adjustSpan(superClassConstructorCall.superClassType.span), "Unable to inherit from generic type")
            .label(superClassConstructorCall.superClassType.span, "Generic type constraint cannot be inherited from")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportIllegalPackageInheritance(superClassConstructorCall: Class.SuperClassConstructorCall) {
        reporter()
            .error(adjustSpan(superClassConstructorCall.superClassType.span), "Unable to inherit from package")
            .label(superClassConstructorCall.superClassType.span, "Package is not inheritable")
            .color(Attribute.RED_TEXT())
            .build().build()
    }
}
