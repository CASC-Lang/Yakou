package io.github.chaosunity.parsing.scope

import io.github.chaosunity.parsing.global.MetaData

class Scope {
    private List<FunctionSignature> functionSignatures = []
    private List<LocalVariable> localVariables = []
    private final MetaData metaData

    Scope(MetaData metaData) {
        this.metaData = metaData
    }

    Scope(Scope scope) {
        metaData = scope.metaData
        functionSignatures = new ArrayList(scope.functionSignatures)
    }

    void addSignature(FunctionSignature signature) {
        functionSignatures += signature
    }

    FunctionSignature getSignature(String methodName) {
        return functionSignatures.stream()
                .filter(signature -> signature.getName() == methodName)
                .findFirst()
                .orElseThrow()
    }

    void addLocalVariable(LocalVariable variable) {
        localVariables += variable
    }

    LocalVariable getVariable(String variableName) {
        return localVariables.stream()
                .filter(variable -> variable.getName() == variableName)
                .findFirst()
                .orElseThrow()
    }

    int getVariableIndex(String variableName) {
        def variable = getVariable(variableName)
        return localVariables.indexOf(variableName)
    }

    String getClassName() {
        return metaData.getClassName()
    }
}
