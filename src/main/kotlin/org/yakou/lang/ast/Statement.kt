package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.Variable

sealed class Statement {
    abstract val span: Span

    class VariableDeclaration(
        val let: Token,
        val mut: Token?,
        val name: Token,
        val colon: Token?,
        val specifiedType: Path.SimplePath?,
        val equal: Token,
        var expression: Expression,
    ) : Statement() {
        override val span: Span by lazy {
            let.span.expand(expression.span)
        }

        lateinit var variableInstance: Variable
        var ignore: Boolean = name.literal == "_"
    }

    class Return(val `return`: Token, var expression: Expression): Statement() {
        override val span: Span by lazy {
            `return`.span.expand(expression.span)
        }
    }

    class ExpressionStatement(var expression: Expression): Statement() {
        override val span: Span = expression.span
    }
}