package io.github.chaosunity.casc.exceptions

import io.github.chaosunity.casc.parsing.expression.FunctionParameter

import java.util

class ParameterForNameNotFoundException(name: String, parameters: util.List[FunctionParameter]) extends RuntimeException() {
}
