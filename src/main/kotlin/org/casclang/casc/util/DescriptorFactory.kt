package org.casclang.casc.util

import org.casclang.casc.parsing.Function
import org.casclang.casc.parsing.node.expression.Argument
import org.casclang.casc.parsing.node.expression.Parameter
import org.casclang.casc.parsing.scope.FunctionSignature
import org.casclang.casc.parsing.type.Type

object DescriptorFactory {
    fun getMethodDescriptorWithParameter(function: Function<*>): String =
        getMethodDescriptorWithParameter(function.signature)

    fun getMethodDescriptorWithParameter(signature: FunctionSignature): String =
        getMethodDescriptorWithParameter(signature.parameters, signature.returnType)

    fun getMethodDescriptorWithParameter(parameters: List<Parameter>, returnType: Type): String {
        val parameterDescriptors = parameters.map(Parameter::type).map(Type::descriptor).joinToString("", "(", ")")
        val returnDescriptor = returnType.descriptor

        return "$parameterDescriptors$returnDescriptor"
    }

    fun getMethodDescriptorWithArgument(argument: List<Argument>, returnType: Type): String {
        val argumentDescriptors = argument.map(Argument::type).map(Type::descriptor).joinToString("", "(", ")")
        val returnDescriptor = returnType.descriptor

        return "$argumentDescriptors$returnDescriptor"
    }
}