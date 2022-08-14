package org.yakou.lang.ast

import chaos.unity.nenggao.Span

typealias GenericParameter = Type

data class GenericParameters(
    val openBracket: Token,
    val genericParameters: List<GenericParameter>,
    val closeBracket: Token
): AstNode {
    override val span: Span by lazy {
        openBracket.span.expand(closeBracket.span)
    }
}
