package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.scope.LocalVariable
import org.casclang.casc.parsing.type.Type

data class LocalVariableReference(val localVariable: LocalVariable) : Reference<LocalVariableReference> {
    override val name: String = localVariable.name
    override val type: Type = localVariable.type
}