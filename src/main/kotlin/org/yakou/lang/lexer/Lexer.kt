package org.yakou.lang.lexer

import chaos.unity.nenggao.FileReportBuilder
import chaos.unity.nenggao.Line
import chaos.unity.nenggao.SourceCache
import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.ast.Keyword
import org.yakou.lang.ast.Token
import org.yakou.lang.ast.TokenType
import org.yakou.lang.bind.PrimitiveType
import org.yakou.lang.util.SpanHelper
import java.io.File

class Lexer(sourceFile: File) {
    private val report: FileReportBuilder =
        FileReportBuilder.sourceFile(sourceFile).relativePath(sourceFile.parentFile.toPath())
    private val lines: List<Line> = SourceCache.INSTANCE.getOrAdd(sourceFile).lines
    private lateinit var currentLine: String
    private val tokens: MutableList<Token> = mutableListOf()
    private var line: Int = 0
    private var pos: Int = 0

    fun lex(): List<Token> {
        while (line < lines.size) {
            currentLine = lines[line].chars

            while (pos < currentLine.length) {
                // Space lexing (ignore spaces)
                if (currentLine[pos].isWhitespace()) {
                    while (pos < currentLine.length && currentLine[pos].isWhitespace())
                        pos++

                    continue
                }

                // Number literal lexing
                if (currentLine[pos].isDigit()) {
                    lexNumberLiteral()
                    continue
                }

                // Float Literal lexing
                if (currentLine[pos] == '.') {
                    val (floatLiteral, floatLiteralSpan) = lexFloatLiteral()
                    val (typeAnnotation, typeAnnotationSpan) =
                        if (!currentLine[pos].isWhitespace()) lexLiteralTypeAnnotation()
                        else null to null

                    reportInvalidTypeAnnotation(typeAnnotation, typeAnnotationSpan)

                    tokens += Token.NumberLiteralToken(
                        null,
                        floatLiteral,
                        typeAnnotation,
                        null,
                        floatLiteralSpan,
                        typeAnnotationSpan,
                        floatLiteralSpan.expand(typeAnnotationSpan)
                    )
                    continue
                }

                // Identifier / Keyword lexing
                if (currentLine[pos].isLetter()) {
                    val (identifier, identifierSpan) = lexSegment { !it.isWhitespace() }

                    tokens += Token(
                        identifier,
                        if (Keyword.isKeyword(identifier)) TokenType.Keyword(identifier) else TokenType.Identifier(
                            identifier
                        ),
                        identifierSpan
                    )
                    continue
                }

                lexSymbol()
            }

            line++
        }

        report.print(System.out)

        return tokens
    }

    private fun lexNumberLiteral() {
        val (integerLiteral, integerLiteralSpan) = lexIntegerLiteral()
        val (floatLiteral, floatLiteralSpan) =
            if (currentLine[pos] == '.') lexFloatLiteral()
            else null to null
        val (typeAnnotation, typeAnnotationSpan) =
            if (!currentLine[pos].isWhitespace()) lexLiteralTypeAnnotation()
            else null to null

        reportInvalidTypeAnnotation(typeAnnotation, typeAnnotationSpan)

        tokens += Token.NumberLiteralToken(
            integerLiteral,
            floatLiteral,
            typeAnnotation,
            integerLiteralSpan,
            floatLiteralSpan,
            typeAnnotationSpan,
            integerLiteralSpan.expand(floatLiteralSpan).expand(typeAnnotationSpan)
        )
    }

    private fun lexIntegerLiteral(): Pair<String, Span> {
        return lexSegment(Char::isDigit)
    }

    // lexFloatLiteral returns float literal that does not contains dot,
    // but span still does.
    private fun lexFloatLiteral(): Pair<String, Span> {
        val hasDot = if (currentLine[pos] == '.') {
            pos++
            true
        } else false
        val (floatLiteral, span) = lexSegment(Char::isDigit)

        return (if (hasDot) ".$floatLiteral" else floatLiteral) to span
    }

    private fun lexLiteralTypeAnnotation(): Pair<String, Span> {
        return lexSegment { !it.isWhitespace() }
    }

    private inline fun lexSegment(predicate: (Char) -> Boolean): Pair<String, Span> {
        val startPos = pos

        while (pos < currentLine.length && predicate(currentLine[pos]))
            pos++

        val lastPos = pos
        val range = startPos until lastPos
        return currentLine.substring(range) to range.span()
    }

