package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.parsing.type.Type
import io.github.chaosunity.casc.util.ReflectionMapper

class ClassPathScope {
    fun getMethodSignature(owner: Type, methodName: String, arguments: List<Type>): FunctionSignature? =
        try {
            val (methodOwnerClass, params) = getExecutableInfo(owner, arguments)
            val method = methodOwnerClass.getMethod(methodName, *params)

            ReflectionMapper.fromMethod(method)
        } catch (e: Exception) {
            null
        }

    fun getConstructorSignature(className: String, arguments: List<Type>): FunctionSignature? =
        try {
            val (methodOwnerClass, params) = getExecutableInfo(ClassType(className), arguments)
            val constructor = methodOwnerClass.getConstructor(*params)

            ReflectionMapper.fromConstructor(constructor)
        } catch (e: Exception) {
            null
        }

    private fun getExecutableInfo(owner: Type, arguments: List<Type>): Pair<Class<*>, Array<Class<*>?>> =
        owner.classType()!! to arguments.map(Type::classType).toTypedArray()
}