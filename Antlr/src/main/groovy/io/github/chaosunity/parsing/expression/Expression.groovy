package io.github.chaosunity.parsing.expression

import io.github.chaosunity.parsing.statement.Statement
import io.github.chaosunity.parsing.type.Type

abstract class Expression implements Statement {
    private Type type

    Expression(Type type) {
        this.type = type
    }

    Type getType() {
        return type
    }
}
