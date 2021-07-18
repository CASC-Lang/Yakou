package org.casclang.casc.parsing.scope

import org.casclang.casc.parsing.Function
import org.casclang.casc.parsing.MetaData
import org.casclang.casc.parsing.node.expression.Argument
import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.parsing.type.Type

class Scope(private val metadata: MetaData) {
    val usages = mutableMapOf<String, Reference>()

    val localVariables = linkedMapOf<String, LocalVariable>()
    private val concealedFields = linkedMapOf<String, Field>()
    val fields = linkedMapOf<String, Field>()
    val functions = mutableListOf<Function<*>>()
    val functionSignatures = mutableListOf<FunctionSignature>()

    var callingScope = CallingScope.STATIC

    val className = metadata.className
    val classType = ClassType(className)
    val superClassInternalName = ClassType(metadata.superClassName).internalName

    constructor(scope: Scope) : this(scope.metadata) {
        usages += scope.usages

        callingScope = scope.callingScope
        concealedFields += scope.concealedFields
        localVariables += scope.localVariables
        fields += scope.fields
        functions += scope.functions
        functionSignatures += scope.functionSignatures
    }

    fun addUsage(usage: Reference): Boolean {
        if (usages.containsKey(usage.localName))
            return false

        usages += usage.localName to usage

        return true
    }

    fun addFunction(function: Function<*>) {
        functions += function
    }

    fun addSignature(signature: FunctionSignature): Boolean {
        if (isSignatureExists(signature)) return false
        functionSignatures += signature

        return true
    }

    fun isSignatureExists(signature: FunctionSignature): Boolean =
        functionSignatures.any { it.matches(signature) }

    fun isSignatureExists(identifier: String, arguments: List<Argument>): Boolean =
        functionSignatures.any { it.matches(identifier, arguments) }

    fun getMethodCallSignatureWithoutParameters(identifier: String): FunctionSignature? =
        getMethodCallSignature(identifier, listOf())

    fun getMethodCallSignature(identifier: String, arguments: List<Argument>): FunctionSignature? =
        functionSignatures.find { it.matches(identifier, arguments) }

    fun getConstructorCallSignature(className: String, arguments: List<Argument>): FunctionSignature? =
        if (className != this.className) {
            val argumentsType = arguments.map(Argument::type)

            ClassPathScope.getConstructorSignature(className, argumentsType)
        } else getMethodCallSignature(null, className, arguments)

    fun getMethodCallSignature(owner: Type?, methodName: String, arguments: List<Argument>): FunctionSignature? =
        if (owner != null && owner != classType) {
            val argumentsType = arguments.map(Argument::type)

            ClassPathScope.getMethodSignature(owner, methodName, argumentsType)
        } else getMethodCallSignature(methodName, arguments)

    fun concealNonStaticFields() {
        if (callingScope != CallingScope.STATIC)
            return

        val staticFields = fields.filter { it.value.static }

        concealedFields.putAll(fields)
        fields.clear()
        fields.putAll(staticFields)
    }

    fun revealNonStaticFields() {
        if (callingScope != CallingScope.STATIC)
            return

        val nonStaticFields = concealedFields

        fields.putAll(nonStaticFields)
        concealedFields.clear()
    }

    fun addField(field: Field) {
        fields += field.name to field
    }

    fun getField(owner: Type?, fieldName: String): Field? =
        if (owner != null && owner != classType) {
            ClassPathScope.getField(owner, fieldName)
        } else if (owner != null && owner == classType) {
            concealedFields[fieldName] ?: fields[fieldName]
        } else getField(fieldName)

    fun getField(fieldName: String): Field? =
        fields[fieldName]

    fun isFieldExists(fieldName: String): Boolean =
        fields.containsKey(fieldName)

    fun addLocalVariable(variable: LocalVariable) {
        localVariables += variable.name to variable
    }

    fun getLocalVariable(variableName: String): LocalVariable? =
        localVariables[variableName]

    fun isLocalVariableExists(variableName: String): Boolean =
        localVariables.containsKey(variableName)

    fun getLocalVariableIndex(variableName: String): Int =
        localVariables.keys.indexOf(variableName)
}