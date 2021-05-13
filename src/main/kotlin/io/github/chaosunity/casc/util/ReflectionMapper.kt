package io.github.chaosunity.casc.util

import io.github.chaosunity.casc.parsing.node.expression.Parameter
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.ClassType
import java.lang.reflect.Constructor
import java.lang.reflect.Executable
import java.lang.reflect.Method

object ReflectionMapper {
    fun fromMethod(method: Method): FunctionSignature {
        val (name, parameters) = fromExecutable(method)
        val returnType = method.returnType

        return FunctionSignature(name, parameters, ClassType(returnType.canonicalName))
    }

    fun fromConstructor(constructor: Constructor<*>): FunctionSignature {
        val (name, parameters) = fromExecutable(constructor)

        return FunctionSignature(name, parameters, BuiltInType.VOID)
    }

    private fun fromExecutable(executable: Executable): Pair<String, List<Parameter>> =
        executable.name to executable.parameters.map {
            Parameter(
                TypeResolver.getTypeByName(it.type.canonicalName),
                it.name,
                null
            )
        }
}