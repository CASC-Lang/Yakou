package org.casc.lang.table

import io.github.classgraph.ClassGraph
import org.casc.lang.ast.Accessor
import org.casc.lang.ast.ClassInstance
import org.casc.lang.ast.Field
import org.casc.lang.ast.HasSignature
import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.utils.getOrElse
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.*

data class Scope(
    val preference: AbstractPreference,
    var companion: Boolean,
    var mutable: Boolean,
    var accessor: Accessor,
    var typeReference: Reference,
    var isTrait: Boolean = false,
    var parentClassPath: Reference? = null,
    var usages: MutableSet<Reference> = mutableSetOf(),
    var fields: MutableSet<TypeField> = mutableSetOf(),
    var signatures: MutableSet<FunctionSignature> = mutableSetOf(),
    var variables: MutableList<Variable> = mutableListOf(),
    var scopeDepth: Int = 0, // Scope always starts from global scope
    var isCompScope: Boolean = false,
    var isLoopScope: Boolean = false,
) {
    constructor(
        parent: Scope,
        companion: Boolean? = null,
        mutable: Boolean? = null,
        accessor: Accessor? = null,
        classPath: Reference? = null,
        isTrait: Boolean? = null,
        isCompScope: Boolean = false,
        isLoopScope: Boolean = false,
    ) : this(
        parent.preference,
        companion ?: parent.companion,
        mutable ?: parent.mutable,
        accessor ?: parent.accessor,
        classPath ?: parent.typeReference,
        isTrait ?: parent.isTrait,
        parent.parentClassPath,
        parent.usages.toMutableSet(),
        parent.fields.toMutableSet(),
        parent.signatures.toMutableSet(),
        parent.variables.toMutableList(),
        parent.scopeDepth + 1,
        isCompScope,
        isLoopScope
    )

    init {
        if (usages.isEmpty()) {
            // It was just initialized, add classes under package java.lang
            val classes = ClassGraph().acceptPackagesNonRecursive("java.lang")
                .overrideClassLoaders(ClassLoader.getSystemClassLoader()).scan()

            for (classInfo in classes.allStandardClasses) usages += Reference(classInfo.loadClass().name)
        }
    }

    //=======================================//
    //                Fields                 //
    //=======================================//

    fun registerField(field: Field) {
        fields += field.asClassField()
    }

    fun findField(ownerPath: Reference?, fieldName: String): TypeField? {
        if (ownerPath == null || ownerPath == typeReference) return fields.find { it.name == fieldName }

        // Find function signature from cached classes first
        val ownerClass = Table.findTypeInstance(ownerPath)

        if (ownerClass != null) {
            return ownerClass.fields.find {
                it.name?.literal == fieldName
            }?.asClassField()
        }

        val ownerType = TypeUtil.asType(ownerPath, preference)

        if (ownerType != null) {
            val ownerClazz = ownerType.type(preference)!!

            try {
                val field = ownerClazz.getField(fieldName)

                return TypeField(
                    Reference(ownerClazz),
                    Modifier.isStatic(field.modifiers),
                    !Modifier.isFinal(field.modifiers),
                    Accessor.fromModifier(field.modifiers),
                    fieldName,
                    TypeUtil.asType(field.type, preference)!!
                )
            } catch (_: Exception) {
            }
        }

        return null
    }

    //=======================================//
    //         Function Signatures           //
    //=======================================//

    fun registerSignature(signatureObject: HasSignature): Boolean = signatures.add(signatureObject.asSignature())

    private fun findSignatureInSameType(functionName: String, argumentTypes: List<Type?>): FunctionSignature? =
        signatures.find {
            it.name == functionName && it.parameters.size == argumentTypes.size && argumentTypes.zip(it.parameters)
                .all { (l, r) ->
                    canCast(l, r)
                }
        }

    /**
     * Searches functions with the following searching order:
     * 1. Current scope if:
     *     - argument `ownerPath` is null
     *     - argument `ownerPath`'s reference is same as current scope's typeReference
     * 2. Type symbol table / JVM Class Loader:
     *     - always search implemented trait (interface) functions first
     *     - if previous step does not return available function signature, search parent class
     */
    fun findSignature(
        ownerPath: Reference?, functionName: String, argumentTypes: List<Type?>, searchTraitOnly: Boolean = false
    ): FunctionSignature? {
        if (ownerPath == null) return findSignatureInSameType(
            functionName, argumentTypes
        )

        val reference = findReference(ownerPath)

        // Find function signature from cached type instances first
        val typeInstance = Table.findTypeInstance(reference)

        if (typeInstance != null) {
            if (typeInstance is ClassInstance && functionName == "<init>") {
                return typeInstance.impl?.constructors?.find {
                    it.parameterTypes.size == argumentTypes.size && argumentTypes.zip(it.parameterTypes).all { (l, r) ->
                        canCast(l, r)
                    }
                }?.asSignature()
            } else {
                // TODO: Find functions from implemented traits
                var functionSignature = typeInstance.impl?.functions?.find {
                    it.name?.literal == functionName && it.parameterTypes?.size == argumentTypes.size && argumentTypes.zip(
                        it.parameterTypes!!
                    ).all { (l, r) ->
                        canCast(l, r)
                    }
                }?.let(HasSignature::asSignature)

                if (functionSignature != null) return functionSignature

                // Search trait function first
                if (typeInstance.traitImpls != null) {
                    for (traitImpl in typeInstance.traitImpls!!) {
                        functionSignature =
                            findSignature(traitImpl.implementedTraitReference, functionName, argumentTypes)

                        if (functionSignature != null) return functionSignature
                    }
                }

                if (searchTraitOnly) return null

                // Search function in parent class
                return if (typeInstance.impl != null) {
                    findSignature(
                        typeInstance.impl!!.parentClassReference ?: Reference.OBJECT_TYPE_REFERENCE,
                        functionName,
                        argumentTypes
                    )
                } else null
            }
        }

        val ownerType = findType(reference)

        if (ownerType != null) {
            val argTypes = argumentTypes.mapNotNull { it }

            if (functionName == "<init>") {
                // Constructor
                val (ownerClazz, argumentClasses) = retrieveExecutableInfo(ownerType, argTypes)
                val constructors = ownerClazz.constructors.filter { it.parameters.size == argumentClasses.size }

                for (constructor in constructors) {
                    if (constructor.parameterTypes.zip(argumentClasses).all { (l, r) -> l.isAssignableFrom(r) }) {
                        return FunctionSignature(
                            Reference(ownerClazz),
                            TypeUtil.asType(ownerClazz, preference) as ClassType,
                            companion = true,
                            mutable = false,
                            Accessor.fromModifier(constructor.modifiers),
                            functionName,
                            constructor.parameterTypes.map { TypeUtil.asType(it, preference)!! },
                            ownerType
                        )
                    }
                }
            } else {
                // Function
                var (ownerClazz, argumentClasses) = if (ownerPath == typeReference) retrieveExecutableInfo(
                    findType(parentClassPath) ?: ClassType(Any::class.java), argTypes
                )
                else retrieveExecutableInfo(ownerType, argTypes)

                var functions = filterFunction(ownerClazz, functionName, argumentClasses)

                for (function in functions) {
                    if (isSameFunction(function, argumentClasses)) {
                        return FunctionSignature(ownerClazz, function, preference)
                    }
                }

                // Search trait function first (BFS)
                val traits = LinkedList(ownerClazz.interfaces.toList())

                while (traits.isNotEmpty()) {
                    val trait = traits.remove()
                    functions = filterFunction(trait, functionName, argumentClasses)

                    for (function in functions) {
                        if (isSameFunction(function, argumentClasses)) {
                            return FunctionSignature(trait, function, preference)
                        }
                    }

                    traits += trait.interfaces
                }

                if (searchTraitOnly) return null

                if (ownerClazz.superclass == null) return null

                ownerClazz = ownerClazz.superclass

                while (ownerClazz != null) {
                    functions = filterFunction(ownerClazz, functionName, argumentClasses)

                    for (function in functions) {
                        if (isSameFunction(function, argumentClasses)) {
                            return FunctionSignature(ownerClazz, function, preference)
                        }
                    }

                    ownerClazz = ownerClazz.superclass
                }
            }
        }

        return null
    }

    private fun filterFunction(
        ownerClass: Class<*>,
        functionName: String,
        argumentClasses: Array<Class<*>>
    ): List<Method> =
        ownerClass.declaredMethods.filter { it.name == functionName && it.parameters.size == argumentClasses.size }

    private fun isSameFunction(function: Method, argumentClasses: Array<Class<*>>): Boolean {
        for ((i, parameterType) in function.parameterTypes.withIndex()) {
            if (!parameterType.isAssignableFrom(argumentClasses[i])) {
                return false
            }
        }

        return true
    }

    //=======================================//
    //            Local Variables            //
    //=======================================//

    fun registerVariable(mutable: Boolean, variableName: String, type: Type?): Boolean {
        val index = if (variables.isEmpty()) 0
        else {
            val (_, _, lastType, lastIndex) = variables.last()

            if (lastType == PrimitiveType.F64 || lastType == PrimitiveType.I64) lastIndex + 2 // f32 and f64 occupy two stack
            else lastIndex + 1
        }
        val variable = Variable(
            mutable, variableName, type, index, scopeDepth
        )

        if (variables.contains(variable)) return false

        return variables.add(variable)
    }

    fun findVariable(variableName: String): Variable? =
        if (!isCompScope && (variableName == "self" || variableName == "super")) {
            when (variableName) {
                "self" -> Variable(true, "self", findType(typeReference), 0, scopeDepth)
                "super" -> Variable(true, "super", findType(parentClassPath), 0, scopeDepth)
                else -> null // Should not happen
            }
        } else variables.find { it.name == variableName }

    fun findVariableIndex(variableName: String): Int? {
        var index: Int? = null

        variables.forEach {
            if (it.name == variableName) index = it.index
        }

        return index
    }

    fun findType(reference: Reference?): Type? = when (reference) {
        null -> null
        typeReference -> ClassType(
            typeReference.fullQualifiedPath, parentClassPath?.fullQualifiedPath, accessor, mutable, isTrait
        )
        else -> TypeUtil.asType(findReference(reference), preference)
    }

    fun canCast(from: Type?, to: Type?): Boolean = TypeUtil.canCast(from, to, preference)

    /**
     * Find reference (either shortened, aliased, or full-qualified) from usage, return itself if not found
     */
    fun findReference(reference: Reference): Reference =
        usages.find { it.className == reference.className } ?: reference

    private fun isChildType(parentType: Type?, childType: Type?): Boolean =
        parentType?.type(preference)?.isAssignableFrom(childType?.type(preference) ?: Any::class.java).getOrElse()

    fun isChildType(parentReference: Reference?, childReference: Reference?): Boolean = when {
        childReference == null -> false
        parentReference == childReference -> false
        childReference == typeReference -> isChildType(
            findType(parentReference), findType(parentClassPath)
        )
        else -> isChildType(findType(parentReference), findType(childReference))
    }

    fun isChildType(parentReference: Reference?): Boolean = isChildType(parentReference, typeReference)

    private fun retrieveExecutableInfo(
        ownerType: Type, argumentTypes: List<Type>
    ): Pair<Class<*>, Array<Class<*>>> =
        ownerType.type(preference)!! to argumentTypes.mapNotNull { it.type(preference) }.toTypedArray()
}
