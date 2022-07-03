package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.ClassMember
import org.yakou.lang.bind.TypeInfo

sealed class Item {
    abstract val span: Span

    data class Package(
        val pkg: Token,
        val identifier: Token,
        val openBrace: Token?,
        val items: List<Item>?,
        val closeBrace: Token?
    ) : Item() {
        override val span: Span by lazy {
            var finalSpan = pkg.span

            finalSpan =
                if (closeBrace != null) finalSpan.expand(closeBrace.span)
                else finalSpan.expand(identifier.span)

            finalSpan
        }
    }

    data class Const(
        val modifiers: Modifiers,
        val const: Token,
        val identifier: Token,
        val colon: Token,
        val type: Type,
        val equal: Token,
        var expression: Expression
    ) : Item() {
        override val span: Span by lazy {
            var finalSpan =
                if (!modifiers.isEmpty()) modifiers.modifierMap.toList().first().second
                else const.span

            finalSpan = finalSpan.expand(expression.span)

            finalSpan
        }
        lateinit var typeInfo: TypeInfo
        lateinit var fieldInstance: ClassMember.Field
    }

    data class StaticField(
        val modifiers: Modifiers,
        val static: Token,
        val identifier: Token,
        val colon: Token,
        val explicitType: Type,
        val equal: Token,
        var expression: Expression
    ) : Item() {
        override val span: Span by lazy {
            var finalSpan =
                if (!modifiers.isEmpty()) modifiers.modifierMap.toList().first().second
                else static.span

            finalSpan = finalSpan.expand(expression.span)

            finalSpan
        }
        lateinit var typeInfo: TypeInfo
        lateinit var fieldInstance: ClassMember.Field
    }

    data class Class(
        val modifiers: Modifiers,
        val `class`: Token,
        val identifier: Token,
        val openBrace: Token?,
        val classItems: List<ClassItem>?,
        val closeBrace: Token?
    ) : Item() {
        override val span: Span by lazy {
            var finalSpan = `class`.span

            finalSpan =
                if (closeBrace != null) finalSpan.expand(closeBrace.span)
                else finalSpan.expand(identifier.span)

            finalSpan
        }
    }

    data class Function(
        val modifiers: Modifiers,
        val fn: Token,
        val identifier: Token,
        val openParenthesis: Token,
        val parameters: List<Parameter>,
        val closeParenthesis: Token,
        val arrow: Token?,
        val returnType: Type?,
        val body: FunctionBody?,
    ) : Item() {
        override val span: Span by lazy {
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
        lateinit var functionInstance: ClassMember.Fn
    }
}
