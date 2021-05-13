package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.bytecode.expression.FieldFactory
import io.github.chaosunity.casc.parsing.type.Type
import io.github.chaosunity.casc.parsing.type.Variable

data class Field(val ownerType: Type, override val name: String, override val type: Type) : Variable {
    fun generate(factory: FieldFactory) =
        factory.generate(this)
}