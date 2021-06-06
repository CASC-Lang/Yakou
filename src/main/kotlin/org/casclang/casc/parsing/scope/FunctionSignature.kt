package org.casclang.casc.parsing.scope

import org.casclang.casc.parsing.node.expression.Argument
import org.casclang.casc.parsing.node.expression.Parameter
import org.casclang.casc.parsing.type.Type

data class FunctionSignature(
    val name: String,
    val parameters: List<Parameter>,
    val returnType: Type,
    val static: Boolean
) {
    fun getParameterByName(name: String): Parameter =
        parameters.find { it.name == name }
            ?: throw RuntimeException("Function '${this.name}' has no parameter named '$name'")

    fun getIndexOfParameter(parameterName: String): Int =
        parameters.indexOf(getParameterByName(parameterName))

    fun matches(signature: FunctionSignature) =
        when {
            signature.name != name -> false
            signature.parameters.size != parameters.size -> false
            else -> (signature.parameters.indices).all {
                signature.parameters[it].type == parameters[it].type
            }
        }

    fun matches(signatureName: String, arguments: List<Argument>): Boolean {
        if (name != signatureName) return false

        val nonDefaultParameterCount = parameters.filter { it.defaultValue == null }.count()

        if (nonDefaultParameterCount > arguments.size) return false

        if (arguments.any { it.argumentName != null }) {
            return arguments.all {
                val parameterName = it.argumentName!!
                return parameters.map(Parameter::name).any(parameterName::equals)
            }
        }

        if (parameters.isEmpty() && arguments.size > parameters.size) return false

        return (arguments.indices).all {
            arguments[it].type == parameters[it].type
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val (name1, parameters1, returnType1) = other as FunctionSignature
        if (name != name1) return false
        return if (parameters != parameters1) false else returnType == returnType1
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + parameters.hashCode()
        result = 31 * result + returnType.hashCode()
        return result
    }
}