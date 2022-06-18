package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.Fn
import org.yakou.lang.bind.TypeInfo

sealed class Item {
    data class Package(
        val pkg: Token,
        val identifier: Token,
        val openBrace: Token?,
        val items: List<Item>?,
        val closeBrace: Token?
    ) : Item()

    data class Function(
        val modifiers: Modifiers,
        val fn: Token,
        val name: Token,
        val openParenthesis: Token,
        val parameters: List<Parameter>,
        val closeParenthesis: Token,
        val arrow: Token?,
        val returnType: Type?,
        val body: FunctionBody?,
    ) : Item() {
        val span: Span by lazy {
            var finalSpan =
                if (!modifiers.isEmpty()) modifiers.modifierMap.toList().first().second
                else fn.span

            finalSpan = if (body != null) {
                when (body) {
                    is FunctionBody.BlockExpression -> finalSpan.expand(body.closeBrace.span)
                    is FunctionBody.SingleExpression -> finalSpan.expand(body.expression.span)
                }
            } else if (returnType != null) {
                finalSpan.expand(returnType.span)
            } else {
                finalSpan.expand(closeParenthesis.span)
            }

            finalSpan
        }
        lateinit var returnTypeInfo: TypeInfo
        lateinit var functionInstance: Fn
    }
}
