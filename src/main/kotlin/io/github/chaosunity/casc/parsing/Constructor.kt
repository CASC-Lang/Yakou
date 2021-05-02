package io.github.chaosunity.casc.parsing

import io.github.chaosunity.casc.parsing.node.expression.EmptyExpression
import io.github.chaosunity.casc.parsing.node.statement.Block
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.Type

class Constructor(signature: FunctionSignature, block: Block?) :
    Function<Constructor>(signature, block ?: EmptyExpression(BuiltInType.VOID)) {
    override val returnType: Type = BuiltInType.VOID
}
