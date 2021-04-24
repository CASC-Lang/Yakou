package io.github.chaosunity.casc.parsing.`class`

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.{Expression, FunctionParameter}
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.Statement

import java.util

class Function(private var _scope: Scope,
               private val _name: String,
               retType: Type,
               private val _params: util.List[FunctionParameter],
               private val _statements: util.List[Statement]) extends Expression(retType) {
    def scope: Scope = _scope

    def name: String = _name

    def parameters: util.List[FunctionParameter] = _params

    def statements: util.List[Statement] = _statements
}
