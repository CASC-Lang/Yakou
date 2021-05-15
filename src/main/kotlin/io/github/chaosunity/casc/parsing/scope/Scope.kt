package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.parsing.MetaData
import io.github.chaosunity.casc.parsing.node.expression.Argument
import io.github.chaosunity.casc.parsing.node.expression.SuperCall
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.parsing.type.Type

class Scope(private val metadata: MetaData) {
    val localVariables = linkedMapOf<String, LocalVariable>()
    val fields = linkedMapOf<String, Field>()
    val functionSignatures = mutableListOf<FunctionSignature>()

    var callingScope = CallingScope.STATIC

    val className = metadata.className
    val classType = ClassType(className)
    val superClassInternalName = ClassType(metadata.superClassName).internalName

    constructor(scope: Scope) : this(scope.metadata) {
        callingScope = scope.callingScope
        localVariables += scope.localVariables
        fields += scope.fields
        functionSignatures += scope.functionSignatures
    }

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

    fun getConstructorCallSignature(className: String, arguments: List<Argument>): FunctionSignature {
        if (className != this.className) {
            val argumentsType = arguments.map(Argument::type)

            return ClassPathScope().getConstructorSignature(className, argumentsType)
                ?: throw RuntimeException(
                    "Class constructor '$className' with type arguments '${
                        argumentsType.map(Type::internalName).joinToString(", ")
                    }' does not exist."
                )
        }

        return getMethodCallSignature(null, className, arguments)
    }

    fun getMethodCallSignature(owner: Type?, methodName: String, arguments: List<Argument>): FunctionSignature {
        if (owner != null && owner != classType) {
            val argumentsType = arguments.map(Argument::type)

            return ClassPathScope().getMethodSignature(owner, methodName, argumentsType)
                ?: throw RuntimeException("Class '$className' with type arguments '${argumentsType.joinToString(", ")}' does not exist.")
        }

        return getMethodCallSignature(methodName, arguments)
    }

    fun addField(field: Field) {
        fields += field.name to field
    }

    fun getField(fieldName: String): Field =
        fields[fieldName] ?: throw RuntimeException("Field '$fieldName' does not exist.")

    fun isFieldExists(fieldName: String): Boolean =
        fields.containsKey(fieldName)

    fun addLocalVariable(variable: LocalVariable) {
        localVariables += variable.name to variable
    }

    fun getLocalVariable(variableName: String): LocalVariable =
        localVariables[variableName] ?: throw RuntimeException("Variable '$variableName' does not exist.")

    fun isLocalVariableExists(variableName: String): Boolean =
        localVariables.containsKey(variableName)

    fun getLocalVariableIndex(variableName: String): Int =
        localVariables.keys.indexOf(variableName)
}