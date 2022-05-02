package org.casc.lang.lexer

import org.casc.lang.ast.Position
import org.casc.lang.ast.Token
import org.casc.lang.ast.TokenType
import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report

class Lexer(private val preference: AbstractPreference) {
    private lateinit var currentLine: String
    private val reports = mutableListOf<Report>()
    private val tokens = mutableListOf<Token>()

    private var lineNumber: Int = 1
    private var pos: Int = 0

    private fun currentPos(): Position =
        Position(lineNumber, pos)

    private fun hasNext(): Boolean =
        pos < currentLine.length

    private fun skip(offset: Int = 1): Int {
        val currentPos = pos
        pos += offset
        return currentPos
    }

    private fun peek(offset: Int = 0): Char = when {
        pos + offset >= currentLine.length -> '\u0000'
        else -> currentLine[pos + offset]
    }

    private fun peekInc(): Char {
        pos++
        return peek(-1)
    }

    fun lex(chunkedSource: List<String>): Pair<List<Report>, List<Token>> {
        if (chunkedSource.isEmpty())
            return listOf(Error("Unable to lex an empty source file")) to listOf()

        for (i in chunkedSource.indices) {
            currentLine = chunkedSource[i]

            while (hasNext()) {
                // Number Literals
                if (peek().isDigit()) {
                    lexNumberLiteral(reports, tokens)
                    continue
                }

                // Whitespace & Tabs
                if (peek().isWhitespace()) {
                    while (hasNext() && peek().isWhitespace())
                        pos++

                    // Discard Whitespace tokens
                    continue
                }

                // Identifiers (Including Keywords)
                if (!isSymbol(peek(), false)) {
                    val start = pos

                    while (hasNext() && !isSymbol(peek()))
                        pos++

                    tokens += Token(
                        currentLine.substring(start until pos),
                        TokenType.Identifier,
                        Position(lineNumber, start, pos)
                    )
                    continue
                }

                // Char Literal
                if (peek() == '\'') {
                    lexCharLiteral(reports, tokens)
                    continue
                }

                // String Literal
                if (peek() == '"') {
                    lexStringLiteral(reports, tokens)
                    continue
                }

                // Operators etc.
                lexSymbols(tokens, reports)
            }

            // move to next line
            pos = 0
            lineNumber++
        }

        return reports to tokens
    }

    private fun lexNumberLiteral(
        reports: MutableList<Report>,
        tokens: MutableList<Token>
    ) {
        var endsWithDot = false
        var isFloatingPointNumber = false
        val start = pos

        while (hasNext() && peek().isDigit())
            pos++

        if (hasNext()) {
            if (peek() == '.') {
                isFloatingPointNumber = true
                pos++

                while (hasNext() && peek().isDigit()) {
                    if (!endsWithDot) endsWithDot = true
                    pos++
                }
            }

            if (hasNext()) {
                when (peek()) {
                    'B', 'S', 'I', 'L' -> {
                        if (isFloatingPointNumber) {
                            reports += Error(
                                currentPos(),
                                "Cannot declare a floating point number literal as non-floating type",
                                "Consider removing type suffix"
                            )
                        }

                        pos++
                    }
                    'F', 'D' -> {
                        isFloatingPointNumber = true
                        pos++
                    }
                }
            }
        }

        tokens += Token(
            currentLine.substring(start until pos),
            if (isFloatingPointNumber) TokenType.FloatLiteral
            else TokenType.IntegerLiteral,
            Position(lineNumber, start, pos)
        )
    }

