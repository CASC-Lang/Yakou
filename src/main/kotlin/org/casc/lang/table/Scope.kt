package org.casc.lang.table

import org.casc.lang.ast.Function
import org.casc.lang.ast.Parameter
import org.casc.lang.checker.Checker
import org.casc.lang.parser.Parser

data class Scope(
    val isGlobalScope: Boolean = true,
    var usages: MutableSet<Reference> = mutableSetOf(),
    var functions: MutableSet<FunctionSignature> = mutableSetOf()
) {
    constructor(parent: Scope) : this(false, parent.usages, parent.functions)

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

    fun findType(reference: Reference?): Type? =
        if (reference == null) null
        else TypeUtil.asType(reference)
}
