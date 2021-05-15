package io.github.chaosunity.casc.bytecode

import io.github.chaosunity.casc.parsing.scope.Field
import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.Opcodes.ACC_STATIC

class FieldFactory(private val cw: ClassWriter) {
    fun generate(field: Field) {
        val name = field.name
        val descriptor = field.type.descriptor
        val fieldVisitor = cw.visitField(field.accessModifier.accessOpcode + field.finalOpcode + if (field.static) ACC_STATIC else 0, name, descriptor, null, null)

        fieldVisitor.visitEnd()
    }
}