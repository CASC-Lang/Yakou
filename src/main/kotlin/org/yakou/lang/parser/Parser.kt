package org.yakou.lang.parser

import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.ast.*
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.util.SpanHelper

class Parser(private val compilationUnit: CompilationUnit) {
    private val tokens: List<Token> = compilationUnit.tokens ?: listOf()
    private var pos: Int = 0

    fun parse(): YkFile {
        val items = parseItems()

        return YkFile(items)
    }

    private fun parseItems(): List<Item> {
        val items = mutableListOf<Item>()

        while (pos < tokens.size)
            parseItem()?.let(items::add)

        return items
    }

    private fun parseScopedItems(): List<Item> {
        val items = mutableListOf<Item>()

        while (pos < tokens.size && !optExpectType(TokenType.CloseBrace))
            parseItem()?.let(items::add)

        return items
    }

    private fun parseItem(): Item? {
        val modifiers = parseModifiers()

        return when {
            optExpectKeyword(Keyword.PKG) -> {
                // Item.Package
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

                Item.Package(pkg, identifier, openBrace, innerItems, closeBrace)
            }
            optExpectKeyword(Keyword.FN) -> parseFunction(modifiers)
            optExpectKeyword(Keyword.CONST) -> parseConst(modifiers)
            else -> {
                reportUnexpectedToken(next()!!, Keyword.PKG, Keyword.CLASS, Keyword.IMPL, Keyword.FN)

                null
            }
        }
    }

    private fun parseConst(modifiers: Modifiers): Item.Const {
        val const = next()!! // Should be asserted when called
        val identifier = expect(TokenType.Identifier)
        val colon: Token?
        val type: Type?

        if (optExpectType(TokenType.Colon)) {
            // Specified constant field's type
            colon = next()!!
            type = parseType()
        } else {
            colon = null
            type = null
        }

        val equal = expect(TokenType.Equal)
        val expression = parseExpression()

        return Item.Const(
            modifiers,
            const,
            identifier,
            colon,
            type,
            equal,
            expression
        )
    }

