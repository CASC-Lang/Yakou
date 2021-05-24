package io.github.chaosunity.casc.bytecode.expression

import io.github.chaosunity.casc.parsing.node.expression.FieldReference
import io.github.chaosunity.casc.parsing.node.expression.LocalVariableReference
import io.github.chaosunity.casc.parsing.scope.CallingScope
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.ArrayType
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class ReferenceFactory(private val mv: MethodVisitor, private val ef: ExpressionFactory, private val scope: Scope) {
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

        if (scope.callingScope == CallingScope.OBJECT) {
            mv.visitVarInsn(ALOAD, 0)
            mv.visitFieldInsn(GETFIELD, ownerInternalName, varName, descriptor)
        } else {
            mv.visitFieldInsn(GETSTATIC, scope.classType.internalName, varName, descriptor)
        }
    }
}