package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.Value
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.util.TypeResolver

class ValueVisitor : CASCBaseVisitor<Value>() {
    override fun visitValue(ctx: CASCParser.ValueContext): Value {
        var value = ctx.text
        val type = TypeResolver.getTypeByValue(value)

        if (type == BuiltInType.LONG) value = value.removeSuffix("L").removeSuffix("l")
        if (type == BuiltInType.FLOAT) value = value.removeSuffix("F").removeSuffix("f")

        return Value(type, value)
    }
}