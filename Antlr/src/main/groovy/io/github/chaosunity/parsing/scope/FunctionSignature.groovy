package io.github.chaosunity.parsing.scope

import io.github.chaosunity.parsing.expression.FunctionParameter
import io.github.chaosunity.parsing.type.Type

class FunctionSignature {
    private final String name
    private final List<FunctionParameter> parameters
    private final Type returnType

    FunctionSignature(String name, List<FunctionParameter> parameters, Type returnType) {
        this.returnType = returnType
        this.parameters = parameters
        this.name = name
    }

    String getName() {
        return name
    }

    List<FunctionParameter> getParameters() {
        return Collections.unmodifiableList(parameters)
    }

    Type getReturnType() {
        return returnType
    }
}
