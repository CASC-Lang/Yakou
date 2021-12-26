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
    DoubleGreater,
    TripleGreater,
    Lesser,
    LesserEqual,
    DoubleLesser,
    Pipe,
    DoublePipe,
    Ampersand,
    DoubleAmpersand,
    Hat,
    Tilde,
    Plus,
    Minus,
    Star,
    Slash,
    Percentage;

    fun unaryPrecedence(): Int = when (this) {
        Plus, Minus, Tilde, Bang -> 11
        else -> 0
    }

    fun binaryPrecedence(): Int = when (this) {
        Star, Slash, Percentage -> 10
        Plus, Minus -> 9
        DoubleGreater, DoubleLesser, TripleGreater -> 8
        Greater, GreaterEqual, Lesser, LesserEqual -> 7
        EqualEqual, BangEqual -> 6
        Ampersand -> 5
        Hat -> 4
        Pipe -> 3
        DoubleAmpersand -> 2
        DoublePipe -> 1
        else -> 0
    }
}