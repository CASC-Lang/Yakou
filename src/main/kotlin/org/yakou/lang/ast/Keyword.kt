package org.yakou.lang.ast

enum class Keyword(val literal: String) {
    PUB("pub"),
    INTL("intl"),
    PROT("prot"),
    PRIV("priv"),
    CLASS("class"),
    IMPL("impl"),
    COMP("comp"),
    FN("impl"),
    SELF("self");

    companion object {
        val keywords: Array<Keyword> = values()

        fun isKeyword(literal: String): Boolean =
            keywords.any { it.literal == literal }
    }
}