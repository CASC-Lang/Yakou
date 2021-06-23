package org.casclang.casc.parsing.node.expression

import org.casclang.casc.bytecode.expression.ExpressionFactory
import org.casclang.casc.parsing.node.statement.Statement
import org.casclang.casc.parsing.type.Type

interface Expression<T> : Statement<Expression<T>> where T : Expression<T> {
    val type: Type

    fun accept(factory: ExpressionFactory) = factory.generate(this)

    fun isInt(): Boolean =
        type.isInt()

    fun isLong(): Boolean =
        type.isLong()

    fun isFloat(): Boolean =
        type.isFloat()

    fun isDouble(): Boolean =
        type.isDouble()

    fun isBool(): Boolean =
        type.isBool()

    fun isString(): Boolean =
        type.isString()
}