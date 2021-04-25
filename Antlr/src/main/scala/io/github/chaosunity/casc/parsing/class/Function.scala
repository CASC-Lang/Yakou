package io.github.chaosunity.casc.parsing.`class`

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.FunctionParameter
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.statement.Statement

import java.util

class Function(val signature: FunctionSignature,
               val rootStatement: Statement) {
    def name: String = signature.name

    def parameters: util.List[FunctionParameter] = signature.parameters

    def returnType: Type = signature.returnType
}
