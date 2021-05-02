package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.Value
import io.github.chaosunity.casc.util.TypeResolver

class ValueVisitor : CASCBaseVisitor<Value>() {
    override fun visitValue(ctx: CASCParser.ValueContext): Value {
        val value = ctx.text
        val type = TypeResolver.getTypeByValue(value)

        return Value(type, value)
    }
}