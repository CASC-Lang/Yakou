package org.yakou.lang.ast

enum class Keyword(val literal: String) {
    PUB("pub"),
    PROT("prot"),
    PRIV("priv"),
    PKG("pkg"),
    MUT("mut"),
    INLINE("inline"),
    INNER("inner"),
    CLASS("class"),
    IMPL("impl"),
    CONST("const"),
    STATIC("static"),
    FN("fn"),
    SELF_VALUE("self"),
    SELF_TYPE("Self"),
    LET("let"),
    AS("as"),
    NEW("new"),
    TRUE("true"),
    FALSE("false"),
    RETURN("return"),
    FOR("for");

    companion object {
        private val keywords: Array<Keyword> = values()

        fun isKeyword(literal: String): Boolean =
            keywords.any { it.literal == literal }
    }
}
