package io.github.chaosunity.casc.bytecode

import io.github.chaosunity.casc.parsing.scope.Field
import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.Opcodes.*

class FieldFactory(private val cw: ClassWriter) {
    fun generate(field: Field) {
        val name = field.name
        val descriptor = field.type.descriptor
        val fieldVisitor = cw.visitField(ACC_PUBLIC, name, descriptor, null, null)

        fieldVisitor.visitEnd()
    }
}