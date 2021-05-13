package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.node.statement.Statement
import io.github.chaosunity.casc.parsing.type.Type

interface Expression<T> : Statement<Expression<T>> where T : Expression<T> {
    val type: Type

    fun accept(factory: ExpressionFactory) = factory.generate(this)

    fun isInt(): Boolean =
        type.isInt()

    fun isFloat(): Boolean =
        type.isFloat()

    fun isDouble(): Boolean =
        type.isDouble()

    fun isBool(): Boolean =
        type.isBool()

    fun isString(): Boolean =
        type.isString()
}