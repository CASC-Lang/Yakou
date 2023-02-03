package org.yakou.lang.checker

import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.ast.Class
import org.yakou.lang.ast.Const
import org.yakou.lang.ast.Expression
import org.yakou.lang.ast.Func
import org.yakou.lang.ast.GenericDeclarationParameters
import org.yakou.lang.ast.Modifier
import org.yakou.lang.ast.StaticField
import org.yakou.lang.ast.Token
import org.yakou.lang.ast.Type
import org.yakou.lang.bind.TypeInfo
import org.yakou.lang.compilation.UnitReporter

interface CheckerReporter : UnitReporter {
    fun reportUnmatchedGenericDeclarationParameters(
        implIdentifierSpan: Span,
        genericDeclarationParameters: GenericDeclarationParameters?,
        classGenericConstraints: List<TypeInfo.GenericConstraint>
    ) {
        val innerImplGenericConstraintsLiteral = genericDeclarationParameters?.parameters
            ?.map(GenericDeclarationParameters.GenericDeclarationParameter::genericConstraint)
            ?.joinToString(transform = TypeInfo.GenericConstraint::toString) ?: ""
        val implGenericConstraintsLiteral =
            colorize("[$innerImplGenericConstraintsLiteral]", Attribute.RED_TEXT())
        val innerClassGenericConstraintsLiteral =
            classGenericConstraints.joinToString(transform = TypeInfo.GenericConstraint::toString)
        val classGenericConstraintsLiteral =
            colorize("[$innerClassGenericConstraintsLiteral]", Attribute.CYAN_TEXT())

        reporter()
            .error(adjustSpan(implIdentifierSpan), "Generic declaration parameters mismatched")
            .label(implIdentifierSpan, "Expected: $classGenericConstraintsLiteral, Got: $implGenericConstraintsLiteral")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportImplicitTypeConversionOnConstant(const: Const) {
        val explicitTypeLiteral = colorize(const.typeInfo.toString(), Attribute.CYAN_TEXT())
        val expressionTypeLiteral = colorize(const.expression.finalType.toString(), Attribute.RED_TEXT())

        reporter()
            .error(adjustSpan(const.span), "Cannot implicitly cast constant's expression")
            .label(const.explicitType.span, "Expression type should explicitly be `$explicitTypeLiteral`")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(const.expression.span, "Expression has type `$expressionTypeLiteral`")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportStaticFieldAsConstant(staticField: StaticField) {
        val staticLiteral = colorize("static", Attribute.CYAN_TEXT())
        val constLiteral = colorize("const", Attribute.CYAN_TEXT())
        val expressionTypeLiteral = colorize(staticField.expression.finalType.toString(), Attribute.GREEN_TEXT())

        reporter()
            .warning(adjustSpan(staticField.span), "Static field is sufficient to convert into constant")
            .label(staticField.static.span, "Consider replace `$staticLiteral` with `$constLiteral`")
            .color(Attribute.YELLOW_TEXT())
            .build()
            .label(staticField.expression.span, "Expression has primitive type `$expressionTypeLiteral`")
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }

    fun reportMutableStaticFieldIsInlined(staticField: StaticField) {
        val inlineLiteral = colorize("inline", Attribute.YELLOW_TEXT())
        val mutLiteral = colorize("mut", Attribute.YELLOW_TEXT())

        reporter()
            .error(
                adjustSpan(staticField.span),
                "Static field cannot be mutable and inline-able at the same time"
            )
            .label(staticField.modifiers[Modifier.Inline]!!, "`$inlineLiteral` modifier here")
            .color(Attribute.RED_TEXT())
            .build()
            .label(staticField.modifiers[Modifier.Mut]!!, "`$mutLiteral` modifier here")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportInheritsFromImmutableClass(superClassConstructorCall: Class.SuperClassConstructorCall) {
        val typeLiteral = colorize(superClassConstructorCall.superClassType.standardizeType(), Attribute.CYAN_TEXT())
        val mutKeyword = colorize("mut", Attribute.CYAN_TEXT())

        reporter()
            .error(adjustSpan(superClassConstructorCall.superClassType.span), "Unable to inherit immutable class")
            .label(
                superClassConstructorCall.superClassType.span,
                "Consider make class `$typeLiteral` mutable with keyword `$mutKeyword`"
            )
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }

    fun reportNumberLiteralRedundantTypeSuffix(
        specifiedType: Type,
        numberLiteral: Expression.NumberLiteral,
        floatPart: Token
    ) {
        val specifiedTypeLiteral = colorize(specifiedType.standardizeType(), Attribute.CYAN_TEXT())

        reporter()
            .warning(
                adjustSpan(numberLiteral.span),
                "Redundant type suffix `$specifiedTypeLiteral` for float number literal"
            )
            .label(floatPart.span, "Float number literal here")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(specifiedType.span, "Redundant type suffix here")
            .color(Attribute.YELLOW_TEXT())
            .build().build()
    }

    fun reportNumberLiteralIllegalRepresentation(
        specifiedType: Type,
        numberLiteral: Expression.NumberLiteral,
        floatPart: Token
    ) {
        val specifiedTypeLiteral = colorize(specifiedType.standardizeType(), Attribute.RED_TEXT())

        reporter()
            .error(
                adjustSpan(numberLiteral.span),
                "Illegal type suffix `$specifiedTypeLiteral` for float number literal"
            )
            .label(floatPart.span, "Float number literal here")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(specifiedType.span, "Illegal type suffix here")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportIgnoredVariableHasNoEffect(variableSpan: Span, expressionSpan: Span) {
        reporter()
            .warning(
                adjustSpan(variableSpan.expand(expressionSpan)),
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

    fun reportIllegalInline(span: Span, labelMessage: String) {
        val inlineLiteral = colorize("inline", Attribute.RED_TEXT())

        reporter()
            .error(adjustSpan(span), "Illegal modifier `$inlineLiteral`")
            .label(span, labelMessage)
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportIllegalMut(span: Span, labelMessage: String) {
        val mutLiteral = colorize("mut", Attribute.RED_TEXT())

        reporter()
            .error(adjustSpan(span), "Illegal modifier `$mutLiteral`")
            .label(span, labelMessage)
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportIllegalInner(span: Span, labelMessage: String) {
        val innerLiteral = colorize("inner", Attribute.RED_TEXT())

        reporter()
            .error(adjustSpan(span), "Illegal modifier `$innerLiteral`")
            .label(span, labelMessage)
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportIllegalSelf(span: Span, labelMessage: String) {
        val selfLiteral = colorize("self", Attribute.RED_TEXT())

        reporter()
            .error(adjustSpan(span), "Illegal value-parameter `$selfLiteral`")
            .label(span, labelMessage)
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportMissingReturn(
        function: Func,
        closeBrace: Token,
        returnTypeInfo: TypeInfo
    ) {
        val functionString = colorize(function.functionInstance.toString(), Attribute.CYAN_TEXT())
        val returnLiteral = colorize("return", Attribute.CYAN_TEXT())
        val returnType = colorize(returnTypeInfo.toString(), Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(function.span),
                "Missing `$returnLiteral` statement at the end of function `$functionString`"
            )
            .label(function.returnType!!.span, "Function needs to return `$returnType`")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(closeBrace.span, "Missing `$returnLiteral` statement at the end of function")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportUnableToImplicitlyCast(
        commonSpan: Span,
        fromTypeSpan: Span,
        fromTypeLiteral: String,
        toTypeSpan: Span,
        toTypeLiteral: String
    ) {
        val fromType = colorize(fromTypeLiteral, Attribute.RED_TEXT())
        val toType = colorize(toTypeLiteral, Attribute.CYAN_TEXT())

        reporter()
            .error(adjustSpan(commonSpan), "Unable to implicitly cast `$fromType` into `$toType`")
            .label(toTypeSpan, "Expected `$toType`")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(fromTypeSpan, "Got `$fromType`")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportUnableToImplicitlyCast(
        fromTypeSpan: Span,
        fromTypeLiteral: String,
        toTypeLiteral: String
    ) {
        val fromType = colorize(fromTypeLiteral, Attribute.RED_TEXT())
        val toType = colorize(toTypeLiteral, Attribute.CYAN_TEXT())

        reporter()
            .error(adjustSpan(fromTypeSpan), "Unable to implicitly cast `$fromType` into `$toType`")
            .label(fromTypeSpan, "Expected `$toType`, Got `$fromType`")
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }

    fun reportUnableToExplicitlyCast(
        commonSpan: Span,
        fromTypeSpan: Span,
        fromTypeLiteral: String,
        toTypeSpan: Span,
        toTypeLiteral: String
    ) {
        val fromType = colorize(fromTypeLiteral, Attribute.RED_TEXT())
        val toType = colorize(toTypeLiteral, Attribute.CYAN_TEXT())

        reporter()
            .error(adjustSpan(commonSpan), "Unable to explicitly cast `$fromType` into `$toType`")
            .label(toTypeSpan, "Cast into `$toType` is impossible")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(fromTypeSpan, "Got `$fromType`")
            .color(Attribute.RED_TEXT())
            .build().build()
    }
}