package org.casclang.casc.parsing.scope

import org.casclang.casc.parsing.Immutable
import org.casclang.casc.parsing.type.Type
import org.casclang.casc.parsing.type.Variable

data class LocalVariable(
    override val name: String, override var type: Type,
    override val immutable: Boolean = true
) : Variable, Immutable