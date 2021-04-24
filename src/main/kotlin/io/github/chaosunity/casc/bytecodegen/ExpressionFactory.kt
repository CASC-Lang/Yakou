package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.exception.FunctionAbsenceException
import io.github.chaosunity.casc.parsing.expression.*
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.util.DescriptorFactory
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type

class ExpressionFactory(private val mv: MethodVisitor) {
    fun generate(expression: Expression, scope: Scope) {
        when (expression) {
            is VarReference -> generate(expression, scope)
            is Value -> generate(expression, scope)
            is FunctionCall -> generate(expression, scope)
            is FunctionParameter -> generate(expression, scope)
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

    fun generate(value: Value, scope: Scope) {
        val type = value.type()
        val stringValue = value.value()

        if (type == BuiltInType.INT()) {
            mv.visitIntInsn(BIPUSH, stringValue.toInt())
        } else if (type == BuiltInType.STRING()) {
            mv.visitLdcInsn(stringValue)
        }
    }

    fun generate(call: FunctionCall, scope: Scope) {
        val parameters = call.parameters()

        parameters.forEach { generate(it, scope) }

        val owner = call.owner().getOrElse { ClassType(scope.className()) }
        val methodDescriptor = getFunctionDescriptor(call, scope)
        val ownerDescriptor = owner.internalName()
        val functionName = call.functionName()

        mv.visitMethodInsn(INVOKESTATIC, ownerDescriptor, functionName, methodDescriptor, false)
    }

    fun generate(parameter: FunctionParameter, scope: Scope) {
        val type = parameter.type()
        val index = scope.getLocalVariableIndex(parameter.name())

        if (type == BuiltInType.INT()) {
            mv.visitVarInsn(ILOAD, index)
        } else if (type == BuiltInType.STRING()) {
            mv.visitVarInsn(ALOAD, index)
        }
    }

    private fun getFunctionDescriptor(call: FunctionCall, scope: Scope): String =
        getDescriptorForFunctionInScope(call, scope) ?: getDescriptorForFunctionOnClasspath(call, scope)
        ?: throw FunctionAbsenceException(call)

    private fun getDescriptorForFunctionInScope(call: FunctionCall, scope: Scope): String? =
        DescriptorFactory.getMethodDescriptor(call.signature())

    private fun getDescriptorForFunctionOnClasspath(call: FunctionCall, scope: Scope): String? = try {
        val functionName = call.functionName()
        val parameters = call.parameters()
        val owner = call.owner()
        val className = owner.getOrElse { scope.className() }
        val clazz = Class.forName(className)
        val method = clazz.getMethod(functionName)

        Type.getMethodDescriptor(method)
    } catch (e: ReflectiveOperationException) {
        null
    }
}