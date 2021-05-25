package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.node.expression.Expression
import io.github.chaosunity.casc.parsing.node.expression.FieldCall
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
        val variableReference = assignment.variableReference
        val expression = assignment.expression
        val type = expression.type

        if (variableName != null && scope.isLocalVariableExists(variableName)) {
            if (!initialAssignment && scope.getLocalVariable(variableName).immutable)
                throw RuntimeException("Cannot assign value to immutable variable '$variableName'.")

            val index = scope.getLocalVariableIndex(variableName)

            if (!initialAssignment) expression.accept(ef)
            mv.visitVarInsn(type.storeVariableOpcode, index)

            return
        }

        val callingScope = assignment.callingScope

        when (variableReference) {
            is Reference<*> -> {
                val field = scope.getField(variableReference.name)
                val ownerTypeInternalName = field.ownerType.internalName
                val fieldName = field.name
                val descriptor = field.type.descriptor

                when (callingScope) {
                    CallingScope.CONSTRUCTOR -> {
                        if (!initialAssignment && field.immutable)
                            throw RuntimeException("Cannot assign value to immutable field '$variableName'.")
                    }
                    CallingScope.OBJECT -> {
                        if (initialAssignment)
                            throw RuntimeException("Cannot initialize field in object's function.")

                        if (field.immutable)
                            throw RuntimeException("Cannot assign value to immutable field '$variableName'.")
                    }
                    CallingScope.STATIC -> {
                        if (initialAssignment)
                            throw RuntimeException("Cannot initialize static field in object's function.")

                        if (field.immutable)
                            throw RuntimeException("Cannot assign value to immutable field '$variableName'.")
                    }
                }

                generateVariableReferenceAssignment(
                    expression,
                    ownerTypeInternalName,
                    fieldName,
                    descriptor,
                    callingScope
                )
            }
            is FieldCall -> {
                val ownerType = variableReference.owner.type

                if (!variableReference.static)
                    variableReference.owner.accept(ef)

                expression.accept(ef)

                mv.visitFieldInsn(
                    if (variableReference.static) PUTSTATIC else PUTFIELD,
                    ownerType.internalName,
                    variableReference.identifier,
                    variableReference.type.descriptor
                )
            }
            else -> {
                variableReference.accept(ef)

                expression.accept(ef)

            }
        }
    }

    private fun generateVariableReferenceAssignment(
        rightExpression: Expression<*>,
        ownerTypeInternalName: String,
        fieldName: String,
        descriptor: String,
        callingScope: CallingScope
    ) {
        when (callingScope) {
            CallingScope.CONSTRUCTOR -> {
                mv.visitVarInsn(ALOAD, 0)
                rightExpression.accept(ef)
                mv.visitFieldInsn(PUTFIELD, ownerTypeInternalName, fieldName, descriptor)
            }
            CallingScope.OBJECT -> {
                mv.visitVarInsn(ALOAD, 0)
                rightExpression.accept(ef)
                mv.visitFieldInsn(PUTFIELD, ownerTypeInternalName, fieldName, descriptor)
            }
            CallingScope.STATIC -> {
                rightExpression.accept(ef)
                mv.visitFieldInsn(PUTSTATIC, ownerTypeInternalName, fieldName, descriptor)
            }
        }
    }
}