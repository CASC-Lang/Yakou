package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.scope.Field
import org.casclang.casc.parsing.type.Type

data class FieldReference(val field: Field) : Reference<FieldReference> {
    override val type: Type = field.type
    override val name: String = field.name
}