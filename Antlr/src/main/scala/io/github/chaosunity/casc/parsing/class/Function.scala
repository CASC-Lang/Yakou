package io.github.chaosunity.casc.parsing.`class`

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.FunctionParameter
import io.github.chaosunity.casc.parsing.statement.Statement

import java.util

class Function(private val _name: String,
               private val _retType: Type,
               private val _params: util.List[FunctionParameter],
               private val _rootStatement: Statement) {
    def name: String = _name

    def returnType: Type = _retType

    def parameters: util.List[FunctionParameter] = _params

    def rootStatement: Statement = _rootStatement
}
