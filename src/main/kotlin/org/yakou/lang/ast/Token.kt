package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.api.AbstractPreference

open class Token(open val literal: String, val type: TokenType, val span: Span) {
    companion object {
        /**
         * Create a token that does not actually exist in the source. Useful when attempting to show missing tokens in
         * the source code.
         *
         * @param expectedType The most possible type to be identified by compiler unit.
         * @param previousSpan The previous token's span, if the source is empty, pass null instead.
         * @return A nonnull synthetic token.
         */
        fun syntheticToken(expectedType: TokenType, previousSpan: Span?): Token =
            Token("", expectedType, previousSpan ?: Span.singleLine(1, 0, 0))
    }

    fun isKeyword(keyword: Keyword): Boolean =
        type == TokenType.Keyword && literal == keyword.literal

    fun colorizeTokenType(preference: AbstractPreference, vararg attribute: Attribute): String {
        val tokenLiteral = when (type) {
            is TokenType.SizedTokenType -> "`${type.literal}`"
            TokenType.Identifier -> "<Identifier>"
            TokenType.Keyword -> "<Keyword>" // TODO: Necessary?
            TokenType.NumberLiteral -> "<Number Literal>" // TODO: Necessary
        }
        return if (preference.enableColor) Ansi.colorize(tokenLiteral, *attribute)
        else tokenLiteral
    }

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
        TokenType.NumberLiteral,
        span
    )
}
