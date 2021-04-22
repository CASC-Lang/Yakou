package io.github.chaosunity.parsing.expression

import io.github.chaosunity.parsing.type.Type

class Value extends Expression {
    private String value

    Value(Type type, String value) {
        super(type)

        this.value = value
    }

    String getValue() {
        return value
    }
}
