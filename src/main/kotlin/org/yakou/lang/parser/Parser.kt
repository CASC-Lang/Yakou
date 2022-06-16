package org.yakou.lang.parser

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

        while (pos < tokens.size) {
            if (optExpectKeyword(Keyword.PKG)) {
                // Item.Package
                consume() // `pkg` keyword
                val identifier = expect(TokenType.Identifier)
                val innerItems = mutableListOf<Item>()

                // TODO: Allow items declared in pkg scope (same as Module in Rust)

                items += Item.Package(identifier, innerItems)
            }
        }

        return items
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

    private fun peek(offset: Int = 0): Token? =
        tokens.getOrNull(pos + offset)

    private fun consume(count: Int = 1) {
        pos += count
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
}