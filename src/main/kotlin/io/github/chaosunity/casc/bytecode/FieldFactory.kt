package io.github.chaosunity.casc.bytecode

import io.github.chaosunity.casc.parsing.scope.Field
import jdk.internal.org.objectweb.asm.ClassWriter

class FieldFactory(private val cw: ClassWriter) {
    fun generate(field: Field) {
        val name = field.name
        val descriptor = field.type.descriptor
        val fieldVisitor = cw.visitField(field.accessModifier.accessOpcode + field.finalOpcode, name, descriptor, null, null)

        fieldVisitor.visitEnd()
    }
}