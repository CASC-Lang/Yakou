package org.casc.lang.ast

data class Token(var literal: String, val type: TokenType, val pos: Position) {
    constructor(literal: Char, type: TokenType, pos: Position): this(literal.toString(), type, pos)

    companion object {
        val keywords = arrayOf(
            "package", "use", "class", "impl", "comp", "pub", "prot", "intl", "priv",
            "mut", "fn", "if", "else", "for", "return", "as"
        )
    }

    fun isPackageKeyword(): Boolean =
        literal == "package"

    fun isUseKeyword(): Boolean =
        literal == "use"

    fun isClassKeyword(): Boolean =
        literal == "class"

    fun isImplKeyword(): Boolean =
        literal == "impl"

    fun isCompKeyword(): Boolean =
        literal == "comp"

    fun isMutKeyword(): Boolean =
        literal == "mut"

    fun isIfKeyword(): Boolean =
        literal == "if"

    fun isElseKeyword(): Boolean =
        literal == "else"

    fun isForKeyword(): Boolean =
        literal == "for"

    fun isReturnKeyword(): Boolean =
        literal == "return"

    fun isFnKeyword(): Boolean =
        literal == "fn"

    fun isAsKeyword(): Boolean =
        literal == "as"

    fun isAccessorKeyword(): Boolean =
        Accessor.validKeywords.contains(literal)
}
