package io.github.chaosunity.casc.exception

class FunctionNameSameAsClassNameException(functionName: String) :
    RuntimeException("Function name cannot be same as class name : Duplicated name '$functionName'")