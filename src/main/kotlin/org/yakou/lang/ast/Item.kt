package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.ClassMember
import org.yakou.lang.bind.TypeInfo

sealed interface Item : AstNode {
    abstract override val span: Span
}
