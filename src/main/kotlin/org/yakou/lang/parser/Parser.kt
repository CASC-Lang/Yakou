package org.yakou.lang.parser

import org.yakou.lang.ast.Argument
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
import org.yakou.lang.ast.GenericParameter
import org.yakou.lang.ast.GenericParameters
import org.yakou.lang.ast.Impl
import org.yakou.lang.ast.ImplItem
import org.yakou.lang.ast.Item
import org.yakou.lang.ast.Keyword
import org.yakou.lang.ast.Modifier
import org.yakou.lang.ast.Modifiers
import org.yakou.lang.ast.Package
import org.yakou.lang.ast.Parameter
import org.yakou.lang.ast.Path
import org.yakou.lang.ast.PrimaryConstructor
import org.yakou.lang.ast.Return
import org.yakou.lang.ast.Statement
import org.yakou.lang.ast.StaticField
import org.yakou.lang.ast.Token
import org.yakou.lang.ast.TokenType
import org.yakou.lang.ast.Type
import org.yakou.lang.ast.VariableDeclaration
import org.yakou.lang.ast.YkFile
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.compilation.UnitReporter

class Parser(private val compilationUnit: CompilationUnit) :
    ParserReporter,
    UnitReporter by compilationUnit {
    private val tokens: List<Token> = compilationUnit.tokens ?: listOf()
    private var pos: Int = 0
    private var lastRecoverSteps: Int = 0

    private var optionalExpression: Boolean = false

    fun parse(): YkFile {
        val items = parseItems()

        return YkFile(items)
    }

    private fun parseItems(): List<Item> {
        val items = mutableListOf<Item>()

        while (pos < tokens.size) {
            parseItem()?.let(items::add)
        }

        return items
    }

    private fun parseScopedItems(): List<Item> {
        val items = mutableListOf<Item>()

        while (pos < tokens.size && !optExpectType(TokenType.CloseBrace)) {
            parseItem()?.let(items::add)
        }

        return items
    }

    private fun parseItem(): Item? {
        val modifiers = parseModifiers()

        return when {
            optExpectKeyword(Keyword.PKG) -> parsePackage(modifiers)
            optExpectKeyword(Keyword.CONST) -> parseConst(modifiers)
            optExpectKeyword(Keyword.STATIC) -> parseStaticField(modifiers)
            optExpectKeyword(Keyword.CLASS) -> parseClass(modifiers)
            optExpectKeyword(Keyword.IMPL) -> parseImpl(modifiers)
            optExpectKeyword(Keyword.FN) -> parseFunction(modifiers)
            else -> {
                reportUnexpectedToken(
                    next()!!,
                    Keyword.PKG,
                    Keyword.CONST,
                    Keyword.STATIC,
                    Keyword.CLASS,
                    Keyword.IMPL,
                    Keyword.FN
                )

                null
            }
        }
    }

    private fun parsePackage(modifiers: Modifiers): Package {
        val pkg = next()!!
        val identifier = expect(TokenType.Identifier)
        val openBrace: Token?
        val innerItems: List<Item>?
        val closeBrace: Token?

        if (optExpectType(TokenType.OpenBrace)) {
            openBrace = next()
            innerItems = parseScopedItems()
            closeBrace = expect(TokenType.CloseBrace)
        } else {
            openBrace = null
            innerItems = null
            closeBrace = null
        }

        if (!modifiers.isEmpty()) {
            reportIllegalModifiersInPlace(modifiers)
        }

        return Package(pkg, identifier, openBrace, innerItems, closeBrace)
    }

    private fun parseConst(modifiers: Modifiers): Const {
        val const = next()!! // Should be asserted when called
        val identifier = expect(TokenType.Identifier)
        val colon = expect(TokenType.Colon)
        val type = parseType()

        val equal = expect(TokenType.Equal)
        val expression = parseExpression()

        return Const(
            modifiers,
            const,
            identifier,
            colon,
            type,
            equal,
            expression
        )
    }

    private fun parseStaticField(modifiers: Modifiers): StaticField {
        val static = next()!! // Should be asserted when called
        val identifier = expect(TokenType.Identifier)
        val colon = expect(TokenType.Colon)
        val type = parseType()
        val equal = expect(TokenType.Equal)
        val expression = parseExpression()

        return StaticField(
            modifiers,
            static,
            identifier,
            colon,
            type,
            equal,
            expression
        )
    }

    private fun parseClass(modifiers: Modifiers): Class {
        val `class` = next()!! // Should be asserted when called
        val name = expect(TokenType.Identifier)
        val genericParameters =
            if (optExpectType(TokenType.OpenBracket)) {
                parseGenericDeclarationParameters()
            } else {
                null
            }
        val primaryConstructorModifiers = parseModifiers()
        val primaryConstructor =
            if (optExpectType(TokenType.OpenParenthesis)) {
                parsePrimaryConstructor(primaryConstructorModifiers)
            } else {
                // Recover modifiers
                recover()

                null
            }
        val colon: Token?
        val superClassConstructorCall: Class.SuperClassConstructorCall?

        if (optExpectType(TokenType.Colon)) {
            colon = next()!!
            superClassConstructorCall = parseSuperClassConstructorCall()
        } else {
            colon = null
            superClassConstructorCall = null
        }

        val openBrace: Token?
        val classItems: List<ClassItem>?
        val closeBrace: Token?

        if (optExpectType(TokenType.OpenBrace)) {
            openBrace = next()!!
            classItems = parseClassItems()
            closeBrace = expect(TokenType.CloseBrace)
        } else {
            openBrace = null
            classItems = null
            closeBrace = null
        }

        return Class(
            modifiers,
            `class`,
            name,
            genericParameters,
            primaryConstructor,
            colon,
            superClassConstructorCall,
            openBrace,
            classItems,
            closeBrace
        )
    }

    private fun parseImpl(modifiers: Modifiers): Impl {
        val impl = next()!!
        val genericDeclarationParameters =
            if (optExpectType(TokenType.OpenBracket)) {
                parseGenericDeclarationParameters()
            } else {
                null
            }
        val identifier = expect(TokenType.Identifier)
        val genericParameters =
            if (optExpectType(TokenType.OpenBracket)) {
                parseGenericParameters()
            } else {
                null
            }
        val openBrace: Token?
        val implItems: List<ImplItem>?
        val closeBrace: Token?

        if (optExpectType(TokenType.OpenBrace)) {
            openBrace = next()!!
            implItems = parseImplItems()
            closeBrace = expect(TokenType.CloseBrace)
        } else {
            openBrace = null
            implItems = null
            closeBrace = null
        }

        return Impl(
            modifiers,
            impl,
            genericDeclarationParameters,
            identifier,
            genericParameters,
            openBrace,
            implItems,
            closeBrace
        )
    }

    private fun parseImplItems(): List<ImplItem> {
        val implItems = mutableListOf<ImplItem>()

        while (pos < tokens.size && !optExpectType(TokenType.CloseBrace)) {
            parseImplItem()?.let(implItems::add)
        }

        return implItems
    }

    private fun parseImplItem(): ImplItem? {
        val modifiers = parseModifiers()

        return when {
            optExpectKeyword(Keyword.CONST) -> parseConst(modifiers)
            optExpectKeyword(Keyword.STATIC) -> parseStaticField(modifiers)
            optExpectKeyword(Keyword.CLASS) -> parseClass(modifiers)
            optExpectKeyword(Keyword.IMPL) -> parseImpl(modifiers)
            optExpectKeyword(Keyword.FN) -> parseFunction(modifiers)
            else -> {
                reportUnexpectedToken(next()!!, Keyword.CONST, Keyword.STATIC, Keyword.CLASS, Keyword.IMPL, Keyword.FN)

                null
            }
        }
    }

    private fun parsePrimaryConstructor(modifiers: Modifiers): PrimaryConstructor {
        val openParenthesis = next()!!
        val self: Token?
        val selfComma: Token?

        if (optExpectKeyword(Keyword.SELF_VALUE)) {
            self = next()!!
            selfComma = expect(TokenType.Comma)
        } else {
            self = null
            selfComma = null
        }

        val parameters = parseConstructorParameters()
        val closeParenthesis = expect(TokenType.CloseParenthesis)

        return PrimaryConstructor(modifiers, openParenthesis, self, selfComma, parameters, closeParenthesis)
    }

    private fun parseConstructorParameters(): List<PrimaryConstructor.ConstructorParameter> {
        val parameters = mutableListOf<PrimaryConstructor.ConstructorParameter>()

        while (pos < tokens.size && (optExpectType(TokenType.Keyword) || optExpectType(TokenType.Identifier))) {
            val modifiers = parseModifiers(mute = true)
            val parameter = parseParameter()

            parameters += PrimaryConstructor.ConstructorParameter(
                modifiers,
                parameter.name,
                parameter.colon,
                parameter.type
            )

            if (optExpectType(TokenType.Comma)) {
                consume()
            } else {
                break
            }
        }

        return parameters
    }

    private fun parseSuperClassConstructorCall(): Class.SuperClassConstructorCall {
        val superClass = parseType()
        val openParenthesis = expect(TokenType.OpenParenthesis)
        val arguments = parseArguments()
        val closeParenthesis = expect(TokenType.CloseParenthesis)

        return Class.SuperClassConstructorCall(superClass, openParenthesis, arguments, closeParenthesis)
    }

    private fun parseClassItems(): List<ClassItem> {
        val classItems = mutableListOf<ClassItem>()

        while (pos < tokens.size && !optExpectType(TokenType.CloseBrace))
            parseClassItem()?.let(classItems::add)

        return classItems
    }

    private fun parseClassItem(): ClassItem? {
        val modifiers = parseModifiers()

        return when {
            optExpectType(TokenType.Identifier) -> parseField(modifiers)
            else -> {
                reportUnexpectedToken(next()!!, TokenType.Identifier)

                null
            }
        }
    }

    private fun parseField(modifiers: Modifiers): Field {
        val name = expect(TokenType.Identifier)
        val colon = expect(TokenType.Colon)
        val explicitType = parseType()

        val equal: Token?
        val expression: Expression?

        if (optExpectType(TokenType.Equal)) {
            equal = next()!!
            expression = parseExpression()
        } else {
            equal = null
            expression = null
        }

        return Field(modifiers, name, colon, explicitType, equal, expression)
    }

    private fun parseFunction(modifiers: Modifiers): Func {
        val fn = next()!! // Should be asserted when called
        val name = expect(TokenType.Identifier)
        val genericDeclarationParameters =
            if (optExpectType(TokenType.OpenBracket)) {
                parseGenericDeclarationParameters()
            } else {
                null
            }
        val openParenthesis = expect(TokenType.OpenParenthesis)
        val self: Token?
        val selfComma: Token?

        if (optExpectKeyword(Keyword.SELF_VALUE)) {
            self = next()!!
            selfComma = expect(TokenType.Comma)
        } else {
            self = null
            selfComma = null
        }

        val parameters = parseParameters()
        val closeParenthesis = expect(TokenType.CloseParenthesis)
        val arrow: Token?
        val returnType: Type?

        if (optExpectType(TokenType.Arrow)) {
            // Has return type
            arrow = next()!!
            returnType = parseType()
        } else {
            arrow = null
            returnType = null
        }

        val functionBody = parseFunctionBody()

        return Func(
            modifiers,
            fn,
            name,
            genericDeclarationParameters,
            openParenthesis,
            self,
            selfComma,
            parameters,
            closeParenthesis,
            arrow,
            returnType,
            functionBody
        )
    }

    private fun parseFunctionBody(): FunctionBody? = when {
        optExpectType(TokenType.Equal) -> {
            // Single expression
            val equal = next()!!
            val expression = parseExpression()

            FunctionBody.SingleExpression(equal, expression)
        }

        optExpectType(TokenType.OpenBrace) -> {
            // Block expression
            val openBrace = next()!!
            val statements = parseStatements()
            val closeBrace = expect(TokenType.CloseBrace)

            FunctionBody.BlockExpression(openBrace, statements, closeBrace)
        }

        else -> null // Empty function body
    }

    private fun parseStatements(): List<Statement> {
        val statements = mutableListOf<Statement>()

        while (pos < tokens.size && !optExpectType(TokenType.CloseBrace))
            statements += parseStatement()

        return statements
    }

    private fun parseStatement(): Statement = when {
        optExpectKeyword(Keyword.LET) -> parseVariableDeclaration()
        optExpectKeyword(Keyword.FOR) -> parseFor()
        optExpectType(TokenType.OpenBrace) -> parseBlock()
        optExpectKeyword(Keyword.RETURN) -> parseReturn()
        else -> {
            val expression = parseExpression()

            ExpressionStatement(expression)
        }
    }

    private fun parseVariableDeclaration(): VariableDeclaration {
        val let = next()!!
        val mut =
            if (optExpectKeyword(Keyword.MUT)) {
                next()!!
            } else {
                null
            }
        val name = expect(TokenType.Identifier)
        val colon: Token?
        val specifiedType: Path.SimplePath?

        if (optExpectType(TokenType.Colon)) {
            colon = next()!!
            specifiedType = parseSimplePath()
        } else {
            colon = null
            specifiedType = null
        }

        val equal = expect(TokenType.Equal)
        val expression = parseExpression()

        return VariableDeclaration(let, mut, name, colon, specifiedType, equal, expression)
    }

    private fun parseFor(): For {
        val `for` = next()!!
        val expression = parseExpression(true)
        val block = parseBlock()

        return For(`for`, expression, block)
    }

    private fun parseBlock(): Block {
        val openBrace = expect(TokenType.OpenBrace)
        val statements = parseStatements()
        val closeBrace = expect(TokenType.CloseBrace)

        return Block(openBrace, statements, closeBrace)
    }

    private fun parseReturn(): Return {
        val `return` = next()!!
        val expression = parseExpression(true)

        return Return(`return`, expression)
    }

    private fun parseExpression(optional: Boolean = false): Expression {
        optionalExpression = optional
        val expression = parseDisjunctionExpression()
        optionalExpression = true

        return expression
    }

    private fun parseDisjunctionExpression(): Expression {
        var leftExpression = parseConjunctionExpression()

        while (optExpectType(TokenType.DoublePipe)) {
            leftExpression = parseRhsOp(
                leftExpression,
                ::parseConjunctionExpression,
                Expression.BinaryExpression.BinaryOperation.LogicalOr
            )
        }

        return leftExpression
    }

    private fun parseConjunctionExpression(): Expression {
        var leftExpression = parseEqualityExpression()

        while (optExpectType(TokenType.DoubleAmpersand)) {
            leftExpression = parseRhsOp(
                leftExpression,
                ::parseEqualityExpression,
                Expression.BinaryExpression.BinaryOperation.LogicalAnd
            )
        }

        return leftExpression
    }

    private fun parseEqualityExpression(): Expression {
        var leftExpression = parseComparisonExpression()

        while (true) {
            leftExpression = when {
                optExpectType(TokenType.DoubleEqual) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseComparisonExpression,
                        Expression.BinaryExpression.BinaryOperation.Equal
                    )
                }

                optExpectType(TokenType.BangEqual) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseComparisonExpression,
                        Expression.BinaryExpression.BinaryOperation.NotEqual
                    )
                }

                optExpectType(TokenType.TripleEqual) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseComparisonExpression,
                        Expression.BinaryExpression.BinaryOperation.ExactEqual
                    )
                }

                optExpectType(TokenType.BangDoubleEqual) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseComparisonExpression,
                        Expression.BinaryExpression.BinaryOperation.ExactNotEqual
                    )
                }

                else -> break
            }
        }

        return leftExpression
    }

    private fun parseComparisonExpression(): Expression {
        var leftExpression = parseAddictiveExpression()

        while (true) {
            leftExpression = when {
                optExpectType(TokenType.Greater) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseAddictiveExpression,
                        Expression.BinaryExpression.BinaryOperation.Greater
                    )
                }

                optExpectType(TokenType.GreaterEqual) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseAddictiveExpression,
                        Expression.BinaryExpression.BinaryOperation.GreaterEqual
                    )
                }

                optExpectType(TokenType.Lesser) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseAddictiveExpression,
                        Expression.BinaryExpression.BinaryOperation.Lesser
                    )
                }

                optExpectType(TokenType.LesserEqual) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseAddictiveExpression,
                        Expression.BinaryExpression.BinaryOperation.LesserEqual
                    )
                }

                else -> break
            }
        }

        return leftExpression
    }

    private fun parseAddictiveExpression(): Expression {
        var leftExpression = parseMultiplicativeExpression()

        while (true) {
            leftExpression = when {
                optExpectType(TokenType.Plus) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseMultiplicativeExpression,
                        Expression.BinaryExpression.BinaryOperation.Addition
                    )
                }

                optExpectType(TokenType.Minus) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseMultiplicativeExpression,
                        Expression.BinaryExpression.BinaryOperation.Subtraction
                    )
                }

                else -> break
            }
        }

        return leftExpression
    }

    private fun parseMultiplicativeExpression(): Expression {
        var leftExpression = parseAsExpression()

        while (true) {
            leftExpression = when {
                optExpectType(TokenType.Star) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseAsExpression,
                        Expression.BinaryExpression.BinaryOperation.Multiplication
                    )
                }

                optExpectType(TokenType.Slash) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseAsExpression,
                        Expression.BinaryExpression.BinaryOperation.Division
                    )
                }

                optExpectType(TokenType.Percentage) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parseAsExpression,
                        Expression.BinaryExpression.BinaryOperation.Modulo
                    )
                }

                else -> break
            }
        }

        return leftExpression
    }

    private fun parseAsExpression(): Expression {
        var leftExpression = parseShiftingExpression()

        while (optExpectKeyword(Keyword.AS)) {
            val `as` = next()!!
            val type = parseType()

            leftExpression = Expression.As(leftExpression, `as`, type)
        }

        return leftExpression
    }

    private fun parseShiftingExpression(): Expression {
        var leftExpression = parsePrimaryExpression()

        while (true) {
            leftExpression = when {
                optExpectType(TokenType.Lesser) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parsePrimaryExpression,
                        Expression.BinaryExpression.BinaryOperation.LeftShift
                    )
                }

                optExpectType(TokenType.DoubleGreater) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parsePrimaryExpression,
                        Expression.BinaryExpression.BinaryOperation.RightShift
                    )
                }

                optExpectType(TokenType.TripleGreater) -> {
                    parseRhsOp(
                        leftExpression,
                        ::parsePrimaryExpression,
                        Expression.BinaryExpression.BinaryOperation.UnsignedRightShift
                    )
                }

                else -> {
                    break
                }
            }
        }

        return leftExpression
    }

    private fun parsePrimaryExpression(): Expression = when {
        optExpectType(TokenType.OpenParenthesis) -> {
            val leftParenthesis = next()!!
            val expression = parseAsExpression()
            val rightParenthesis = expect(TokenType.CloseParenthesis)

            Expression.Parenthesized(
                leftParenthesis,
                expression,
                rightParenthesis
            )
        }

        optExpectKeyword(Keyword.NEW) -> {
            val new = next()!!
            val classType = parseType()
            val leftParenthesis = next()!!
            val arguments = parseArguments()
            val rightParenthesis = expect(TokenType.CloseParenthesis)

            Expression.ConstructorCall(
                new,
                classType,
                leftParenthesis,
                arguments,
                rightParenthesis
            )
        }

        else -> parseLiteralExpression()
    }

    private fun parseLiteralExpression(): Expression = when {
        optExpectType(TokenType.Identifier) -> Expression.Identifier(next()!!)
        optExpectKeyword(Keyword.TRUE) || optExpectKeyword(Keyword.FALSE) -> {
            val boolLiteral = next()!!

            Expression.BoolLiteral(boolLiteral, boolLiteral.span)
        }

        optExpectType(TokenType.NumberLiteral) -> {
            val numberToken = next()!! as Token.NumberLiteralToken
            val integer =
                numberToken.integerLiteral?.let { Token(it, TokenType.Synthetic, numberToken.integerLiteralSpan!!) }
            val float = numberToken.floatLiteral?.let { Token(it, TokenType.Synthetic, numberToken.floatLiteralSpan!!) }
            val type = numberToken.typeAnnotation?.let {
                Type.TypePath(
                    Path.TokenPath(
                        Token(
                            it,
                            TokenType.Identifier,
                            numberToken.typeAnnotationSpan!!
                        )
                    ),
                    null
                )
            }

            Expression.NumberLiteral(integer, numberToken.dot, float, type, numberToken.span)
        }

        else -> {
            if (optionalExpression) {
                Expression.Empty(peek(-1)!!.span)
            } else {
                reportUnexpectedToken(next()!!)

                Expression.Undefined
            }
        }
    }

    private fun parseRhsOp(
        lhs: Expression,
        functor: () -> Expression,
        operationType: Expression.BinaryExpression.BinaryOperation
    ): Expression.BinaryExpression {
        val operator = next()!!
        val rightExpression = functor()

        return Expression.BinaryExpression(
            lhs,
            operator,
            rightExpression,
            operationType
        )
    }

    private fun parseGenericDeclarationParameters(): GenericDeclarationParameters {
        val openBracket = next()!!
        val parameters = mutableListOf<GenericDeclarationParameters.GenericDeclarationParameter>()

        while (pos < tokens.size &&
            (
                optExpectType(TokenType.Identifier) ||
                    optExpectType(TokenType.Plus) ||
                    optExpectType(TokenType.Minus)
                )
        ) {
            parameters += parseGenericDeclarationParameter()

            if (optExpectType(TokenType.Comma)) {
                consume()
            } else {
                break
            }
        }

        val closeBracket = expect(TokenType.CloseBracket)

        return GenericDeclarationParameters(openBracket, parameters, closeBracket)
    }

    private fun parseGenericDeclarationParameter(): GenericDeclarationParameters.GenericDeclarationParameter = when {
        optExpectType(TokenType.Identifier) -> parseConstraintGenericParameter()
        optExpectType(TokenType.Plus) || optExpectType(TokenType.Minus) -> parseVarianceGenericParameter()
        else -> TODO("UNREACHABLE")
    }

    private fun parseGenericParameters(): GenericParameters {
        val openBracket = next()!!
        val parameters = mutableListOf<GenericParameter>()

        while (pos < tokens.size && (optExpectType(TokenType.Identifier) || optExpectType(TokenType.OpenBracket))) {
            parameters += parseType()

            if (optExpectType(TokenType.Comma)) {
                consume()
            } else {
                break
            }
        }

        val closeBracket = expect(TokenType.CloseBracket)

        return GenericParameters(openBracket, parameters, closeBracket)
    }

    private fun parseConstraintGenericParameter(): GenericDeclarationParameters.ConstraintGenericDeclarationParameter {
        val identifier = next()!!
        val boundIndicator: Token?
        val constraints: List<Type>

        if (optExpectType(TokenType.GreaterColon) || optExpectType(TokenType.LesserColon)) {
            boundIndicator = next()!!
            constraints = parseConstraintParameters()
        } else {
            boundIndicator = null
            constraints = listOf()
        }

        return GenericDeclarationParameters.ConstraintGenericDeclarationParameter(
            identifier,
            boundIndicator,
            constraints
        )
    }

    private fun parseConstraintParameters(): List<Type> {
        val constraints = mutableListOf<Type>()

        // Identifier for primitive type or class type path
        // OpenBracket for array type
        // though in generic, primitive type and array type are not able to be used as upper or lower bound type
        // we later check this in binder
        while (pos < tokens.size && (optExpectType(TokenType.Identifier) || optExpectType(TokenType.OpenBracket))) {
            constraints += parseType()

            if (optExpectType(TokenType.Plus)) {
                consume()
            } else {
                break
            }
        }

        return constraints
    }

    private fun parseVarianceGenericParameter(): GenericDeclarationParameters.VarianceGenericDeclarationParameter {
        val varianceIndicator = next()!!
        val identifier = expect(TokenType.Identifier)

        return GenericDeclarationParameters.VarianceGenericDeclarationParameter(varianceIndicator, identifier)
    }

    private fun parseParameters(): List<Parameter> {
        val parameters = mutableListOf<Parameter>()

        while (pos < tokens.size && optExpectType(TokenType.Identifier)) {
            parameters += parseParameter()

            if (optExpectType(TokenType.Comma)) {
                consume()
            } else {
                break
            }
        }

        return parameters
    }

    private fun parseParameter(): Parameter {
        val name = expect(TokenType.Identifier)
        val colon = expect(TokenType.Colon)
        val type = parseType()

        return Parameter(name, colon, type)
    }

    private fun parseArguments(): List<Argument> {
        val arguments = mutableListOf<Argument>()

        while (pos < tokens.size && !optExpectType(TokenType.CloseParenthesis)) {
            arguments += parseArgument()

            if (optExpectType(TokenType.Comma)) {
                consume()
            } else {
                break
            }
        }

        return arguments
    }

    private fun parseArgument(): Argument =
        parseExpression()

    private fun parseType(): Type = when {
        optExpectType(TokenType.OpenBracket) -> {
            // Array type
            val openBracket = next()!!
            val type = parseType()
            val closeBracket = expect(TokenType.CloseBracket)

            Type.Array(openBracket, type, closeBracket)
        }

        optExpectKeyword(Keyword.SELF_TYPE) -> {
            // Self type
            val self = next()!!

            Type.TypePath(Path.SimplePath(self), null)
        }

        else -> {
            // Path type
            val simplePath = parseSimplePath()
            val genericParameters =
                if (optExpectType(TokenType.OpenBracket)) {
                    parseGenericParameters()
                } else {
                    null
                }

            Type.TypePath(simplePath, genericParameters)
        }
    }

    private fun parseModifiers(mute: Boolean = false): Modifiers {
        var recoverStep = 0
        val modifiers = Modifiers()

        while (pos < tokens.size) {
            when {
                optExpectKeyword(Keyword.PUB) -> {
                    if (optExpectType(TokenType.OpenParenthesis, 1) &&
                        optExpectKeyword(Keyword.PKG, 2) &&
                        optExpectType(TokenType.CloseParenthesis, 3)
                    ) {
                        // `pub (pkg)`
                        val modifierSpan = peek()!!.span.expand(peek(3)!!.span)

                        if (!modifiers.set(Modifier.PubPkg, modifierSpan)) {
                            // Duplication
                            reportModifierDuplication(modifiers[Modifier.PubPkg]!!, modifierSpan)
                        }

                        consume(4)
                        recoverStep += 4
                    } else {
                        // `pub`
                        val span = next()!!.span

                        if (!modifiers.set(Modifier.Pub, span)) {
                            // Duplication
                            reportModifierDuplication(modifiers[Modifier.Pub]!!, span)
                        } else if (!mute) {
                            // Warning about modifiers contains `pub` access modifier by default (it's hidden though)
                            reportRedundantModifier(Modifier.Pub, modifiers[Modifier.Pub]!!)
                        }

                        recoverStep += 1
                    }
                }

                optExpectKeyword(Keyword.PRIV) -> {
                    // `priv`
                    val span = next()!!.span

                    if (!modifiers.set(Modifier.Priv, span)) {
                        // Duplication
                        reportModifierDuplication(modifiers[Modifier.Priv]!!, span)
                    }

                    recoverStep += 1
                }

                optExpectKeyword(Keyword.PROT) -> {
                    // `prot`
                    val span = next()!!.span

                    if (!modifiers.set(Modifier.Prot, span)) {
                        // Duplication
                        reportModifierDuplication(modifiers[Modifier.Prot]!!, span)
                    }

                    recoverStep += 1
                }

                optExpectKeyword(Keyword.INLINE) -> {
                    // `inline`
                    val span = next()!!.span

                    if (!modifiers.set(Modifier.Inline, span)) {
                        // Duplication
                        reportModifierDuplication(modifiers[Modifier.Inline]!!, span)
                    }

                    recoverStep += 1
                }

                optExpectKeyword(Keyword.MUT) -> {
                    // `mut`
                    val span = next()!!.span

                    if (!modifiers.set(Modifier.Mut, span)) {
                        // Duplication
                        reportModifierDuplication(modifiers[Modifier.Mut]!!, span)
                    }

                    recoverStep += 1
                }

                optExpectKeyword(Keyword.INNER) -> {
                    // `inner`
                    val span = next()!!.span

                    if (!modifiers.set(Modifier.Inner, span)) {
                        // Duplication
                        reportModifierDuplication(modifiers[Modifier.Inner]!!, span)
                    }

                    recoverStep += 1
                }

                else -> break // Not a modifier
            }
        }

        this.lastRecoverSteps = recoverStep

        return modifiers
    }

    private fun parseSimplePath(): Path.SimplePath {
        val baseSegment =
            expect(TokenType.Identifier) // simple path's segment should not be empty, and always starts with identifier
        val pathSegments = mutableListOf(baseSegment)

        while (pos < tokens.size && optExpectType(TokenType.DoubleColon)) {
            pathSegments += next()!!
            pathSegments += expect(TokenType.Identifier)
        }

        return Path.SimplePath(pathSegments.map(Path::TokenPath))
    }

    private fun expect(type: TokenType): Token {
        val token = peek()

        pos++

        return when {
            token == null -> {
                // Token out of bound
                reportUnexpectedToken(null, Token.syntheticToken(type, peek(-2)?.span))
            }

            token.type != type -> {
                // Token mismatch, returns a synthetic token
                reportUnexpectedToken(token, Token.syntheticToken(type, token.span))
            }

            else -> token
        }
    }

    private fun optExpectKeyword(keyword: Keyword, offset: Int = 0): Boolean =
        peek(offset)?.isKeyword(keyword) == true

    private fun optExpectType(type: TokenType, offset: Int = 0): Boolean =
        peek(offset)?.isType(type) == true

    private fun optExpectRepeatType(type: TokenType, repeat: Int, offset: Int = 0): Boolean {
        var pass = true

        for (i in 0 until repeat) {
            if (!optExpectType(type, offset + i)) {
                pass = false
                break
            }
        }

        return pass
    }

    private fun peek(offset: Int = 0): Token? =
        tokens.getOrNull(pos + offset)

    private fun consume(count: Int = 1) {
        pos += count
    }

    private fun next(): Token? =
        if (pos < tokens.size) {
            pos++
            peek(-1)
        } else {
            tokens.lastOrNull()
        }

    private fun recover() {
        pos -= lastRecoverSteps
        lastRecoverSteps = 0
    }
}
