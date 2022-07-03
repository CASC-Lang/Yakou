package org.yakou.lang.ast

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.api.AbstractPreference

sealed class TokenType(open val literal: String?) {
    fun colorizeLiteral(preference: AbstractPreference, vararg attribute: Attribute): String {
        val tokenLiteral = when (this) {
            is SizedTokenType -> "`$literal`"
            Identifier -> "<Identifier>"
            Keyword -> "<Keyword>" // TODO: Necessary?
            NumberLiteral -> "<Number Literal>" // TODO: Necessary
            Synthetic -> "<Synthetic>"
        }
        return if (preference.enableColor) Ansi.colorize(tokenLiteral, *attribute)
        else tokenLiteral
    }

    object Identifier : TokenType(null)
    object Keyword : TokenType(null)
    object NumberLiteral : TokenType(null)
    object Synthetic : TokenType(null)

    sealed class SizedTokenType(override val literal: String) : TokenType(literal) {
        fun size(): Int =
            literal.length
    }

    object OpenBrace : SizedTokenType("{")
    object CloseBrace : SizedTokenType("}")
    object OpenBracket : SizedTokenType("[")
    object CloseBracket : SizedTokenType("]")
    object OpenParenthesis : SizedTokenType("(")
    object CloseParenthesis : SizedTokenType(")")
    object Colon : SizedTokenType(":")
    object DoubleColon : SizedTokenType("::")
    object ColonEqual : SizedTokenType(":=")
    object SemiColon : SizedTokenType(";")
    object Comma : SizedTokenType(",")
    object Dot : SizedTokenType(".")
    object Bang : SizedTokenType("!")
    object Equal : SizedTokenType("=")
    object DoubleEqual : SizedTokenType("==")
    object BangEqual : SizedTokenType("!=")
    object Greater : SizedTokenType(">")
    object GreaterEqual : SizedTokenType(">=")
    object Lesser : SizedTokenType("<")
    object DoubleLesser : SizedTokenType("<<")
    object LesserEqual : SizedTokenType("<=")
    object Pipe : SizedTokenType("|")
    object DoublePipe : SizedTokenType("||")
    object Ampersand : SizedTokenType("&")
    object DoubleAmpersand : SizedTokenType("&&")
    object Hat : SizedTokenType("^")
    object Tilde : SizedTokenType("~")
    object Plus : SizedTokenType("+")
    object DoublePlus : SizedTokenType("++")
    object Minus : SizedTokenType("-")
    object DoubleMinus : SizedTokenType("--")
    object Arrow : SizedTokenType("->")
    object Star : SizedTokenType("*")
    object Slash : SizedTokenType("/")
    object Percentage : SizedTokenType("%")
}