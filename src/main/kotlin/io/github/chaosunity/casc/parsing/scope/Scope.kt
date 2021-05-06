package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.parsing.MetaData
import io.github.chaosunity.casc.parsing.node.expression.Argument
import io.github.chaosunity.casc.parsing.node.expression.SuperCall
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.util.ReflectionMapper
import org.apache.commons.lang3.ClassUtils
import org.apache.commons.lang3.RegExUtils
import org.apache.commons.lang3.reflect.ConstructorUtils
import org.apache.commons.lang3.reflect.MethodUtils

class Scope(private val metadata: MetaData) {
    val localVariables = mutableListOf<LocalVariable>()
    val functionSignatures = mutableListOf<FunctionSignature>()
    val className = metadata.className
    val classType = ClassType(className)
    val classInternalName = classType.internalName
    val superClassName = metadata.superClassName
    val superClassInternalName = ClassType(superClassName).internalName

    constructor(scope: Scope) : this(scope.metadata) {
        localVariables += scope.localVariables
        functionSignatures += scope.functionSignatures
    }

    operator fun plusAssign(signature: FunctionSignature) =
        addSignature(signature)

    fun addSignature(signature: FunctionSignature) {
        if (isParameterLessSignatureExists(signature.name)) throw RuntimeException("Function '${signature.name}' already exists.")
        functionSignatures += signature
    }

    fun isParameterLessSignatureExists(identifier: String): Boolean =
        isSignatureExists(identifier, listOf())

    fun isSignatureExists(identifier: String, arguments: List<Argument>): Boolean =
        if (identifier == SuperCall.SUPER_CALL_ID) true
        else functionSignatures.any { it.matches(identifier, arguments) }

    fun getMethodCallSignatureWithoutParameters(identifier: String): FunctionSignature =
        getMethodCallSignature(identifier, listOf())

    fun getMethodCallSignature(identifier: String, arguments: List<Argument>): FunctionSignature =
        if (identifier == SuperCall.SUPER_CALL_ID) FunctionSignature(
            SuperCall.SUPER_CALL_ID,
            listOf(),
            BuiltInType.VOID
        )
        else functionSignatures.find { it.matches(identifier, arguments) }
            ?: throw RuntimeException("Function '$identifier' does not exist.")

    fun addLocalVariable(variable: LocalVariable) {
        localVariables += variable
    }

    fun getLocalVariable(variableName: String): LocalVariable =
        localVariables.find { it.name == variableName }
            ?: throw RuntimeException("Variable '$variableName' does not exist.")

    fun isLocalVariableExists(variableName: String): Boolean =
        localVariables.any { it.name == variableName }

    fun getLocalVariableIndex(variableName: String): Int =
        localVariables.indexOf(getLocalVariable(variableName))

    fun getSignatureFromClassPath(fullMethodName: String): FunctionSignature? {
        val methodName = RegExUtils.removePattern(fullMethodName, ".*\\.")
        val ownerClass: Class<*>

        try {
            ownerClass = ClassUtils.getClass(fullMethodName)
        } catch (e: Exception) {
            throw RuntimeException("Class '$fullMethodName' does not exist.")
        }

        val accessibleMethod = MethodUtils.getAccessibleMethod(ownerClass, methodName)

        if (accessibleMethod != null) {
            return ReflectionMapper.fromMethod(accessibleMethod)
        }

        val accessibleConstructor = ConstructorUtils.getAccessibleConstructor(ownerClass)

        if (accessibleConstructor != null) {
            return ReflectionMapper.fromConstructor(accessibleConstructor)
        }

        return null
    }
}