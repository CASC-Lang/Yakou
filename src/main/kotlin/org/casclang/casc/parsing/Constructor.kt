package org.casclang.casc.parsing

import org.casclang.casc.parsing.node.expression.EmptyExpression
import org.casclang.casc.parsing.node.statement.Block
import org.casclang.casc.parsing.scope.AccessModifier
import org.casclang.casc.parsing.scope.FunctionSignature
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.Type

class Constructor(
    signature: FunctionSignature,
    block: Block?,
    accessModifier: AccessModifier,
    val isPrimary: Boolean = false
) : Function<Constructor>(signature, block ?: EmptyExpression(BuiltInType.VOID), accessModifier) {
    override val returnType: Type = BuiltInType.VOID
}
