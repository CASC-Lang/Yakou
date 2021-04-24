package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.parsing.expression.*
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.BuiltInType
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ALOAD
import org.objectweb.asm.Opcodes.ILOAD

class ExpressionFactory(private val mv: MethodVisitor) {
    fun generate(expression: Expression, scope: Scope) {
        when (expression) {
            is VarReference -> {

            }
            is Value -> {

            }
            is FunctionCall -> {

            }
            is FunctionParameter -> {

            }
        }
    }

    fun generate(reference: VarReference, scope: Scope) {
        val variableName = reference.variableName()
        val index = scope.getLocalVariableIndex(variableName)
        val localVariable = scope.getLocalVariable(variableName)
        val type = localVariable.type()

        if (type == BuiltInType.INT()) {
            mv.visitVarInsn(ILOAD, index)
        } else if (type == BuiltInType.STRING()) {
            mv.visitVarInsn(ALOAD, index)
        }
    }
}