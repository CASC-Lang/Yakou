package io.github.chaosunity.parsing.scope

import io.github.chaosunity.parsing.expression.Expression
import io.github.chaosunity.parsing.type.Type

class LocalVariable extends Expression {
    private final String name

    LocalVariable(Type type, String name) {
        super(type)

        this.name = name
    }

    String getName() {
        return name
    }
}
