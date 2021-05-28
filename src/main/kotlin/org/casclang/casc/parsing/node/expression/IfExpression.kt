package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.Type

data class IfExpression(
    val condition: Expression<*>,
    val trueExpression: Expression<*>,
    val falseExpression: Expression<*>
) : Expression<IfExpression> {
    override val type: Type
        get() = if (trueExpression.type == falseExpression.type) trueExpression.type else
            throw RuntimeException("Type unmatched for type '${trueExpression.type.internalName}' and type '${falseExpression.type.internalName}'")
}