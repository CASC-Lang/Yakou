package org.yakou.lang.parser

import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.objectweb.asm.Opcodes
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

                if (!modifiers.isEmpty()) {
                    reportIllegalModifiersInPlace(modifiers)
                }

                Item.Package(identifier, openBrace, innerItems, closeBrace)
            }
            else -> {
                reportUnexpectedToken(next()!!, Keyword.PKG, Keyword.CLASS, Keyword.IMPL)

                null
            }
        }
    }

    private fun parseModifiers(immutable: Boolean = true): Modifiers {
        val modifiers = Modifiers(immutable = immutable)

        while (pos < tokens.size) {
            when {
                optExpectKeyword(Keyword.PUB) -> {
                    if (optExpectType(TokenType.OpenParenthesis, 1) &&
                        optExpectKeyword(Keyword.PKG, 2) &&
                        optExpectType(TokenType.CloseParenthesis, 3)
                    ) {
                        // `pub (pkg)`
                        val modifierSpan = peek()!!.span.expand(peek(3)!!.span)

                        if (!modifiers.set(Modifier.PUB_PKG, modifierSpan)) {
                            // Duplication
                            reportModifierDuplication(modifiers[Modifier.PUB_PKG]!!, modifierSpan)
                        }
                        consume(4)
                    } else {
                        // `pub`
                        val span = next()!!.span

                        if (!modifiers.set(Modifier.PUB, span)) {
                            // Duplication
                            reportModifierDuplication(modifiers[Modifier.PUB]!!, span)
                        }
                    }
                }
                optExpectKeyword(Keyword.PRIV) -> {
                    // `priv`
                    val span = next()!!.span

                    if (!modifiers.set(Modifier.PRIV, span)) {
                        // Duplication
                        reportModifierDuplication(modifiers[Modifier.PRIV]!!, span)
                    }
                }
                optExpectKeyword(Keyword.PROT) -> {
                    // `prot`
                    val span = next()!!.span

                    if (!modifiers.set(Modifier.PROT, span)) {
                        // Duplication
                        reportModifierDuplication(modifiers[Modifier.PROT]!!, span)
                    }
                }
                optExpectKeyword(Keyword.MUT) -> {
                    // `mut`
                    val span = next()!!.span

                    if (!modifiers.set(Modifier.MUT, span)) {
                        // Duplication
                        reportModifierDuplication(modifiers[Modifier.MUT]!!, span)
                    }
                }
                else -> break // Not a modifier
            }
        }

        return modifiers
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

    private fun optExpectKeyword(keyword: Keyword, offset: Int = 0): Boolean =
        peek(offset)?.isKeyword(keyword) == true

    private fun optExpectType(type: TokenType, offset: Int = 0): Boolean =
        peek(offset)?.isType(type) == true

    private fun peek(offset: Int = 0): Token? =
        tokens.getOrNull(pos + offset)

    private fun consume(count: Int = 1) {
        pos += count
    }

    private fun next(): Token? {
        pos++
        return peek(-1)
    }

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
            .build()
            .build()
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