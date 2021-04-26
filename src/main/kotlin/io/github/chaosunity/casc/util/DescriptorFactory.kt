package io.github.chaosunity.casc.util

import io.github.chaosunity.casc.parsing.`class`.Function
import io.github.chaosunity.casc.parsing.expression.FunctionParameter
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.type.Type

object DescriptorFactory {
    fun getMethodDescriptor(function: Function): String {
        val arguments = function.parameters()
        val returnType = function.returnType()

        return getMethodDescriptor(arguments, returnType)
    }

    fun getMethodDescriptor(functionSignature: FunctionSignature): String {
        val arguments = functionSignature.parameters()
        val returnType = functionSignature.returnType()

        return getMethodDescriptor(arguments, returnType)
    }

    private fun getMethodDescriptor(arguments: List<FunctionParameter>, returnType: Type): String {
        val argumentDescriptor = arguments.joinToString("", "(", ")") {
            it.type().descriptor()
        }
        val returnTypeDescriptor = returnType.descriptor()

        return "$argumentDescriptor$returnTypeDescriptor"
    }
}