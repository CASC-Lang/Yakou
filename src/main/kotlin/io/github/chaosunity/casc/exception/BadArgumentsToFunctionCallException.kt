package io.github.chaosunity.casc.exception

import io.github.chaosunity.casc.parsing.expression.Call

class BadArgumentsToFunctionCallException(functionCall: Call) : RuntimeException()