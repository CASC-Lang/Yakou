package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.parsing.Immutable
import io.github.chaosunity.casc.parsing.type.Type
import io.github.chaosunity.casc.parsing.type.Variable

data class LocalVariable(override val name: String, override var type: Type, override val immutable: Boolean = true) : Variable, Immutable