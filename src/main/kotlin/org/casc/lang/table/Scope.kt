package org.casc.lang.table

import org.casc.lang.ast.*
import org.casc.lang.compilation.AbstractPreference
import java.lang.Class
import java.lang.reflect.Modifier

data class Scope(
    val preference: AbstractPreference,
    val isGlobalScope: Boolean = true,
    var classPath: String = "",
    var parentClassPath: String? = null,
    var usages: MutableSet<Reference> = mutableSetOf(),
    var fields: MutableSet<ClassField> = mutableSetOf(),
    var signatures: MutableSet<FunctionSignature> = mutableSetOf(),
    var variables: MutableList<Variable> = mutableListOf(),
    var isCompScope: Boolean = false
) {
    constructor(parent: Scope, classPath: String = "", parentClassPath: String? = null, isCompScope: Boolean = false) : this(
        parent.preference,
        false,
        parent.classPath.ifEmpty { classPath },
        parent.parentClassPath,
        parent.usages.toMutableSet(),
        parent.fields.toMutableSet(),
        parent.signatures.toMutableSet(),
        parent.variables.toMutableList(),
        isCompScope
    )

    fun registerField(field: Field) {
        fields += field.asClassField()
    }

    fun findField(ownerPath: String?, name: String): ClassField? =
        if (ownerPath == null || ownerPath == classPath) fields.find { it.name == name }
        else {
            val ownerType = TypeUtil.asType(ownerPath, preference)

            if (ownerType == null) null
            else {
                val ownerClass = ownerType.type()!!

                try {
                    val field = ownerClass.getField(name)

                    ClassField(
                        Reference.fromClass(ownerClass),
                        Modifier.isStatic(field.modifiers),
                        Modifier.isFinal(field.modifiers),
                        Accessor.fromModifier(field.modifiers),
                        name,
                        TypeUtil.asType(field.type, preference)!!
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }

    fun registerSignature(signatureObject: HasSignature) {
        signatures += signatureObject.asSignature()
    }

    private fun findSignatureInSameClass(name: String, argumentTypes: List<Type?>): FunctionSignature? =
        if (isGlobalScope) null
        else signatures.find {
            it.name == name && it.parameters.size == argumentTypes.size && it.parameters.zip(
                argumentTypes
            ).all { (t1, t2) -> t1 == t2 }
        }

    fun findSignature(ownerPath: String?, name: String, argumentTypes: List<Type?>): FunctionSignature? =
        if (ownerPath == null || ownerPath == classPath) findSignatureInSameClass(name, argumentTypes)
        else {
            val ownerType = findType(ownerPath)

            if (ownerType != null) {
                val argTypes = argumentTypes.mapNotNull { it }

                if (name == "<init>") {
                    // Constructor
                    try {
                        val (ownerClass, argumentClasses) = retrieveExecutableInfo(ownerType, argTypes)
                        val constructor = ownerClass.getConstructor(*argumentClasses)

                        FunctionSignature(
                            Reference.fromClass(ownerClass),
                            companion = true,
                            mutable = false,
                            Accessor.fromModifier(constructor.modifiers),
                            name,
                            argTypes,
                            ownerType
                        )
                    } catch (e: Exception) {
                        null
                    }
                } else {
                    // Function
                    try {
                        val (ownerClass, argumentClasses) = retrieveExecutableInfo(ownerType, argTypes)
                        val function = ownerClass.getMethod(name, *argumentClasses)

                        FunctionSignature(
                            Reference.fromClass(ownerClass),
                            Modifier.isStatic(function.modifiers),
                            Modifier.isFinal(function.modifiers),
                            Accessor.fromModifier(function.modifiers),
                            name,
                            argTypes,
                            TypeUtil.asType(function.returnType, preference)!!
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
            } else null
        }


    fun registerVariable(mutable: Boolean, name: String, type: Type?): Boolean {
        val index = if (variables.isEmpty()) 0
        else {
            val (_, _, lastType, lastIndex) = variables.last()

            if (lastType == PrimitiveType.F64 || lastType == PrimitiveType.I64) lastIndex + 2 // f32 and f64 occupy two stack
            else lastIndex + 1
        }
        val variable = Variable(
            mutable,
            name,
            type,
            index
        )

        if (variables.contains(variable)) return false

        return variables.add(variable)
    }

    fun findVariable(name: String): Variable? =
        variables.find { it.name == name }

    fun findVariableIndex(name: String): Int? {
        var index: Int? = null

        variables.forEach {
            if (it.name == name) index = it.index
        }

        return index
    }

    fun findType(className: String?): Type? =
        if (className == null) null
        else {
            val clazzName = usages.find {
                it.className == className
            }

            if (clazzName != null) TypeUtil.asType(clazzName, preference)
            else TypeUtil.asType(className, preference)
        }

    fun findType(reference: Reference?): Type? =
        if (reference == null) null
        else TypeUtil.asType(usages.find {
            it.className == reference.className
        }, preference) ?: TypeUtil.asType(reference, preference)

    private fun retrieveExecutableInfo(ownerType: Type, argumentTypes: List<Type>): Pair<Class<*>, Array<Class<*>>> =
        ownerType.type()!! to argumentTypes.mapNotNull(Type::type).toTypedArray()
}
