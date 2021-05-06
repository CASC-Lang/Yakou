package io.github.chaosunity.casc.bytecode.expression

import io.github.chaosunity.casc.parsing.node.expression.VariableReference
import io.github.chaosunity.casc.parsing.scope.Scope
import jdk.internal.org.objectweb.asm.MethodVisitor

class VariableReferenceFactory(private val mv: MethodVisitor, private val scope: Scope) {
    fun generate(ref: VariableReference) {
        val name = ref.variableName
        val index = scope.getLocalVariableIndex(name)
        val type = scope.getLocalVariable(name).type

        mv.visitVarInsn(type.loadVariableOpcode, index)
    }
}