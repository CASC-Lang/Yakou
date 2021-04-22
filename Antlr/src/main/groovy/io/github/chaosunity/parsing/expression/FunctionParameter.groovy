package io.github.chaosunity.parsing.expression

import io.github.chaosunity.parsing.type.Type

class FunctionParameter extends Expression {
    private final String name

    FunctionParameter(Type type, String name) {
        super(type)

        this.name = name
    }

    String getName() {
        return name
    }
}
