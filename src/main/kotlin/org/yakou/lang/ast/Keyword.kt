package org.yakou.lang.ast

enum class Keyword(val literal: String) {
    PUB("pub"),
    PROT("prot"),
    PRIV("priv"),
    PKG("pkg"),
    MUT("mut"),
    INLINE("inline"),
    CLASS("class"),
    IMPL("impl"),
    CONST("const"),
    STATIC("static"),
    FN("fn"),
    SELF("self"),
    LET("let"),
    AS("as"),
    TRUE("true"),
    FALSE("false"),
    RETURN("return"),
    FOR("for");

    companion object {
        val keywords: Array<Keyword> = values()

        fun isKeyword(literal: String): Boolean =
            keywords.any { it.literal == literal }
    }
}