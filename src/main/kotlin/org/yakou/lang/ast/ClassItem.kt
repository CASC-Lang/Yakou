package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.ClassMember
import org.yakou.lang.bind.TypeInfo

sealed interface ClassItem : AstNode {
    abstract override val span: Span
}

data class Field(
    val modifiers: Modifiers,
    val identifier: Token,
    val colon: Token,
    val explicitType: Type,
    val equal: Token?,
    var expression: Expression?
) : ClassItem {
    override val span: Span by lazy {
        var finalSpan =
            if (!modifiers.isEmpty()) {
                modifiers.toList().first().second
            } else {
                identifier.span
            }

        finalSpan =
            if (equal != null && expression != null) {
                finalSpan.expand(expression!!.span)
            } else {
                finalSpan.expand(explicitType.span)
            }

        finalSpan
    }
    lateinit var typeInfo: TypeInfo
    lateinit var fieldInstance: ClassMember.Field
}
