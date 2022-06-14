package org.yakou.lang.lexer

import chaos.unity.nenggao.Span

open class Token(open val literal: String, val type: TokenType, val span: Span) {
    class NumberLiteralToken(
        val integerLiteral: String?,
        val floatLiteral: String?,
        val typeAnnotation: String?,
        val integerLiteralSpan: Span?,
        val floatLiteralSpan: Span?,
        val typeAnnotationSpan: Span?,
        span: Span,
    ) : Token(
        integerLiteral.orEmpty() + floatLiteral.orEmpty() + typeAnnotation.orEmpty(),
        TokenType.NumberLiteral(integerLiteral.orEmpty() + floatLiteral.orEmpty() + typeAnnotation.orEmpty()),
        span
    )
}