    private fun lexSymbols(
        tokens: MutableList<Token>,
        reports: MutableList<Report>
    ) {
        when (peek()) {
            '{' -> tokens.charToken(TokenType.OpenBrace)
            '}' -> tokens.charToken(TokenType.CloseBrace)
            '[' -> tokens.charToken(TokenType.OpenBracket)
            ']' -> tokens.charToken(TokenType.CloseBracket)
            '(' -> tokens.charToken(TokenType.OpenParenthesis)
            ')' -> tokens.charToken(TokenType.CloseParenthesis)
            ':' -> when (peek(1)) {
                ':' ->
                    tokens += Token(
                        currentLine.substring(pos..pos + 1),
                        TokenType.DoubleColon,
                        Position(lineNumber, pos, skip(2) + 1)
                    )
                '=' ->
                    tokens += Token(
                        currentLine.substring(pos..pos + 1),
                        TokenType.ColonEqual,
                        Position(lineNumber, pos, skip(2) + 1)
                    )
                else -> tokens.charToken(TokenType.Colon)
            }
            ';' -> tokens.charToken(TokenType.SemiColon)
            ',' -> tokens.charToken(TokenType.Comma)
            '.' -> tokens.charToken(TokenType.Dot)
            '!' -> when (peek(1)) {
                '=' ->
                    tokens += Token(
                        currentLine.substring(pos..pos + 1),
                        TokenType.BangEqual,
                        Position(lineNumber, pos, skip(2) + 1)
                    )
                else -> tokens.charToken(TokenType.Bang)
            }
            '=' -> when (peek(1)) {
                '=' ->
                    tokens += Token(
                        currentLine.substring(pos..pos + 1),
                        TokenType.EqualEqual,
                        Position(lineNumber, pos, skip(2) + 1)
                    )
                else -> tokens.charToken(TokenType.Equal)
            }
            '|' -> when (peek(1)) {
                '|' ->
                    tokens += Token(
                        currentLine.substring(pos..pos + 1),
                        TokenType.DoublePipe,
                        Position(lineNumber, pos, skip(2) + 1)
                    )
                else -> tokens.charToken(TokenType.Pipe)
            }
            '&' -> when (peek(1)) {
                '&' ->
                    tokens += Token(
                        currentLine.substring(pos..pos + 1),
                        TokenType.DoubleAmpersand,
                        Position(lineNumber, pos, skip(2) + 1)
                    )
                else -> tokens.charToken(TokenType.Ampersand)
            }
            '>' -> when (peek(1)) {
                '=' ->
                    tokens += Token(
                        currentLine.substring(pos..pos + 1),
                        TokenType.GreaterEqual,
                        Position(lineNumber, pos, skip(2) + 1)
                    )
                '>' ->
                    tokens += if (peek(2) == '>') {
                        Token(
                            currentLine.substring(pos..pos + 2),
                            TokenType.TripleGreater,
                            Position(lineNumber, pos, skip(3) + 1)
                        )
                    } else {
                        Token(
                            currentLine.substring(pos..pos + 1),
                            TokenType.DoubleGreater,
                            Position(lineNumber, pos, skip(2) + 2)
                        )
                    }
                else -> tokens.charToken(TokenType.Greater)
            }
            '<' -> when (peek(1)) {
                '=' ->
                    tokens += Token(
                        currentLine.substring(pos..pos + 1),
                        TokenType.LesserEqual,
                        Position(lineNumber, pos, skip(2) + 1)
                    )
                '<' ->
                    tokens += Token(
                        currentLine.substring(pos..pos + 1),
                        TokenType.DoubleLesser,
                        Position(lineNumber, pos, skip(2) + 1)
                    )
                else -> tokens.charToken(TokenType.Lesser)
            }
            '~' -> tokens.charToken(TokenType.Tilde)
            '^' -> tokens.charToken(TokenType.Hat)
            '+' -> when (peek(1)) {
                '+' ->
                    tokens += Token(
                        currentLine.substring(pos..pos + 1),
                        TokenType.DoublePlus,
                        Position(lineNumber, pos, skip(2) + 1)
                    )
                else -> tokens.charToken(TokenType.Plus)
            }
            '-' -> when (peek(1)) {
                '-' ->
                    tokens += Token(
                        currentLine.substring(pos..pos + 1),
                        TokenType.DoubleMinus,
                        Position(lineNumber, pos, skip(2) + 1)
                    )
                else -> tokens.charToken(TokenType.Minus)
            }
            '*' -> tokens.charToken(TokenType.Star)
            '/' -> tokens.charToken(TokenType.Slash)
            '%' -> tokens.charToken(TokenType.Percentage)
            else ->
                reports += Report.Error(
                    currentPos(),
                    "Unexpected character ${currentLine[pos++]}"
                )
        }
    }

