package org.casc.lang.table

import io.github.classgraph.ClassGraph
import org.casc.lang.ast.Accessor
import org.casc.lang.ast.Field
import org.casc.lang.ast.HasSignature
import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.utils.getOrElse
import java.lang.reflect.Modifier

data class Scope(
    val preference: AbstractPreference,
    var companion: Boolean,
    var mutable: Boolean,
    var accessor: Accessor,
    var classReference: Reference,
    var parentClassPath: Reference? = null,
    var usages: MutableSet<Reference> = mutableSetOf(),
    var fields: MutableSet<ClassField> = mutableSetOf(),
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
        isCompScope: Boolean = false,
        isLoopScope: Boolean = false,
    ) : this(
        parent.preference,
        companion ?: parent.companion,
        mutable ?: parent.mutable,
        accessor ?: parent.accessor,
        classPath ?: parent.classReference,
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
            val classes = ClassGraph()
                .acceptPackagesNonRecursive("java.lang")
                .overrideClassLoaders(ClassLoader.getSystemClassLoader())
                .scan()


            for (classInfo in classes.allStandardClasses)
                usages += Reference(classInfo.loadClass().name)
        }
    }

    //=======================================//
    //                Fields                 //
    //=======================================//

    fun registerField(field: Field) {
        fields += field.asClassField()
    }

    fun findField(ownerPath: Reference?, fieldName: String): ClassField? {
        if (ownerPath == null || ownerPath == classReference) return fields.find { it.name == fieldName }

        // Find function signature from cached classes first
        val ownerClass = Table.findClass(ownerPath)

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

                return ClassField(
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

    fun registerSignature(signatureObject: HasSignature) {
        signatures += signatureObject.asSignature()
    }

    private fun findSignatureInSameClass(functionName: String, argumentTypes: List<Type?>): FunctionSignature? =
        signatures.find {
            it.name == functionName &&
                    it.parameters.size == argumentTypes.size &&
                    argumentTypes
                        .zip(it.parameters)
                        .all { (l, r) ->
                            canCast(l, r)
                        }
        }

    fun findSignature(ownerPath: Reference?, functionName: String, argumentTypes: List<Type?>): FunctionSignature? {
        if (ownerPath == null) return findSignatureInSameClass(
            functionName,
            argumentTypes
        )

        val reference = findReference(ownerPath)

        // Find function signature from cached classes first
        val ownerClass = Table.findClass(reference)

        if (ownerClass != null) {
            return if (functionName == "<init>") {
                ownerClass.constructors.find {
                    it.parameterTypes.size == argumentTypes.size &&
                            argumentTypes
                                .zip(it.parameterTypes)
                                .all { (l, r) ->
                                    canCast(l, r)
                                }
                }?.asSignature() ?: findSignature(ownerClass.parentClassReference, functionName, argumentTypes)
            } else {
                ownerClass.functions.find {
                    it.name?.literal == functionName &&
                            it.parameterTypes?.size == argumentTypes.size &&
                            argumentTypes
                                .zip(it.parameterTypes!!)
                                .all { (l, r) ->
                                    canCast(l, r)
                                }
                }?.let(HasSignature::asSignature) ?: findSignature(
                    ownerClass.parentClassReference,
                    functionName,
                    argumentTypes
                )
            }
        }

        val ownerType = findType(reference)

        if (ownerType != null) {
            val argTypes = argumentTypes.mapNotNull { it }

            if (functionName == "<init>") {
                // Constructor
                try {
                    val (ownerClazz, argumentClasses) = retrieveExecutableInfo(ownerType, argTypes)
                    val constructors = ownerClazz.constructors.filter { it.parameters.size == argumentClasses.size }

                    for (constructor in constructors) {
                        if (constructor.parameterTypes.zip(argumentClasses).all { (l, r) -> l.isAssignableFrom(r) }) {
                            return FunctionSignature(
                                Reference(ownerClazz),
                                companion = true,
                                mutable = false,
                                Accessor.fromModifier(constructor.modifiers),
                                functionName,
                                constructor.parameterTypes.map { TypeUtil.asType(it, preference)!! },
                                ownerType
                            )
                        }
                    }
                } catch (_: Exception) {
                }
            } else {
                // Function
                try {
                    var (ownerClazz, argumentClasses) =
                        if (ownerPath == classReference) retrieveExecutableInfo(
                            findType(parentClassPath) ?: ClassType(Any::class.java), argTypes
                        )
                        else retrieveExecutableInfo(ownerType, argTypes)
                    var signature: FunctionSignature? = null

                    while (ownerClazz != Any::class.java) {
                        try {
                            val functions =
                                ownerClazz.declaredMethods.filter { it.name == functionName && it.parameters.size == argumentClasses.size }

                            for (function in functions) {
                                if (function.parameterTypes.zip(argumentClasses)
                                        .all { (l, r) -> l.isAssignableFrom(r) }
                                ) {
                                    signature = FunctionSignature(
                                        Reference(ownerClazz),
                                        Modifier.isStatic(function.modifiers),
                                        Modifier.isFinal(function.modifiers),
                                        Accessor.fromModifier(function.modifiers),
                                        functionName,
                                        function.parameterTypes.map { TypeUtil.asType(it, preference)!! },
                                        TypeUtil.asType(function.returnType, preference)!!
                                    )
                                }
                            }

                            break
                        } catch (e: Throwable) {
                            ownerClazz = ownerClazz.superclass
                        }
                    }

                    return signature
                } catch (_: Throwable) {
                }
            }
        }

        return null
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
            mutable,
            variableName,
            type,
            index,
            scopeDepth
        )

        if (variables.contains(variable)) return false

        return variables.add(variable)
    }

    fun findVariable(variableName: String): Variable? =
        if (!isCompScope && (variableName == "self" || variableName == "super")) {
            when (variableName) {
                "self" -> Variable(true, "self", findType(classReference), 0, scopeDepth)
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
        classReference -> ClassType(
            classReference.fullQualifiedPath,
            parentClassPath?.fullQualifiedPath,
            accessor,
            mutable
        )
        else -> TypeUtil.asType(findReference(reference), preference)
    }

    fun canCast(from: Type?, to: Type?): Boolean =
        TypeUtil.canCast(from, to, preference)

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
        childReference == classReference -> isChildType(
            findType(parentReference),
            findType(parentClassPath)
        )
        else -> isChildType(findType(parentReference), findType(childReference))
    }

    fun isChildType(parentReference: Reference?): Boolean =
        isChildType(parentReference, classReference)

    private fun retrieveExecutableInfo(
        ownerType: Type,
        argumentTypes: List<Type>
    ): Pair<Class<*>, Array<Class<*>>> =
        ownerType.type(preference)!! to argumentTypes.mapNotNull { it.type(preference) }.toTypedArray()
}
