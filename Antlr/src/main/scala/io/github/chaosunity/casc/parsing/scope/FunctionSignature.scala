package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.exceptions.ParameterForNameNotFoundException
import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.FunctionParameter

import java.util

class FunctionSignature(val name: String,
                        val parameters: util.List[FunctionParameter],
                        val returnType: Type) {
    def getParameterForName(name: String): FunctionParameter =
        parameters.stream()
            .filter(_.name.equals(name))
            .findFirst()
            .orElseThrow(() => new ParameterForNameNotFoundException(name, parameters))

    def getIndexOfParameter(parameterName: String): Int =
        parameters.indexOf(getParameterForName(parameterName))
}
