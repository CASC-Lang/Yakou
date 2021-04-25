package io.github.chaosunity.casc.parsing.`class`

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.FunctionParameter
import io.github.chaosunity.casc.parsing.statement.Statement

import java.util

class Function(val name: String,
               val returnType: Type,
               val parameters: util.List[FunctionParameter],
               val rootStatement: Statement) {
}
