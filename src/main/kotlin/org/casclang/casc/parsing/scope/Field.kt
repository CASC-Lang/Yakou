package org.casclang.casc.parsing.scope

import org.casclang.casc.bytecode.FieldFactory
import org.casclang.casc.parsing.Immutable
import org.casclang.casc.parsing.type.Type
import org.casclang.casc.parsing.type.Variable

data class Field(
    override val immutable: Boolean,
    val ownerType: Type,
    override val name: String,
    override val type: Type,
    val accessModifier: AccessModifier,
    val initialized: Boolean = false,
    val static: Boolean = false
) : Variable, Immutable {
    fun generate(factory: FieldFactory) =
        factory.generate(this)
}