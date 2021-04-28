package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.exceptions.ParameterForNameNotFoundException
import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.expression.FunctionParameter

import java.util
import scala.jdk.CollectionConverters.ListHasAsScala

class FunctionSignature(val name: String,
                        val parameters: util.List[FunctionParameter],
                        val returnType: Type) {
    def getParameterForName(name: String): FunctionParameter =
        parameters.stream()
            .filter(_.name.equals(name))
            .findFirst()
            .orElseThrow(() => new ParameterForNameNotFoundException(name, parameters))

    def matches(signatureName: String, signatureParamTypes: util.List[Type]): Boolean = {
        if (!name.equals(signatureName)) return false

        val p1 = parameters.asScala.map(_.`type`).toSet
        val p2 = signatureParamTypes.asScala.toSet

        (p1 subsetOf p2) && (p2 subsetOf p1)
    }

    def getIndexOfParameter(parameterName: String): Int =
        parameters.indexOf(getParameterForName(parameterName))
}
