package org.casc.lang.table

import org.casc.lang.ast.Accessor
import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.utils.getOrElse
import org.objectweb.asm.Opcodes
import java.lang.reflect.Method
import java.lang.reflect.Modifier

data class FunctionSignature(
    val ownerReference: Reference,
    val ownerType: ClassType?,
    val companion: Boolean,
    val mutable: Boolean,
    val abstract: Boolean,
    override val accessor: Accessor,
    val name: String,
    val parameters: List<Type>,
    val returnType: Type
) : HasDescriptor, HasFlag, HasAccessor {
    constructor(ownerClass: Class<*>, function: Method, preference: AbstractPreference) : this(
        Reference(ownerClass),
        TypeUtil.asType(ownerClass, preference) as ClassType,
        Modifier.isStatic(function.modifiers),
        !Modifier.isFinal(function.modifiers),
        Modifier.isAbstract(function.modifiers),
        Accessor.fromModifier(function.modifiers),
        function.name,
        function.parameterTypes.map { TypeUtil.asType(it, preference)!! },
        TypeUtil.asType(function.returnType, preference)!!
    )

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FunctionSignature

        if (ownerReference != other.ownerReference) return false
        if (companion != other.companion) return false
        if (name != other.name) return false
        if (descriptor != other.descriptor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ownerReference.hashCode()
        result = 31 * result + companion.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + descriptor.hashCode()
        return result
    }
}
