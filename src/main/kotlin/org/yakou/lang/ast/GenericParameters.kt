package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.TypeInfo

class GenericParameters(val lesser: Token, val parameters: List<GenericParameter>, val greater: Token): AstNode {
    override val span: Span by lazy {
        lesser.span.expand(greater.span)
    }

    class GenericParameter(val identifier: Token): AstNode { // TODO: Constraints
        override val span: Span by lazy {
            identifier.span
        }

        val genericConstraint: TypeInfo.GenericConstraint = TypeInfo.GenericConstraint(identifier.literal)
    }
}