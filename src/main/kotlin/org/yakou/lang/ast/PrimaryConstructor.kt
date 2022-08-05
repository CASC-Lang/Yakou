package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.ClassMember

data class PrimaryConstructor(
    val modifiers: Modifiers,
    val openParenthesis: Token,
    val self: Token?,
    val selfComma: Token?,
    val parameters: List<ConstructorParameter>,
    val closeParenthesis: Token
) {
    val span: Span by lazy {
        (modifiers.span ?: openParenthesis.span).expand(closeParenthesis.span)
    }

    lateinit var constructorInstance: ClassMember.Constructor

    class ConstructorParameter(val modifiers: Modifiers, name: Token, colon: Token, type: Type): Parameter(name, colon, type)
}