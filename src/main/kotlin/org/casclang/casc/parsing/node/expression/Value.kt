package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.BuiltInType

data class Value(override val type: BuiltInType, val value: String) : FoldableExpression<Value>(type) {
    fun evaluate(targetType: BuiltInType = type): Number =
        when (targetType) {
            BuiltInType.DOUBLE -> value.toDouble()
            BuiltInType.FLOAT -> value.toFloat()
            BuiltInType.LONG -> value.toLong()
            BuiltInType.INT -> value.toInt()
            else -> throw IllegalArgumentException()
        }
}
