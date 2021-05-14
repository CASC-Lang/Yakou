package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.node.statement.Assignment
import io.github.chaosunity.casc.parsing.scope.Scope
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes

class AssignmentFactory(private val mv: MethodVisitor, private val ef: ExpressionFactory, private val scope: Scope) {
    fun generate(assignment: Assignment, initialAssignment: Boolean = false) {
        val variableName = assignment.variableName
        val expression = assignment.expression
        val type = expression.type

        if (scope.isLocalVariableExists(variableName)) {
            if (!initialAssignment && scope.getLocalVariable(variableName).immutable)
                throw RuntimeException("Cannot assign value to immutable variable '$variableName'.")

            val index = scope.getLocalVariableIndex(variableName)

            if (!initialAssignment) expression.accept(ef)
            mv.visitVarInsn(type.storeVariableOpcode, index)

            return
        }

        val field = scope.getField(variableName)
        val descriptor = field.type.descriptor

        mv.visitVarInsn(Opcodes.ALOAD, 0)
        expression.accept(ef)
        mv.visitFieldInsn(Opcodes.PUTFIELD, field.ownerType.internalName, field.name, descriptor)
    }
}