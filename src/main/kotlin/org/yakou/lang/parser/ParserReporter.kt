package org.yakou.lang.parser

import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.ast.Keyword
import org.yakou.lang.ast.Modifier
import org.yakou.lang.ast.Modifiers
import org.yakou.lang.ast.Token
import org.yakou.lang.ast.TokenType
import org.yakou.lang.compilation.UnitReporter

internal interface ParserReporter : UnitReporter {
    fun reportUnusedModifiers(modifiers: Modifiers, message: String) {
        reporter()
            .warning(
                adjustSpan(modifiers.span!!),
                "Unused modifiers in current context"
            )
            .label(modifiers.span!!, message)
            .color(Attribute.CYAN_TEXT())
            .hint("It's safe to remove")
            .build().build()
    }

    fun reportRedundantModifier(modifier: Modifier, span: Span) {
        val pubLiteral = colorize(modifier.toString(), Attribute.CYAN_TEXT())

        reporter()
            .warning(
                adjustSpan(span),
                "Redundant `$pubLiteral` access modifier"
            )
            .label(span, "Access modifier is `$pubLiteral` by default")
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }

    fun reportModifierDuplication(firstSpan: Span, duplicatedSpan: Span) {
        reporter()
            .error(
                adjustSpan(firstSpan.expand(duplicatedSpan)),
                "Modifier duplicated"
            )
            .label(firstSpan, "First encountered here")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(duplicatedSpan, "Duplicated here")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportIllegalModifiersInPlace(modifiers: Modifiers) {
        for ((_, span) in modifiers) {
            reporter()
                .error(
                    adjustSpan(span),
                    "Illegal modifier in current context"
                )
                .label(span, "Illegal modifier")
                .color(Attribute.RED_TEXT())
                .build().build()
        }
    }

    fun reportUnexpectedToken(originalToken: Token) {
        val originalTokenLiteral = originalToken.colorizeTokenType(preference(), Attribute.RED_TEXT())

        reporter()
            .error(
                adjustSpan(originalToken.span),
                "Unexpected token $originalTokenLiteral"
            )
            .label(originalToken.span, "Unexpected token here")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportUnexpectedToken(originalToken: Token?, syntheticToken: Token): Token {
        val originalTokenLiteral = originalToken?.colorizeTokenType(preference(), Attribute.RED_TEXT())
        val syntheticTokenLiteral = syntheticToken.colorizeTokenType(preference(), Attribute.CYAN_TEXT())

        reporter()
            .error(
                adjustSpan(syntheticToken.span),
                "Unexpected token%s",
                originalToken?.let { " $originalTokenLiteral" }.orEmpty()
            )
            .label(syntheticToken.span, "Expected $syntheticTokenLiteral")
            .color(Attribute.CYAN_TEXT())
            .build().build()

        return syntheticToken
    }

    /**
     * Consume current token and report it, this does not generate a synthetic token since there's more than 1 choices.
     */
    fun reportUnexpectedToken(
        originalToken: Token,
        acceptableType: TokenType,
        vararg additionalTypes: TokenType
    ) {
        val originalTokenLiteral = originalToken.colorizeTokenType(preference(), Attribute.RED_TEXT())
        val acceptableTokenLiteral = arrayOf(acceptableType, *additionalTypes)
            .joinToString(prefix = "Expected ") {
                it.colorizeLiteral(
                    preference(),
                    Attribute.CYAN_TEXT()
                )
            }

        reporter()
            .error(
                adjustSpan(originalToken.span),
                "Unexpected token %s",
                originalTokenLiteral
            )
            .label(originalToken.span, acceptableTokenLiteral)
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }

    fun reportUnexpectedToken(
        originalToken: Token,
        acceptableKeyword: Keyword,
        vararg additionalKeyword: Keyword
    ) {
        val originalTokenLiteral = originalToken.colorizeTokenType(preference(), Attribute.RED_TEXT())
        val acceptableTokenLiteral = arrayOf(acceptableKeyword, *additionalKeyword)
            .joinToString(prefix = "Expected ") {
                "`${colorize(it.literal, Attribute.CYAN_TEXT())}`"
            }

        reporter()
            .error(
                adjustSpan(originalToken.span),
                "Unexpected token %s",
                originalTokenLiteral
            )
            .label(originalToken.span, acceptableTokenLiteral)
            .color(Attribute.CYAN_TEXT())
            .build().build()
    }
}
