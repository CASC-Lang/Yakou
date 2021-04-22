package io.github.chaosunity.casc.parsing.`class`

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.{Expression, FunctionParameter}
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.Statement

class Function(private var _scope: Scope,
               private val _name: String,
               retType: Type,
               private val _params: List[FunctionParameter],
               private val _statements: List[Statement]) extends Expression(retType) {
    def scope: Scope = _scope

    def name: String = _name

    def parameters: List[FunctionParameter] = _params

    def statements: List[Statement] = _statements
}
