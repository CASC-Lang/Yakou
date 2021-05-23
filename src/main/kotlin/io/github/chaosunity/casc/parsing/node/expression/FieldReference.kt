package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.scope.Field
import io.github.chaosunity.casc.parsing.type.Type

data class FieldReference(val field: Field, override val dimensions: List<Expression<*>> = listOf()) : Reference<FieldReference> {
    override val type: Type = field.type
    override val name: String = field.name
}