package org.casc.lang.ast

data class Token(var literal: String, val type: TokenType, val pos: Position) {
    constructor(literal: Char, type: TokenType, pos: Position): this(literal.toString(), type, pos)

    fun isClassKeyword(): Boolean =
        literal == "class"

    fun isImplKeyword(): Boolean =
        literal == "impl"

    fun isCompKeyword(): Boolean =
        literal == "comp"

    fun isMutKeyword(): Boolean =
        literal == "mut"

    fun isReturnKeyword(): Boolean =
        literal == "return"

    fun isAccessorKeyword(): Boolean =
        Accessor.validKeywords.contains(literal)

    fun isFnKeyword(): Boolean =
        literal == "fn"
}
