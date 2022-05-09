package org.casc.lang.table

import org.casc.lang.ast.Accessor
import org.casc.lang.utils.getOrElse
import org.objectweb.asm.Opcodes

data class FunctionSignature(
    val ownerReference: Reference,
    val ownerType: ClassType?,
    val companion: Boolean,
    val mutable: Boolean,
    override val accessor: Accessor,
    val name: String,
    val parameters: List<Type>,
    val returnType: Type
) : HasDescriptor, HasFlag, HasAccessor {
    override val descriptor: String =
        if (name == "<init>") {
            // Constructor
            "(${parameters.fold("") { s, parameter -> s + parameter.descriptor }})V"
        } else {
            // Function
            "(${parameters.fold("") { s, parameter -> s + parameter.descriptor }})${returnType.descriptor}"
        }
    override val flag: Int =
        companion.getOrElse(Opcodes.ACC_STATIC) + mutable.getOrElse(0, Opcodes.ACC_FINAL) + accessor.access
}
