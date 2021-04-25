package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.exception.FunctionAbsenceException
import io.github.chaosunity.casc.exception.InvalidComparisonException
import io.github.chaosunity.casc.parsing.expression.*
import io.github.chaosunity.casc.parsing.math.*
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.util.DescriptorFactory
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type

class ExpressionFactory(private val mv: MethodVisitor, private val scope: Scope) {
    fun generate(expression: Expression) {
        when (expression) {
            is VarReference -> generate(expression)
            is Value -> generate(expression)
            is FunctionCall -> generate(expression)
            is FunctionParameter -> generate(expression)
            is Addition -> generate(expression)
            is Subtraction -> generate(expression)
            is Multiplication -> generate(expression)
            is Division -> generate(expression)
            is ConditionalExpression -> generate(expression)
            is EmptyExpression -> {}
            is ArithmeticExpression -> {
                generate(expression.leftExpression())
                generate(expression.rightExpression())
            }
        }
    }

    fun generate(reference: VarReference) {
        val variableName = reference.variableName()
        val index = scope.getLocalVariableIndex(variableName)
        val localVariable = scope.getLocalVariable(variableName)
        val type = localVariable.type()

        if (type == BuiltInType.INT() || type == BuiltInType.BOOLEAN()) {
            mv.visitVarInsn(ILOAD, index)
        } else if (type == BuiltInType.STRING()) {
            mv.visitVarInsn(ALOAD, index)
        }
    }

    fun generate(value: Value) {
        val type = value.type()
        var stringValue = value.value()

        if (type == BuiltInType.INT() || type == BuiltInType.BOOLEAN()) {
            mv.visitIntInsn(BIPUSH, stringValue.toInt())
        } else if (type == BuiltInType.STRING()) {
            stringValue = stringValue.removePrefix("\"").removeSuffix("\"")
            mv.visitLdcInsn(stringValue)
        }
    }

    fun generate(call: FunctionCall) {
        val parameters = call.params()

        parameters.forEach { generate(it) }

        val owner = call.owner().getOrElse { ClassType(scope.className()) }
        val methodDescriptor = getFunctionDescriptor(call)
        val ownerDescriptor = owner.internalName()
        val functionName = call.functionName()

        mv.visitMethodInsn(INVOKESTATIC, ownerDescriptor, functionName, methodDescriptor, false)
    }

    fun generate(parameter: FunctionParameter) {
        val type = parameter.type()
        val index = scope.getLocalVariableIndex(parameter.name())

        if (type == BuiltInType.INT()) {
            mv.visitVarInsn(ILOAD, index)
        } else if (type == BuiltInType.STRING()) {
            mv.visitVarInsn(ALOAD, index)
        }
    }

    fun generate(addition: Addition) {
        generate(addition.leftExpression())
        generate(addition.rightExpression())
        mv.visitInsn(IADD)
    }

    fun generate(subtraction: Subtraction) {
        generate(subtraction.leftExpression())
        generate(subtraction.rightExpression())
        mv.visitInsn(ISUB)
    }

    fun generate(multiplication: Multiplication) {
        generate(multiplication.leftExpression())
        generate(multiplication.rightExpression())
        mv.visitInsn(IMUL)
    }

    fun generate(division: Division) {
        generate(division.leftExpression())
        generate(division.rightExpression())
        mv.visitInsn(IDIV)
    }

    fun generate(conditional: ConditionalExpression) {
        val left = conditional.left()
        val right = conditional.right()
        val type = left.type()
        val opCode = conditional.opCode()

        if (type != right.type())
            throw InvalidComparisonException(left, right, opCode)

        generate(left)
        generate(right)

        val endLabel = Label()
        val trueLabel = Label()

        mv.visitJumpInsn(opCode.opCode(), trueLabel)
        mv.visitInsn(ICONST_0)
        mv.visitJumpInsn(GOTO, endLabel)
        mv.visitLabel(trueLabel)
        mv.visitInsn(ICONST_1)
        mv.visitLabel(endLabel)
    }

//    private fun generate(emptyExpression: EmptyExpression) {}         Not necessary.

    private fun getFunctionDescriptor(call: FunctionCall): String =
        getDescriptorForFunctionInScope(call) ?: getDescriptorForFunctionOnClasspath(call)
        ?: throw FunctionAbsenceException(call)

    private fun getDescriptorForFunctionInScope(call: FunctionCall): String? =
        DescriptorFactory.getMethodDescriptor(call.signature())

    private fun getDescriptorForFunctionOnClasspath(call: FunctionCall): String? = try {
        val functionName = call.functionName()
        val owner = call.owner()
        val className = owner.getOrElse { scope.className() }
        val clazz = Class.forName(className)
        val method = clazz.getMethod(functionName)

        Type.getMethodDescriptor(method)
    } catch (e: ReflectiveOperationException) {
        null
    }
}