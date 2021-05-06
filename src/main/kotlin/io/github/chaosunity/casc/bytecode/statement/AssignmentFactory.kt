package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.parsing.node.statement.Assignment
import io.github.chaosunity.casc.parsing.scope.Scope
import jdk.internal.org.objectweb.asm.MethodVisitor

class AssignmentFactory(private val mv: MethodVisitor, private val scope: Scope) {
    fun generate(assignment: Assignment) {
        val variableName= assignment.variableName
        val type = assignment.expression.type
        val index = scope.getLocalVariableIndex(variableName)

        mv.visitVarInsn(type.storeVariableOpcode, index)
    }
}