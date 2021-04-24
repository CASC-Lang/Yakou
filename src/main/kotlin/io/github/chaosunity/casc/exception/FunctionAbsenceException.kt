package io.github.chaosunity.casc.exception

import io.github.chaosunity.casc.parsing.expression.FunctionCall

class FunctionAbsenceException(functionCall: FunctionCall) : CompilationException("Function '${functionCall.functionName()}' does not exist.") {
}