package io.github.chaosunity.casc.parsing.statement

import io.github.chaosunity.casc.parsing.scope.Scope

import java.util

class BlockStatement(val scope: Scope,
                     val statements: util.List[Statement]) extends Statement {
}
