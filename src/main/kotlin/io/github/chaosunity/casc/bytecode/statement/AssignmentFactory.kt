package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.node.expression.Reference
import io.github.chaosunity.casc.parsing.node.statement.Assignment
import io.github.chaosunity.casc.parsing.scope.CallingScope
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.ArrayType
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class AssignmentFactory(private val mv: MethodVisitor, private val ef: ExpressionFactory, private val scope: Scope) {
    fun generate(assignment: Assignment, initialAssignment: Boolean = false) {
        val variableName = assignment.variableName
        val expression = assignment.expression
        val type = expression.type

        if (scope.isLocalVariableExists(variableName)) {
            if (assignment.dimensions.isEmpty()) {
                if (!initialAssignment && scope.getLocalVariable(variableName).immutable)
                    throw RuntimeException("Cannot assign value to immutable variable '$variableName'.")

                val index = scope.getLocalVariableIndex(variableName)

                if (!initialAssignment) expression.accept(ef)

                if (expression is Reference<*>) {
                    val referencedType = if (scope.isFieldExists(expression.name)) scope.getField(expression.name).type
                    else scope.getLocalVariable(expression.name).type

                    if (referencedType is ArrayType) {
                        val assignableDimension = referencedType.dimension - expression.dimensions.size
                        val assignableType = if (assignableDimension == 0) referencedType.baseType
                        else ArrayType(referencedType.baseType, assignableDimension)

                        mv.visitVarInsn(assignableType.storeVariableOpcode, index)

                        return
                    }
                }

                mv.visitVarInsn(type.storeVariableOpcode, index)

                return
            } else {
                val variableType = if (scope.isFieldExists(variableName)) scope.getField(variableName).type
                else scope.getLocalVariable(variableName).type

                if (variableType !is ArrayType)
                    throw RuntimeException("Type $variableType cannot be indexed.")

                val assignableDimension = variableType.dimension - assignment.dimensions.size
                val assignableType = if (assignableDimension == 0) variableType.baseType
                else ArrayType(variableType.baseType, assignableDimension)

                if (expression.type != assignableType)
                    throw RuntimeException("Cannot cast type ${expression.type} into $assignableType.")

                val index = scope.getLocalVariableIndex(variableName)
                val dimensions = assignment.dimensions.toMutableList()
                val lastDimension = dimensions.removeLast()

                mv.visitVarInsn(ALOAD, index)

                dimensions.forEach {
                    it.accept(ef)
                    mv.visitInsn(AALOAD)
                }

                lastDimension.accept(ef)
                expression.accept(ef)

                mv.visitInsn(assignableType.arrayStoreOpcode)

                return
            }
        }

        val field = scope.getField(variableName)
        val descriptor = field.type.descriptor

        if (!initialAssignment && field.immutable && assignment.callingScope != CallingScope.CONSTRUCTOR)
            throw RuntimeException("Cannot assign value to immutable field '$variableName'.")

        mv.visitVarInsn(ALOAD, 0)

        if (assignment.dimensions.isEmpty()) {
            expression.accept(ef)

            mv.visitFieldInsn(PUTFIELD, field.ownerType.internalName, field.name, descriptor)
        } else {
            mv.visitFieldInsn(GETFIELD, field.ownerType.internalName, field.name, descriptor)

            val variableType = if (scope.isFieldExists(variableName)) scope.getField(variableName).type
            else scope.getLocalVariable(variableName).type

            if (variableType !is ArrayType)
                throw RuntimeException("Type $variableType cannot be indexed.")

            val assignableDimension = variableType.dimension - assignment.dimensions.size
            val assignableType = if (assignableDimension == 0) variableType.baseType
            else ArrayType(variableType.baseType, assignableDimension)

            if (expression.type != assignableType)
                throw RuntimeException("Cannot cast type ${expression.type} into $assignableType.")

            val dimensions = assignment.dimensions.toMutableList()
            val lastDimension = dimensions.removeLast()

            dimensions.forEach {
                it.accept(ef)
                mv.visitInsn(AALOAD)
            }

            lastDimension.accept(ef)
            expression.accept(ef)

            mv.visitInsn(assignableType.arrayStoreOpcode)
        }
    }
}