package org.casc.lang.table

import org.casc.lang.ast.Accessor
import org.casc.lang.ast.Class as Cls
import org.casc.lang.ast.Field
import org.casc.lang.ast.HasSignature
import org.casc.lang.ast.Parameter
import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.utils.getOrElse
import java.lang.reflect.Modifier
import org.casc.lang.utils.eq

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
    var isCompScope: Boolean = false
) {
    constructor(
        parent: Scope,
        companion: Boolean? = null,
        mutable: Boolean? = null,
        accessor: Accessor? = null,
        classPath: Reference? = null,
        isCompScope: Boolean = false
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
        isCompScope
    )

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
            val ownerClass = ownerType.type()!!

            try {
                val field = ownerClass.getField(fieldName)

                return ClassField(
                    Reference.fromClass(ownerClass),
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
                    it.parameters
                        .zip(argumentTypes)
                        .all(::eq)
        }

    fun findSignature(ownerPath: Reference?, functionName: String, argumentTypes: List<Type?>): FunctionSignature? {
        if (ownerPath == null || ownerPath == classReference) return findSignatureInSameClass(
            functionName,
            argumentTypes
        )

        val reference = findReference(ownerPath)

        // Find function signature from cached classes first
        val ownerClass = Table.findClass(reference)

        if (ownerClass != null) {
            return if (functionName == "<init>") {
                ownerClass.constructors.find {
                    it.parameters.size == argumentTypes.size &&
                            it.parameters
                                .map(Parameter::typeReference)
                                .zip(argumentTypes.filterNotNull().map(Type::getReference))
                                .all(::eq)
                }?.asSignature() ?: findSignature(ownerClass.parentClassReference, functionName, argumentTypes)
            } else {
                ownerClass.functions.find {
                    it.name?.literal == functionName &&
                            it.parameters.size == argumentTypes.size &&
                            it.parameters
                                .map(Parameter::type)
                                .zip(argumentTypes)
                                .all(::eq)
                }?.asSignature() ?: findSignature(ownerClass.parentClassReference, functionName, argumentTypes)
            }
        }

        val ownerType = findType(reference)

        if (ownerType != null) {
            val argTypes = argumentTypes.mapNotNull { it }

            if (functionName == "<init>") {
                // Constructor
                try {
                    val (ownerClass, argumentClasses) = retrieveExecutableInfo(ownerType, argTypes)
                    val constructor = ownerClass.getConstructor(*argumentClasses)

                    return FunctionSignature(
                        Reference.fromClass(ownerClass),
                        companion = true,
                        mutable = false,
                        Accessor.fromModifier(constructor.modifiers),
                        functionName,
                        argTypes,
                        ownerType
                    )
                } catch (_: Exception) {
                }
            } else {
                // Function
                try {
                    var (ownerClass, argumentClasses) =
                        if (ownerPath == classReference) retrieveExecutableInfo(
                            findType(parentClassPath) ?: ClassType(Any::class.java), argTypes
                        )
                        else retrieveExecutableInfo(ownerType, argTypes)
                    var signature: FunctionSignature? = null

                    while (ownerClass != Any::class.java) {
                        try {
                            val function = ownerClass.getMethod(functionName, *argumentClasses)

                            signature = FunctionSignature(
                                Reference.fromClass(ownerClass),
                                Modifier.isStatic(function.modifiers),
                                Modifier.isFinal(function.modifiers),
                                Accessor.fromModifier(function.modifiers),
                                functionName,
                                argTypes,
                                TypeUtil.asType(function.returnType, preference)!!
                            )

                            break
                        } catch (e: Throwable) {
                            ownerClass = ownerClass.superclass
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
            index
        )

        if (variables.contains(variable)) return false

        return variables.add(variable)
    }

    fun findVariable(variableName: String): Variable? =
        if (!isCompScope && (variableName == "self" || variableName == "super")) {
            when (variableName) {
                "self" -> Variable(true, "self", findType(classReference), 0)
                "super" -> Variable(true, "super", findType(parentClassPath), 0)
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
        classReference -> ClassType(classReference.fullQualifiedPath, accessor, mutable)
        else -> TypeUtil.asType(findReference(reference), preference)
    }

    /**
     * Find reference (either shortened, aliased, or full-qualified) from usage, return itself if not found
     */
    fun findReference(reference: Reference): Reference =
        usages.find { it.className == reference.className } ?: reference

    fun findClass(reference: Reference?): Cls? = when {
        reference == null -> null
        Table.hasClass(reference) -> Table.findClass(reference)
        else -> null
    }

    fun isChildType(parentType: Type?, childType: Type?): Boolean =
        parentType?.type()?.isAssignableFrom(childType?.type() ?: Any::class.java).getOrElse()

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
        ownerType.type()!! to argumentTypes.mapNotNull(Type::type).toTypedArray()
}