    private fun parseFunction(modifiers: Modifiers): Item.Function {
        val fn = next()!! // Should be asserted when called
        val name = expect(TokenType.Identifier)
        val openParenthesis = expect(TokenType.OpenParenthesis)
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

        return Item.Function(
            modifiers,
            fn,
            name,
            openParenthesis,
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

    private fun parseStatement(): Statement {
        TODO()
    }

    private fun parseExpression(): Expression {
        return parseLiteralExpression()
    }

    private fun parseLiteralExpression(): Expression = when {
        optExpectType(TokenType.NumberLiteral) -> {
            val numberToken = next()!! as Token.NumberLiteralToken
            val integer = numberToken.integerLiteral?.let { Token(it, TokenType.Synthetic, numberToken.integerLiteralSpan!!) }
            val float = numberToken.floatLiteral?.let { Token(it, TokenType.Synthetic, numberToken.floatLiteralSpan!!) }
            val type = numberToken.typeAnnotation?.let { Type.TypePath(Path.SimplePath(listOf(Token(it, TokenType.Identifier, numberToken.typeAnnotationSpan!!)))) }

            Expression.NumberLiteral(integer, numberToken.dot, float, type, numberToken.span)
        }
        else -> {
            reportUnexpectedToken(next()!!)

            Expression.Undefined
        }
    }

    private fun parseParameters(): List<Parameter> {
        val parameters = mutableListOf<Parameter>()

        while (pos < tokens.size && optExpectType(TokenType.Identifier)) {
            parameters += parseParameter()

            if (optExpectType(TokenType.Comma)) consume()
            else break
        }

        return parameters
    }

    private fun parseParameter(): Parameter {
        val name = expect(TokenType.Identifier)
        val colon = expect(TokenType.Colon)
        val type = parseType()

        return Parameter(name, colon, type)
    }

    private fun parseType(): Type = when {
        optExpectType(TokenType.OpenBracket) -> {
            // Array type
            val openBracket = next()!!
            val type = parseType()
            val closeBracket = expect(TokenType.CloseBracket)

            Type.Array(openBracket, type, closeBracket)
        }
        else -> {
            // Path type
            val simplePath = parseSimplePath()

            Type.TypePath(simplePath)
        }
    }

    private fun parseModifiers(): Modifiers {
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
                    } else {
                        // `pub`
                        val span = next()!!.span

                        if (!modifiers.set(Modifier.Pub, span)) {
                            // Duplication
                            reportModifierDuplication(modifiers[Modifier.Pub]!!, span)
                        } else {
                            // Warning about modifiers contains `pub` access modifier by default (it's hidden though)
                            val pubLiteral =
                                if (compilationUnit.preference.enableColor) Ansi.colorize("pub", Attribute.CYAN_TEXT())
                                else "pub"

                            compilationUnit.reportBuilder
                                .warning(
                                    SpanHelper.expandView(span, compilationUnit.maxLineCount),
                                    "Redundant `$pubLiteral` access modifier"
                                )
                                .label(span, "Access modifier is `$pubLiteral` by default")
                                .color(Attribute.CYAN_TEXT())
                                .build().build()
                        }
                    }
                }
                optExpectKeyword(Keyword.PRIV) -> {
                    // `priv`
                    val span = next()!!.span

                    if (!modifiers.set(Modifier.Priv, span)) {
                        // Duplication
                        reportModifierDuplication(modifiers[Modifier.Priv]!!, span)
                    }
                }
                optExpectKeyword(Keyword.PROT) -> {
                    // `prot`
                    val span = next()!!.span

                    if (!modifiers.set(Modifier.Prot, span)) {
                        // Duplication
                        reportModifierDuplication(modifiers[Modifier.Prot]!!, span)
                    }
                }
                optExpectKeyword(Keyword.MUT) -> {
                    // `mut`
                    val span = next()!!.span

                    if (!modifiers.set(Modifier.Mut, span)) {
                        // Duplication
                        reportModifierDuplication(modifiers[Modifier.Mut]!!, span)
                    }
                }
                else -> break // Not a modifier
            }
        }

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

        return Path.SimplePath(pathSegments)
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

    private fun peek(offset: Int = 0): Token? =
        tokens.getOrNull(pos + offset)

    private fun consume(count: Int = 1) {
        pos += count
    }

    private fun next(): Token? =
        if (pos < tokens.size) {
            pos++
            peek(-1)
        } else tokens.lastOrNull()
    private fun reportModifierDuplication(firstSpan: Span, duplicatedSpan: Span) {
        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(firstSpan.expand(duplicatedSpan), compilationUnit.maxLineCount),
                "Modifier duplicated"
            )
            .label(firstSpan, "First encountered here")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(duplicatedSpan, "Duplicated here")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportIllegalModifiersInPlace(modifiers: Modifiers) {
        for ((_, span) in modifiers.modifierMap) {
            compilationUnit.reportBuilder
                .error(
                    SpanHelper.expandView(span, compilationUnit.maxLineCount),
                    "Illegal modifier in current context"
                )
                .label(span, "Illegal modifier")
                .color(Attribute.RED_TEXT())
                .build().build()
        }
    }

    private fun reportUnexpectedToken(originalToken: Token) {
        val originalTokenLiteral = originalToken.colorizeTokenType(compilationUnit.preference, Attribute.RED_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(originalToken.span, compilationUnit.maxLineCount),
                "Unexpected token $originalTokenLiteral"
            )
            .label(originalToken.span, "Unexpected token here")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportUnexpectedToken(originalToken: Token?, syntheticToken: Token): Token {
        val originalTokenLiteral = originalToken?.colorizeTokenType(compilationUnit.preference, Attribute.RED_TEXT())
        val syntheticTokenLiteral = syntheticToken.colorizeTokenType(compilationUnit.preference, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(syntheticToken.span, compilationUnit.maxLineCount),
                "Unexpected token%s",
                originalToken?.let { " $originalTokenLiteral" }.orEmpty()
            )
            .label(syntheticToken.span, "Expected $syntheticTokenLiteral")
            .color(Attribute.CYAN_TEXT())
            .build().build()

        return syntheticToken
    }

    /**
     * Consume current token and report it, this does not generate a synthetic token since there's more than 1 choices.
     */
    private fun reportUnexpectedToken(
        originalToken: Token,
        acceptableType: TokenType,
        vararg additionalTypes: TokenType
    ) {
        val originalTokenLiteral = originalToken.colorizeTokenType(compilationUnit.preference, Attribute.RED_TEXT())
        val acceptableTokenLiteral = arrayOf(acceptableType, *additionalTypes)
            .joinToString(prefix = "Expected ") {
                it.colorizeLiteral(
                    compilationUnit.preference,
                    Attribute.CYAN_TEXT()
                )
            }

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(originalToken.span, compilationUnit.maxLineCount),
                "Unexpected token %s",
                originalTokenLiteral
            )
            .label(originalToken.span, acceptableTokenLiteral)
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }

    private fun reportUnexpectedToken(
        originalToken: Token,
        acceptableKeyword: Keyword,
        vararg additionalKeyword: Keyword
    ) {
        val originalTokenLiteral = originalToken.colorizeTokenType(compilationUnit.preference, Attribute.RED_TEXT())
        val acceptableTokenLiteral = arrayOf(acceptableKeyword, *additionalKeyword)
            .joinToString(prefix = "Expected ") {
                "`${
                    (if (compilationUnit.preference.enableColor) Ansi.colorize(
                        it.literal,
                        Attribute.CYAN_TEXT()
                    ) else it.literal)
                }`"
            }

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(originalToken.span, compilationUnit.maxLineCount),
                "Unexpected token %s",
                originalTokenLiteral
            )
            .label(originalToken.span, acceptableTokenLiteral)
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }
}