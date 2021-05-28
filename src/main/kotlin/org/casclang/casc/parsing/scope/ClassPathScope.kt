package org.casclang.casc.parsing.scope

import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.parsing.type.Type
import org.casclang.casc.util.ReflectionMapper
import org.casclang.casc.util.TypeResolver
import java.lang.reflect.Modifier

object ClassPathScope {
    fun getField(owner: Type, fieldName: String): Field? =
        try {
            val field = owner.classType()!!.getField(fieldName)
            val modifiers = field.modifiers

            Field(
                Modifier.isFinal(modifiers),
                owner,
                fieldName,
                TypeResolver.getTypeByName(field.type.canonicalName),
                AccessModifier.getModifier(modifiers),
                static = Modifier.isStatic(modifiers)
            )
        } catch (e: Exception) {
            null
        }

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