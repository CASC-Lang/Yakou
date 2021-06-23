package org.casclang.casc.bytecode.statement

import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*
import org.casclang.casc.bytecode.expression.ExpressionFactory
import org.casclang.casc.parsing.node.expression.Expression
import org.casclang.casc.parsing.node.expression.FieldCall
import org.casclang.casc.parsing.node.expression.Index
import org.casclang.casc.parsing.node.expression.Reference
import org.casclang.casc.parsing.node.statement.Assignment
import org.casclang.casc.parsing.scope.CallingScope
import org.casclang.casc.parsing.scope.Scope

class AssignmentFactory(private val mv: MethodVisitor, private val ef: ExpressionFactory, private val scope: Scope) {
    fun generate(assignment: Assignment) {
        val variableName = assignment.variableName
        val expression = assignment.expression
        val type = expression.type
        val initialAssignment = assignment.initialAssignment

        if (variableName != null && scope.isLocalVariableExists(variableName)) {
            if (!initialAssignment && scope.getLocalVariable(variableName)!!.immutable)
                throw RuntimeException("Cannot assign value to immutable variable '$variableName'.")

            val index = scope.getLocalVariableIndex(variableName)

            if (!initialAssignment) expression.accept(ef)

            when (val variableReference = assignment.variableReference) {
                is Reference<*> -> {
                    mv.visitVarInsn(type.storeVariableOpcode, index)
                }
                is Index -> {
                    val storeType = variableReference.type

                    ef.generate(variableReference, false)
                    expression.accept(ef)

                    mv.visitInsn(storeType.arrayStoreOpcode)
                }
                else -> throw RuntimeException("Unsupported assignment operation.")
            }

            return
        }

        when (val variableReference = assignment.variableReference) {
            is Reference<*> -> {
                val field = scope.getField(variableReference.name)!!
                val ownerTypeInternalName = field.ownerType.internalName
                val fieldName = field.name
                val descriptor = field.type.descriptor
                val callingScope = assignment.callingScope

                when (callingScope) {
                    CallingScope.CONSTRUCTOR -> {
                        if (!initialAssignment && field.initialized && field.immutable)
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
            is Index -> {
                val storeType = variableReference.type

                ef.generate(variableReference, false)
                expression.accept(ef)

                mv.visitInsn(storeType.arrayStoreOpcode)
            }
            else -> throw RuntimeException("Unsupported assignment operation.")
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
            CallingScope.CONSTRUCTOR, CallingScope.OBJECT -> {
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