package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.bytecode.FieldFactory
import io.github.chaosunity.casc.parsing.Immutable
import io.github.chaosunity.casc.parsing.type.Type
import io.github.chaosunity.casc.parsing.type.Variable

data class Field(
    val accessModifier: AccessModifier,
    override val immutable: Boolean,
    val ownerType: Type,
    override val name: String,
    override val type: Type
) : Variable, Immutable {
    fun generate(factory: FieldFactory) =
        factory.generate(this)
}