package org.casc.lang.lexer

import org.casc.lang.ast.Position
import org.casc.lang.ast.Token
import org.casc.lang.compilation.Report

// TODO: Multi-Threaded Lexing Process
class Lexer(val chunkedSource: List<String>) {
    private var lineNumber: Int = 1
    private var pos: Int = 0

    private fun skip(offset: Int = 1): Int {
        val currentPos = pos
        pos += offset
        return currentPos
    }

    fun lex(): Pair<List<Report>, List<Token>> {
        val reports = mutableListOf<Report>()
        val tokens = mutableListOf<Token>()

        for (source in chunkedSource) {
            while (pos < source.length) {
                // Number Literals
                if (source[pos].isDigit()) {
                    val start = pos

                    while (pos < source.length && source[pos].isDigit())
                        pos++

                    // TODO: Support FloatLiteral
                    tokens += Token(
                        source.substring(start until pos),
                        Token.TokenType.IntegerLiteral,
                        Position(lineNumber, start, pos)
                    )
                    continue
                }

                // Whitespace & Tabs
                if (source[pos].isWhitespace()) {
                    val start = pos

                    while (pos < source.length && source[pos].isWhitespace())
                        pos++

                    // Discard Whitespace tokens
                    continue
                }

                // Identifiers (Including Keywords)
                if (!isSymbol(source[pos])) {
                    val start = pos

                    while (pos < source.length && !isSymbol(source[pos]))
                        pos++

                    tokens += Token(
                        source.substring(start until pos),
                        Token.TokenType.Identifier,
                        Position(lineNumber, start, pos)
                    )
                    continue
                }

                // Operators etc.
                when (source[pos]) {
                    '{' -> tokens += Token(source[pos], Token.TokenType.OpenBracket, Position(lineNumber, pos++))
                    '}' -> tokens += Token(source[pos], Token.TokenType.CloseBracket, Position(lineNumber, pos++))
                    '[' -> tokens += Token(source[pos], Token.TokenType.OpenBrace, Position(lineNumber, pos++))
                    ']' -> tokens += Token(source[pos], Token.TokenType.CloseBrace, Position(lineNumber, pos++))
                    '(' -> tokens += Token(source[pos], Token.TokenType.OpenParenthesis, Position(lineNumber, pos++))
                    ')' -> tokens += Token(source[pos], Token.TokenType.CloseParenthesis, Position(lineNumber, pos++))
                    ':' -> tokens += if (source[pos + 1] == ':') {
                        Token(
                            source.substring(pos until pos + 1),
                            Token.TokenType.DoubleColon,
                            Position(lineNumber, pos, skip(2) + 1)
                        )
                    } else {
                        Token(source[pos], Token.TokenType.Colon, Position(lineNumber, pos++))
                    }
                    ',' -> tokens += Token(source[pos], Token.TokenType.Comma, Position(lineNumber, pos++))
                    else ->
                        reports += Report.Error(
                            Position(lineNumber, pos),
                            "Unexpected character ${source[pos++]}"
                        )
                }
            }

            pos = 0
            lineNumber++
        }

        return reports to tokens
    }

    private fun isSymbol(char: Char): Boolean {
        val charCode = char.code

        return when {
            char.isLetter() -> false
            char.isWhitespace() -> true
            charCode > 127 -> false     // Not Ascii Character
            charCode in 33..47 -> true
            charCode in 58..64 -> true
            charCode in 91..96 -> true
            charCode in 123..126 -> true
            else -> false
        }
    }

}