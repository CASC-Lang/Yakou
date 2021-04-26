package io.github.chaosunity.casc.parsing.statement

import com.google.common.collect.Lists
import io.github.chaosunity.casc.parsing.scope.Scope

import java.util

class Block(val scope: Scope,
            val statements: util.List[Statement]) extends Statement {
}

object Block {
    def empty(scope: Scope): Block =
        new Block(scope, Lists.newArrayList())
}