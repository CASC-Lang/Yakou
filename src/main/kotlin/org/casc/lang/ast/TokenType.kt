package org.casc.lang.ast

enum class TokenType {
    Identifier,
    IntegerLiteral,
    StringLiteral,
    FloatLiteral,
    OpenBrace,
    CloseBrace,
    OpenBracket,
    CloseBracket,
    OpenParenthesis,
    CloseParenthesis,
    Colon,
    DoubleColon,
    ColonEqual,
    Comma,
    Dot,
    Equal,
    Plus,
    Minus,
    Star,
    Slash,
    Percentage;

    fun unaryPrecedence(): Int = when (this) {
        Plus, Minus -> 6
        else -> 0
    }

    fun binaryPrecedence(): Int = when (this) {
        Star, Slash, Percentage -> 5
        Plus, Minus -> 4
        else -> 0
    }
}