    private fun lexSymbol() {
        when (peek()) {
            '{' -> charToken(TokenType.OpenBrace)
            '}' -> charToken(TokenType.CloseBrace)
            '[' -> charToken(TokenType.OpenBracket)
            ']' -> charToken(TokenType.CloseBracket)
            '(' -> charToken(TokenType.OpenParenthesis)
            ')' -> charToken(TokenType.CloseParenthesis)
            ':' -> when (peek(1)) {
                ':' -> stringToken(TokenType.DoubleColon)
                '=' -> stringToken(TokenType.ColonEqual)
                else -> charToken(TokenType.Colon)
            }
            ';' -> charToken(TokenType.SemiColon)
            ',' -> charToken(TokenType.Comma)
            '.' -> charToken(TokenType.Dot)
            '!' -> when (peek(1)) {
                '=' -> stringToken(TokenType.BangEqual)
                else -> charToken(TokenType.Bang)
            }
            '=' -> when (peek(1)) {
                '=' -> stringToken(TokenType.DoubleEqual)
                else -> charToken(TokenType.Equal)
            }
            '|' -> when (peek(1)) {
                '|' -> stringToken(TokenType.DoublePipe)
                else -> charToken(TokenType.Pipe)
            }
            '&' -> when (peek(1)) {
                '&' -> stringToken(TokenType.DoubleAmpersand)
                else -> charToken(TokenType.Ampersand)
            }
            '>' -> when (peek(1)) {
                '>' -> when (peek(2)) {
                    '>' -> stringToken(TokenType.TripleGreater)
                    else -> stringToken(TokenType.DoubleGreater)
                }
                '=' -> stringToken(TokenType.GreaterEqual)
                else -> charToken(TokenType.Greater)
            }
            '<' -> when (peek(1)) {
                '<' -> stringToken(TokenType.DoubleLesser)
                '=' -> stringToken(TokenType.LesserEqual)
                else -> charToken(TokenType.Lesser)
            }
            '~' -> charToken(TokenType.Tilde)
            '^' -> charToken(TokenType.Hat)
            '+' -> when (peek(1)) {
                '+' -> stringToken(TokenType.DoublePlus)
                else -> charToken(TokenType.Plus)
            }
            '-' -> when (peek(1)) {
                '-' -> stringToken(TokenType.DoubleMinus)
                else -> charToken(TokenType.Minus)
            }
            '*' -> charToken(TokenType.Star)
            '/' -> charToken(TokenType.Slash)
            '%' -> charToken(TokenType.Percentage)
            else -> {
                val currentSpan = currentSpan()

                report.error(
                    SpanHelper.expandView(currentSpan, lines.size),
                    "Unknown character `${currentLine[pos++]}`"
                )
                    .label(currentSpan, "This character is unable to be lexical analyzed")
                    .color(Attribute.RED_TEXT())
                    .build().build()
            }
        }
    }

    private fun charToken(type: TokenType.SizedTokenType) {
        tokens += Token(currentLine.substring(pos..pos), type, Span.singleLine(line, pos, pos++))
    }

    private fun stringToken(type: TokenType.SizedTokenType) {
        tokens += Token(
            currentLine.substring(pos..pos + type.size()),
            type,
            Span.singleLine(line, pos, pos + type.size())
        )
        pos += type.size()
    }

    private fun reportInvalidTypeAnnotation(typeAnnotation: String?, typeAnnotationSpan: Span?) {
        if (typeAnnotation != null && typeAnnotationSpan != null && !PrimitiveType.isNumberType(typeAnnotation)) {
            report.error(
                SpanHelper.expandView(typeAnnotationSpan, lines.size),
                "Unknown type annotation $typeAnnotation"
            )
                .label(typeAnnotationSpan, "Only `i8`, `i16`, `i32`, `i64`, `f32` and `f64` are allowed.")
                .color(Attribute.RED_TEXT())
                .build().build()
        }
    }

    private fun peek(offset: Int = 0): Char? =
        if (pos + offset < currentLine.length) currentLine[pos + offset]
        else null

    private fun currentSpan(): Span =
        Span.singleLine(line + 1, pos, pos + 1)

    private fun IntRange.span(): Span =
        Span.singleLine(line + 1, start, last + 1)
}