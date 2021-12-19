package org.casc.lang.table

import org.casc.lang.ast.Accessor
import org.casc.lang.ast.Function
import java.lang.reflect.Modifier

data class Scope(
    val isGlobalScope: Boolean = true,
    var classPath: String = "",
    var usages: MutableSet<Reference> = mutableSetOf(),
    var functions: MutableSet<FunctionSignature> = mutableSetOf(),
    var variables: MutableList<Variable> = mutableListOf()
) {
    constructor(parent: Scope) : this(false, parent.classPath, parent.usages, parent.functions, parent.variables)

    /**
     * registerFunctionSignature must be called after checker assigned types to function object
     */
    fun registerFunctionSignature(function: Function) {
        functions += FunctionSignature(
            function.ownerReference!!,
            function.compKeyword != null,
            function.mutKeyword != null,
            function.accessor,
            function.name?.literal ?: "",
            function.parameterTypes!!.mapNotNull { it },
            function.returnType!!
        )
    }

    fun findFunctionInSameClass(name: String, argumentTypes: List<Type?>): FunctionSignature? =
        if (isGlobalScope) null
        else functions.find { it.name == name && it.parameters.zip(argumentTypes).all { (t1, t2) -> t1 == t2 } }

    fun findFunction(ownerPath: String?, name: String, argumentTypes: List<Type?>): FunctionSignature? =
        if (ownerPath == null || ownerPath == classPath) findFunctionInSameClass(name, argumentTypes)
        else {
            // TODO: Support finding functions in cached classes
            val ownerType = TypeUtil.asType(ownerPath)

            if (ownerType == null) null
            else {
                val argTypes = argumentTypes.mapNotNull { it }

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
                        TypeUtil.asType(function.returnType)!!
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }

    fun registerVariable(mutable: Boolean, name: String, type: Type?): Boolean {
        val index = if (variables.isEmpty()) 0
        else {
            val (_, _, lastType, lastIndex) = variables.last()

            if (lastType == PrimitiveType.F64 || lastType == PrimitiveType.I64) lastIndex + 2
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

    fun findField(ownerPath: String?, name: String): Field? =
        if (ownerPath == null || ownerPath == classPath) null // TODO: Implement local field lookup
        else {
            val ownerType = TypeUtil.asType(ownerPath)

            if (ownerType == null) null
            else {
                val ownerClass = ownerType.type()!!

                try {
                    val field = ownerClass.getField(name)

                    Field(
                        Reference.fromClass(ownerClass),
                        Modifier.isStatic(field.modifiers),
                        Modifier.isFinal(field.modifiers),
                        Accessor.fromModifier(field.modifiers),
                        name,
                        TypeUtil.asType(field.type)!!
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }

    fun findVariableIndex(name: String): Int? {
        var index: Int? = null

        variables.forEach {
            if (it.name == name) index = it.index
        }

        return index
    }

    fun findType(reference: Reference?): Type? =
        if (reference == null) null
        else TypeUtil.asType(reference)

    private fun retrieveExecutableInfo(ownerType: Type, argumentTypes: List<Type>): Pair<Class<*>, Array<Class<*>>> =
        ownerType.type()!! to argumentTypes.mapNotNull(Type::type).toTypedArray()
}
