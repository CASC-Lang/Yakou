package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.Value
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.util.TypeResolver

class ValueVisitor : CASCBaseVisitor<Value>() {
    override fun visitValue(ctx: CASCParser.ValueContext): Value {
        var value = ctx.text.removeSurrounding("\"")
        val type = TypeResolver.getTypeByValue(value)

        if (type == BuiltInType.LONG) value = value.removeSuffix("L").removeSuffix("l")
        if (type == BuiltInType.FLOAT) value = value.removeSuffix("F").removeSuffix("f")

        return Value(type as BuiltInType, value)
    }
}