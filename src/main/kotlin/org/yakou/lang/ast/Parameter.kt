package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.TypeInfo

open class Parameter(val name: Token, val colon: Token, val type: Type) : AstNode {
    override val span: Span by lazy {
        name.span.expand(type.span)
    }

    lateinit var typeInfo: TypeInfo
}
