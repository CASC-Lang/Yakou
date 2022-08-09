package org.yakou.lang.ast

import chaos.unity.nenggao.Span

class GenericParameters(val lesser: Token, val parameters: List<GenericParameter>, val larger: Token): AstNode {
    override val span: Span by lazy {
        lesser.span.expand(larger.span)
    }

    class GenericParameter(val identifier: Token): AstNode { // TODO: Constraints
        override val span: Span by lazy {
            identifier.span
        }
    }
}