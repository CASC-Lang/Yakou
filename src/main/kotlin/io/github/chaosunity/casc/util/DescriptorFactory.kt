package io.github.chaosunity.casc.util

import io.github.chaosunity.casc.parsing.Function
import io.github.chaosunity.casc.parsing.node.expression.Parameter
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.type.Type

object DescriptorFactory {
    fun getMethodDescriptor(function: Function<*>): String =
        getMethodDescriptor(function.signature)

    fun getMethodDescriptor(signature: FunctionSignature): String =
        getMethodDescriptor(signature.parameters, signature.returnType)

    private fun getMethodDescriptor(parameters: List<Parameter>, returnType: Type): String {
        val parameterDescriptors = parameters.map(Parameter::type).map(Type::descriptor).joinToString("", "(", ")")
        val returnDescriptor = returnType.descriptor

        return "$parameterDescriptors$returnDescriptor"
    }
}