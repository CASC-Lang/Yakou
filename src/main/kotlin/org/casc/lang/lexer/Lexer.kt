package org.casc.lang.lexer

import org.casc.lang.ast.Position
import org.casc.lang.ast.Token
import org.casc.lang.ast.TokenType
import org.casc.lang.compilation.Error
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
                    var endsWithDot = false
                    var isFloatingPointNumber = false
                    val start = pos

                    while (pos < source.length && source[pos].isDigit())
                        pos++

                    if (pos != source.length) {
                        if (source[pos] == '.') {

                            isFloatingPointNumber = true
                            pos++

                            while (pos < source.length && source[pos].isDigit()) {
                                if (!endsWithDot) endsWithDot = true
                                pos++
                            }
                        }

                        if (pos != source.length) {
                            when (source[pos]) {
                                'B', 'S', 'I', 'L' -> {
                                    if (isFloatingPointNumber) {
                                        reports += Error(
                                            Position(lineNumber, pos),
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
                        source.substring(start until pos - if (endsWithDot) 1 else 0),
                        if (isFloatingPointNumber) TokenType.FloatLiteral
                        else TokenType.IntegerLiteral,
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
                        TokenType.Identifier,
                        Position(lineNumber, start, pos)
                    )
                    continue
                }

                // Char Literal
                if (source[pos] == '\'') {
                    val start = pos++

                    val char = when (source[pos]) {
                        '\\' -> {
                            // Escaped characters
                            pos++

                            when (source[pos++]) {
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
                                        if (pos >= source.length) {
                                            reports += Error(
                                                Position(lineNumber, pos),
                                                "Unexpected linebreak while parsing unicode literal"
                                            )
                                            invalidUnicodeLiteral = true
                                            break
                                        }

                                        if (source[pos].digitToIntOrNull() != null || source[pos] in 'A'..'F') {
                                            unicodeHexBuilder += source[pos++]
                                        } else if (source[pos] == '\'') {
                                            reports += Error(
                                                Position(lineNumber, pos),
                                                "Incomplete unicode literal ${source[pos++]}"
                                            )
                                            invalidUnicodeLiteral = true
                                            break
                                        } else {
                                            reports += Error(
                                                Position(lineNumber, pos),
                                                "Invalid hexadecimal digit ${source[pos++]} for unicode literal"
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
                        else -> source[pos++]
                    }

                    if (pos < source.length && source[pos] != '\'') {
                        while (pos < source.length && source[pos] != '\'')
                            pos++

                        reports += Error(
                            Position(lineNumber, start + 2, pos - 2),
                            "Too many characters for char literal",
                            "Char literal only allows one character"
                        )
                    }

                    val end = pos++

                    tokens += Token(
                        char,
                        TokenType.CharLiteral,
                        Position(lineNumber, start, end)
                    )
                    continue
                }

                // String Literal
                if (source[pos] == '"') {
                    val start = pos++
                    var builder = ""

                    while (pos < source.length && source[pos] != '"') {
                        when (source[pos]) {
                            '"' -> break
                            '\\' -> {
                                // Escaped characters
                                pos++

                                when (source[pos++]) {
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
                                            if (source[pos].digitToIntOrNull() != null || source[pos] in 'A'..'F') {
                                                unicodeHexBuilder += source[pos++]
                                            } else {
                                                reports += Error(
                                                    Position(lineNumber, pos),
                                                    "Invalid hexadecimal digit ${source[pos++]} for unicode literal"
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
                            else -> builder += source[pos++]
                        }
                    }

                    val end = pos++

                    tokens += Token(
                        builder,
                        TokenType.StringLiteral,
                        Position(lineNumber, start, end)
                    )
                    continue
                }

                // Operators etc.
                when (source[pos]) {
                    '{' -> tokens.charToken(source, TokenType.OpenBrace)
                    '}' -> tokens.charToken(source, TokenType.CloseBrace)
                    '[' -> tokens.charToken(source, TokenType.OpenBracket)
                    ']' -> tokens.charToken(source, TokenType.CloseBracket)
                    '(' -> tokens.charToken(source, TokenType.OpenParenthesis)
                    ')' -> tokens.charToken(source, TokenType.CloseParenthesis)
                    ':' -> when (source[pos + 1]) {
                        ':' ->
                            tokens += Token(
                                source.substring(pos..pos + 1),
                                TokenType.DoubleColon,
                                Position(lineNumber, pos, skip(2) + 1)
                            )
                        '=' ->
                            tokens += Token(
                                source.substring(pos..pos + 1),
                                TokenType.ColonEqual,
                                Position(lineNumber, pos, skip(2) + 1)
                            )
                        else -> tokens.charToken(source, TokenType.Colon)
                    }
                    ';' -> tokens.charToken(source, TokenType.SemiColon)
                    ',' -> tokens.charToken(source, TokenType.Comma)
                    '.' -> tokens.charToken(source, TokenType.Dot)
                    '!' -> when (source[pos + 1]) {
                        '=' ->
                            tokens += Token(
                                source.substring(pos..pos + 1),
                                TokenType.BangEqual,
                                Position(lineNumber, pos, skip(2) + 1)
                            )
                        else -> tokens.charToken(source, TokenType.Bang)
                    }
                    '=' -> when (source[pos + 1]) {
                        '=' ->
                            tokens += Token(
                                source.substring(pos..pos + 1),
                                TokenType.EqualEqual,
                                Position(lineNumber, pos, skip(2) + 1)
                            )
                        else -> tokens.charToken(source, TokenType.Equal)
                    }
                    '|' -> when (source[pos + 1]) {
                        '|' ->
                            tokens += Token(
                                source.substring(pos..pos + 1),
                                TokenType.DoublePipe,
                                Position(lineNumber, pos, skip(2) + 1)
                            )
                        else -> tokens.charToken(source, TokenType.Pipe)
                    }
                    '&' -> when (source[pos + 1]) {
                        '&' ->
                            tokens += Token(
                                source.substring(pos..pos + 1),
                                TokenType.DoubleAmpersand,
                                Position(lineNumber, pos, skip(2) + 1)
                            )
                        else -> tokens.charToken(source, TokenType.Ampersand)
                    }
                    '>' -> when (source[pos + 1]) {
                        '=' ->
                            tokens += Token(
                                source.substring(pos..pos + 1),
                                TokenType.GreaterEqual,
                                Position(lineNumber, pos, skip(2) + 1)
                            )
                        '>' ->
                            tokens += if (source[pos + 2] == '>') {
                                Token(
                                    source.substring(pos..pos + 1),
                                    TokenType.TripleGreater,
                                    Position(lineNumber, pos, skip(2) + 1)
                                )
                            } else {
                                Token(
                                    source.substring(pos..pos + 2),
                                    TokenType.DoubleGreater,
                                    Position(lineNumber, pos, skip(3) + 2)
                                )
                            }
                        else -> tokens.charToken(source, TokenType.Greater)
                    }
                    '<' -> when (source[pos + 1]) {
                        '=' ->
                            tokens += Token(
                                source.substring(pos..pos + 1),
                                TokenType.LesserEqual,
                                Position(lineNumber, pos, skip(2) + 1)
                            )
                        '<' ->
                            tokens += Token(
                                source.substring(pos..pos + 1),
                                TokenType.DoubleLesser,
                                Position(lineNumber, pos, skip(2) + 1)
                            )
                        else -> tokens.charToken(source, TokenType.Lesser)
                    }
                    '~' -> tokens.charToken(source, TokenType.Tilde)
                    '^' -> tokens.charToken(source, TokenType.Hat)
                    '+' -> when (source[pos + 1]) {
                        '+' ->
                            tokens += Token(
                                source.substring(pos..pos + 1),
                                TokenType.DoublePlus,
                                Position(lineNumber, pos, skip(2) + 1)
                            )
                        else -> tokens.charToken(source, TokenType.Plus)
                    }
                    '-' -> when (source[pos + 1]) {
                        '-' ->
                            tokens += Token(
                                source.substring(pos..pos + 1),
                                TokenType.DoubleMinus,
                                Position(lineNumber, pos, skip(2) + 1)
                            )
                        else -> tokens.charToken(source, TokenType.Minus)
                    }
                    '*' -> tokens.charToken(source, TokenType.Star)
                    '/' -> tokens.charToken(source, TokenType.Slash)
                    '%' -> tokens.charToken(source, TokenType.Percentage)
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

    private fun MutableList<Token>.charToken(source: String, type: TokenType) =
        this.add(Token(source[pos], type, Position(lineNumber, pos++)))

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