package io.github.chaosunity.casc.bytecode.expression

import io.github.chaosunity.casc.parsing.node.expression.FieldReference
import io.github.chaosunity.casc.parsing.node.expression.LocalVariableReference
import io.github.chaosunity.casc.parsing.scope.Scope
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.ALOAD
import jdk.internal.org.objectweb.asm.Opcodes.GETFIELD

class ReferenceFactory(private val mv: MethodVisitor, private val scope: Scope) {
    fun generate(local: LocalVariableReference) {
        val varName = local.name
        val index = scope.getLocalVariableIndex(varName)
        val type = local.type

        mv.visitVarInsn(type.loadVariableOpcode, index)
    }

    fun generate(field: FieldReference) {
        val varName = field.name
        val type = field.type
        val ownerInternalName = field.type.internalName
        val descriptor = type.descriptor

        mv.visitVarInsn(ALOAD, 0)
        mv.visitFieldInsn(GETFIELD, ownerInternalName, varName, descriptor)
    }
}