    private fun lexStringLiteral(
        reports: MutableList<Report>,
        tokens: MutableList<Token>
    ) {
        var enclosed = false
        val start = pos++
        var builder = ""

        while (hasNext()) {
            when (peek()) {
                '"' -> {
                    enclosed = true
                    break
                }
                '\\' -> {
                    // Escaped characters
                    pos++

                    when (peekInc()) {
                        '\\' -> builder += "\\"
                        't' -> builder += "\t"
                        'b' -> builder += "\b"
                        'n' -> builder += "\n"
                        'r' -> builder += "\r"
                        'f' -> builder += "\u000c"
                        '\'' -> builder += "'"
                        '\"' -> builder += "\""
                        'u' -> {
                            // Escaped unicode literal
                            var unicodeHexBuilder = ""

                            for (i in 0 until 4) {
                                val current = peek()

                                if (current.digitToIntOrNull() != null || current in 'a' .. 'f' || current in 'A'..'F') {
                                    unicodeHexBuilder += peekInc()
                                } else {
                                    reports += Error(
                                        currentPos(),
                                        "Invalid hexadecimal digit ${peekInc()} for unicode literal",
                                        "Valid candidates are [a-fA-F0-9]"
                                    )
                                }
                            }

                            val hex = unicodeHexBuilder.chunked(2).map { Integer.parseInt(it, 16) }

                            builder += Char((hex[0] shl 8) or hex[1])
                        }
                        else -> {
                            reports += Error(
                                Position(lineNumber, pos - 2, pos - 1),
                                "Unknown escaped character"
                            )
                        }
                    }
                }
                else -> builder += peekInc()
            }
        }

        if (!enclosed) {
            reports += Error(
                currentPos(),
                "Unclosed string literal",
                "Add `\"` here"
            )
        }

        val end = pos++

        tokens += Token(
            builder,
            TokenType.StringLiteral,
            Position(lineNumber, start, end)
        )
    }

    private fun lexCharLiteral(
        reports: MutableList<Report>,
        tokens: MutableList<Token>
    ) {
        var enclosed = false
        val start = pos++

        var char: Char? = null

        while (hasNext() && !enclosed) {
            val currentChar = when (peek()) {
                '\'' -> {
                    pos++

                    if (char == null) {
                        // No character presented
                        reports += Error(
                            currentPos(),
                            "Empty character literal"
                        )
                    }

                    enclosed = true

                    null
                }
                '\\' -> {
                    // Escaped characters
                    pos++

                    when (peekInc()) {
                        '\\' -> '\\'
                        't' -> '\t'
                        'b' -> '\b'
                        'n' -> '\n'
                        'r' -> '\r'
                        'f' -> '\u000c'
                        '\'' -> '\''
                        '\"' -> '"'
                        'u' -> {
                            // Escaped unicode literal
                            var invalidUnicodeLiteral = false
                            var unicodeHexBuilder = ""

                            for (i in 0 until 4) {
                                if (!hasNext()) {
                                    reports += Error(
                                        currentPos(),
                                        "Unexpected linebreak while parsing unicode literal"
                                    )
                                    invalidUnicodeLiteral = true
                                    break
                                }

                                val current = peek()

                                if (current.digitToIntOrNull() != null || current in 'a' .. 'f' || current in 'A'..'F') {
                                    unicodeHexBuilder += peekInc()
                                } else if (peek() == '\'') {
                                    reports += Error(
                                        currentPos(),
                                        "Incomplete unicode literal ${peekInc()}"
                                    )
                                    invalidUnicodeLiteral = true
                                    break
                                } else {
                                    reports += Error(
                                        currentPos(),
                                        "Invalid hexadecimal digit ${peekInc()} for unicode character literal",
                                        "Valid candidates are [a-fA-F0-9]"
                                    )
                                    invalidUnicodeLiteral = true
                                    break
                                }
                            }

                            if (invalidUnicodeLiteral) {
                                ' '
                            } else {
                                val hex = unicodeHexBuilder.chunked(2).map { Integer.parseInt(it, 16) }

                                Char((hex[0] shl 8) or hex[1])
                            }
                        }
                        else -> {
                            reports += Error(
                                Position(lineNumber, pos - 2, pos - 1),
                                "Unknown escaped character"
                            )

                            ' '
                        }
                    }
                }
                else -> peekInc()
            }

            if (char == null) char = currentChar
            else if (!enclosed) {
                reports += Error(
                    currentPos(),
                    "Too many characters for character literal"
                )
            }
        }

        tokens += Token(
            char ?: '\u0000',
            TokenType.CharLiteral,
            Position(lineNumber, start, pos - 1)
        )
    }

    private fun MutableList<Token>.charToken(type: TokenType) =
        this.add(Token(currentLine[pos], type, Position(lineNumber, pos++)))

    private fun isSymbol(char: Char, allowDigit: Boolean = true): Boolean {
        val charCode = char.code

        return when {
            char.isLetter() -> false
            char.isWhitespace() -> true
            char.isDigit() -> !allowDigit
            charCode > 127 -> false     // Not Ascii Character
            charCode in 33..47 -> true
            charCode in 58..64 -> true
            charCode in 91..96 -> true
            charCode in 123..126 -> true
            else -> false
        }
    }

}