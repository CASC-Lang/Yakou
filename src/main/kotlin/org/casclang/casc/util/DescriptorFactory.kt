package org.casclang.casc.util

import org.casclang.casc.parsing.Function
import org.casclang.casc.parsing.node.expression.Parameter
import org.casclang.casc.parsing.scope.FunctionSignature
import org.casclang.casc.parsing.type.Type

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