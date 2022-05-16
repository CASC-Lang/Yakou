package org.casc.lang.table

import org.casc.lang.ast.Accessor
import org.casc.lang.compilation.AbstractPreference
import org.objectweb.asm.Opcodes
import java.lang.reflect.Modifier

data class ClassType(
    override val typeName: String,
    val parentClassName: String?,
    override val accessor: Accessor,
    val mutable: Boolean,
    val isTrait: Boolean,
    override val internalName: String = typeName.replace('.', '/'),
    override val descriptor: String = "L$internalName;"
) : Type, HasAccessor {
    companion object {
        val OBJECT_TYPE = ClassType(Any::class.java)
    }

    constructor(clazz: Class<*>) : this(
        clazz.typeName,
        clazz.superclass?.typeName,
        Accessor.fromModifier(clazz.modifiers),
        Modifier.isFinal(clazz.modifiers),
        clazz.isInterface
    )

    override fun type(preference: AbstractPreference): Class<*>? = try {
        preference.classLoader.loadClass(typeName)
    } catch (e: Exception) {
        null
    }

    override fun asCASCStyle(): String =
        getReference().asCASCStyle()

    override val loadOpcode: Int = Opcodes.ALOAD
    override val storeOpcode: Int = Opcodes.ASTORE
    override val returnOpcode: Int = Opcodes.ARETURN
}
