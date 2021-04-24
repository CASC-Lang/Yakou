package io.github.chaosunity.casc.parsing.statement

import io.github.chaosunity.casc.parsing.scope.Scope

import java.util

class BlockStatement(private val _scope: Scope,
                     private val _statements: util.List[Statement]) extends Statement {
    def scope: Scope = _scope

    def statements: util.List[Statement] = _statements
}
