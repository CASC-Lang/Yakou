package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.type.Type

data class LocalVariableReference(val localVariable: LocalVariable) : Reference<LocalVariableReference> {
    override val name: String = localVariable.name
    override val type: Type = localVariable.type
}