package io.github.chaosunity.casc.parsing

import io.github.chaosunity.casc.bytecode.MethodFactory
import io.github.chaosunity.casc.parsing.node.statement.Statement
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.type.Type

open class Function<T>(val signature: FunctionSignature, val rootStatement: Statement<*>) where T : Function<T> {
    val name: String = signature.name
    open val returnType: Type = signature.returnType

    fun generate(factory: MethodFactory) =
        factory.generate(this as T)
}