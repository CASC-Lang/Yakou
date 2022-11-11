package org.yakou.lang.ast

import chaos.unity.nenggao.Span
import org.yakou.lang.bind.Variable

sealed interface Statement : AstNode {
    abstract override val span: Span
}

class VariableDeclaration(
    val let: Token,
    val mut: Token?,
    val name: Token,
    val colon: Token?,
    val specifiedType: Path.SimplePath?,
    val equal: Token,
    var expression: Expression
) : Statement {
    override val span: Span by lazy {
        let.span.expand(expression.span)
    }

    lateinit var variableInstance: Variable
    var ignore: Boolean = name.literal == "_"
}

class For(
    val `for`: Token,
    var conditionExpression: Expression,
    val block: Block
) : Statement {
    override val span: Span by lazy {
        `for`.span.expand(block.span)
    }
}

class Block(
    val openBrace: Token,
    val statements: List<Statement>,
    val closeBrace: Token
) : Statement {
    override val span: Span by lazy {
        openBrace.span.expand(closeBrace.span)
    }
}

class Return(val `return`: Token, var expression: Expression) : Statement {
    override val span: Span by lazy {
        `return`.span.expand(expression.span)
    }
}

class ExpressionStatement(var expression: Expression) : Statement {
    override val span: Span = expression.span
}
