package org.yakou.lang.ast

enum class Keyword(val literal: String) {
    PUB("pub"),
    PROT("prot"),
    PRIV("priv"),
    PKG("pkg"),
    MUT("mut"),
    CLASS("class"),
    IMPL("impl"),
    COMP("comp"),
    FN("fn"),
    SELF("self");

    companion object {
        val keywords: Array<Keyword> = values()

        fun isKeyword(literal: String): Boolean =
            keywords.any { it.literal == literal }
    }
}