package org.yakou.lang.parser

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

    private fun parseItem(): Item? =
        if (optExpectKeyword(Keyword.PKG)) {
            // Item.Package
            consume() // `pkg` keyword
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

            // TODO: Allow items declared in pkg scope (same as Module in Rust)

            Item.Package(identifier, openBrace, innerItems, closeBrace)
        } else {
            reportUnexpectedToken(next()!!, Keyword.PKG, Keyword.CLASS, Keyword.IMPL)

            null
        }

    private fun parseSimplePath(): Path.SimplePath {
        TODO("AUTO GENERATED")
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

    private fun optExpectKeyword(keyword: Keyword): Boolean =
        peek()?.isKeyword(keyword) == true

    private fun optExpectType(type: TokenType): Boolean =
        peek()?.isType(type) == true

    private fun peek(offset: Int = 0): Token? =
        tokens.getOrNull(pos + offset)

    private fun consume(count: Int = 1) {
        pos += count
    }

    private fun next(): Token? {
        pos++
        return peek(-1)
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