package org.casc.lang.table

import org.casc.lang.ast.Accessor

data class FunctionSignature(
    val ownerReference: Reference,
    val companion: Boolean,
    val mutable: Boolean,
    val accessor: Accessor,
    val name: String,
    val parameters: List<Type>,
    val returnType: Type
) : HasDescriptor {
    override val descriptor: String =
        "(${parameters.fold("") { s, parameter -> s + parameter.descriptor }})${returnType.descriptor}"
}
