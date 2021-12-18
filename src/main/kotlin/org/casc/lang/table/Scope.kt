package org.casc.lang.table

import org.casc.lang.ast.Function

data class Scope(
    val isGlobalScope: Boolean = true,
    var usages: MutableSet<Reference> = mutableSetOf(),
    var functions: MutableSet<FunctionSignature> = mutableSetOf(),
    var variables: MutableList<Variable> = mutableListOf()
) {
    constructor(parent: Scope) : this(false, parent.usages, parent.functions, parent.variables)

    /**
     * registerFunctionSignature must be called after checker assigned types to function object
     */
    fun registerFunctionSignature(function: Function) {
        functions += FunctionSignature(
            function.compKeyword != null,
            function.mutKeyword != null,
            function.accessor,
            function.name?.literal ?: "",
            function.parameterTypes!!.mapNotNull { it },
            function.returnType!!
        )
    }

    fun registerVariable(mutable: Boolean, name: String, type: Type?): Boolean {
        val variable = Variable(mutable, name, type)

        if (variables.contains(variable)) return false

        return variables.add(variable)
    }

    fun findVariable(name: String): Variable? =
        variables.find { it.name == name }

    fun findVariableIndex(name: String): Int? {
        var index: Int? = null

        variables.forEachIndexed { i, it ->
            if (it.name == name) index = i
        }

        return index
    }

    fun findType(reference: Reference?): Type? =
        if (reference == null) null
        else TypeUtil.asType(reference)
}
