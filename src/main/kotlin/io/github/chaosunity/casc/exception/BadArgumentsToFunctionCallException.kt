package io.github.chaosunity.casc.exception

import io.github.chaosunity.casc.parsing.expression.FunctionCall

class BadArgumentsToFunctionCallException(functionCall: FunctionCall) : RuntimeException()