package org.yakou.lang.parser

import chaos.unity.nenggao.FileReportBuilder
import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.api.AbstractPreference
import org.yakou.lang.ast.*
import org.yakou.lang.compilation.CompilationUnit

class Parser(compilationUnit: CompilationUnit) {
    private val preference: AbstractPreference = compilationUnit.preference
    private val tokens: List<Token> = compilationUnit.tokens ?: listOf()
    private val report: FileReportBuilder = compilationUnit.reportBuilder
    private var pos: Int = 0

    fun parse(): YkFile {
        val items = parseItems()

        return YkFile(items)
    }

    private fun parseItems(): List<Item> {
        val items = mutableListOf<Item>()

        while (pos < tokens.size) {
            if (peek()?.isKeyword(Keyword.PKG) == true) {
                // Item.Package
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
                reportUnexpectedToken(Token.syntheticToken(type, peek(-1)?.span))
            }
            token.type != type -> {
                // Token mismatch, returns a synthetic token
                reportUnexpectedToken(Token.syntheticToken(type, peek(-1)?.span))
            }
            else -> token
        }
    }

    private fun peek(offset: Int = 0): Token? =
        tokens.getOrNull(pos + offset)

    private fun reportUnexpectedToken(syntheticToken: Token): Token {
        val tokenLiteral = when (syntheticToken.type) {
            is TokenType.SizedTokenType -> "`${syntheticToken.type.literal}`"
            TokenType.Identifier -> "<Identifier>"
            TokenType.Keyword -> "<Keyword>" // TODO: Necessary?
            TokenType.NumberLiteral -> "<Number Literal>" // TODO: Necessary
        }
        val colorizedTokenLiteral =
            if (preference.enableColor) Ansi.colorize(tokenLiteral, Attribute.CYAN_TEXT())
            else tokenLiteral

        report.error(syntheticToken.span, "Unexpected token %s", colorizedTokenLiteral)

        return syntheticToken
    }
}