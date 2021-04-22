package io.github.chaosunity.parsing.expression

import io.github.chaosunity.parsing.type.Type

class VarReference extends Expression {
    private final String variableName

    VarReference(Type type, String variableName) {
        super(type)

        this.variableName = variableName
    }

    String getVariableName() {
        return variableName
    }
}
