package org.casclang.casc.parsing

import org.casclang.casc.bytecode.FunctionFactory
import org.casclang.casc.parsing.node.statement.Statement
import org.casclang.casc.parsing.scope.AccessModifier
import org.casclang.casc.parsing.scope.FunctionSignature
import org.casclang.casc.parsing.type.Type

open class Function<T>(
    val signature: FunctionSignature,
    val rootStatement: Statement<*>,
    val accessModifier: AccessModifier,
    val static: Boolean = false
) where T : Function<T> {
    val name: String = signature.name
    open val returnType: Type = signature.returnType

    fun generate(factory: FunctionFactory) =
        factory.generate(this)
}