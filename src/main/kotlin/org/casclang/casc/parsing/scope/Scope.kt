package org.casclang.casc.parsing.scope

import org.casclang.casc.parsing.Function
import org.casclang.casc.parsing.MetaData
import org.casclang.casc.parsing.node.expression.Argument
import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.parsing.type.Type

class Scope(private val metadata: MetaData, usages: List<Usage> = listOf()) {
    val usages = mutableMapOf<String, Usage>()

    val localVariables = linkedMapOf<String, LocalVariable>()
    private val concealedFields = linkedMapOf<String, Field>()
    val fields = linkedMapOf<String, Field>()
    val functions = mutableListOf<Function<*>>()
    val functionSignatures = mutableListOf<FunctionSignature>()

    var callingScope = CallingScope.STATIC

    val className = metadata.className
    val classType = ClassType(className)
    val superClassInternalName = ClassType(metadata.superClassName).internalName

    init {
        usages.forEach(this::addUsage)
    }

    constructor(scope: Scope) : this(scope.metadata) {
        usages += scope.usages

        callingScope = scope.callingScope
        concealedFields += scope.concealedFields
        localVariables += scope.localVariables
        fields += scope.fields
        functions += scope.functions
        functionSignatures += scope.functionSignatures
    }

    fun addUsage(usage: Usage) {
        when (usage) {
            is PathUsage -> {
                val referencablePath = usage.qualifiedPath.split('.').last()

                if (usages.containsKey(referencablePath)) throw RuntimeException("Duplicate usage: $referencablePath")

                this.usages += referencablePath to usage
            }
            is ClassUsage -> {
                val referencableClassName = usage.className

                if (usages.containsKey(referencableClassName)) throw RuntimeException("Duplicate usage: $referencableClassName")

                this.usages += referencableClassName to usage
            }
        }
    }

    fun addFunction(function: Function<*>) {
        functions += function
    }

    fun addSignature(signature: FunctionSignature) {
        if (isSignatureExists(signature)) throw RuntimeException("Function '${signature.name}' already exists.")
        functionSignatures += signature
    }

    fun isSignatureExists(signature: FunctionSignature): Boolean =
        functionSignatures.any { it.matches(signature) }

    fun isSignatureExists(identifier: String, arguments: List<Argument>): Boolean =
        functionSignatures.any { it.matches(identifier, arguments) }

    fun getMethodCallSignatureWithoutParameters(identifier: String): FunctionSignature =
        getMethodCallSignature(identifier, listOf())

    fun getMethodCallSignature(identifier: String, arguments: List<Argument>): FunctionSignature =
        functionSignatures.find {
            it.matches(identifier, arguments)
        } ?: throw RuntimeException("Function '$identifier' does not exist.")

    fun getConstructorCallSignature(className: String, arguments: List<Argument>): FunctionSignature =
        if (className != this.className) {
            val argumentsType = arguments.map(Argument::type)

            ClassPathScope.getConstructorSignature(className, argumentsType)
                ?: throw RuntimeException(
                    "Class constructor '$className' with type arguments '(${
                        argumentsType.map(Type::internalName).joinToString(", ")
                    })' does not exist."
                )
        } else getMethodCallSignature(null, className, arguments)

    fun getMethodCallSignature(owner: Type?, methodName: String, arguments: List<Argument>): FunctionSignature =
        if (owner != null && owner != classType) {
            val argumentsType = arguments.map(Argument::type)

            ClassPathScope.getMethodSignature(owner, methodName, argumentsType)
                ?: throw RuntimeException(
                    "Function '${
                        owner.typeName.replace(
                            ".",
                            "::"
                        )
                    }#$methodName' with type arguments '(${argumentsType.joinToString(", ")})' does not exist."
                )
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

    fun getField(owner: Type?, fieldName: String): Field =
        if (owner != null && owner != classType) {
            ClassPathScope.getField(owner, fieldName) ?: throw RuntimeException(
                "Field '${
                    owner.typeName.replace(
                        ".",
                        "::"
                    )
                }#$fieldName' does not exist."
            )
        } else if (owner != null && owner == classType) {
            concealedFields[fieldName] ?: fields[fieldName]
            ?: throw RuntimeException("Field '$fieldName' does not exist.")
        } else getField(fieldName)

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