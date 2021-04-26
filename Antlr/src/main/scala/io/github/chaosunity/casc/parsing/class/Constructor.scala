package io.github.chaosunity.casc.parsing.`class`

import io.github.chaosunity.casc.parsing.`type`.{BuiltInType, Type}
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.statement.Statement

class Constructor(signature: FunctionSignature, block: Statement) extends Function(signature, block) {
    override def returnType: Type = BuiltInType.VOID
}
