package org.casc.lang.ast

enum class TokenType {
    Identifier,
    IntegerLiteral,
    CharLiteral,
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
    SemiColon,
    Comma,
    Dot,
    Bang,
    Equal,
    EqualEqual,
    BangEqual,
    Greater,
    GreaterEqual,
    Lesser,
    LesserEqual,
    Plus,
    Minus,
    Star,
    Slash,
    Percentage;

    fun unaryPrecedence(): Int = when (this) {
        Plus, Minus, Bang -> 6
        else -> 0
    }

    fun binaryPrecedence(): Int = when (this) {
        Star, Slash, Percentage -> 5
        Plus, Minus -> 4
        Greater, GreaterEqual, Lesser, LesserEqual -> 3
        EqualEqual, BangEqual -> 2
        else -> 0
    }
}