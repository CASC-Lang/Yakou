package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.exception.BadArgumentsToFunctionCallException
import io.github.chaosunity.casc.exception.FunctionAbsenceException
import io.github.chaosunity.casc.exception.InvalidNegativeException
import io.github.chaosunity.casc.parsing.expression.*
import io.github.chaosunity.casc.parsing.expression.math.ArithmeticExpression
import io.github.chaosunity.casc.parsing.expression.math.ArithmeticExpression.*
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.util.DescriptorFactory
import io.github.chaosunity.casc.util.TypeResolver
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
            is IfExpression -> generate(expression)
            is ConditionalExpression -> generate(expression)
            is EmptyExpression -> {
            }
            is ArithmeticExpression -> {
                generate(expression.leftExpression())
                generate(expression.rightExpression())
            }
        }

        if (expression.negative()) {
            if (expression.type() == BuiltInType.INT())
                mv.visitInsn(INEG)
            else
                throw InvalidNegativeException(expression.type())
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
        val stringValue = value.value()
        val actualValue = TypeResolver.getValueFromString(stringValue, type)

        mv.visitLdcInsn(actualValue)
    }

    fun generate(call: FunctionCall) {
        val functionName = call.functionName()
        val signature = scope.getSignature(functionName)
        val arguments = call.arguments()
        val parameters = signature.parameters()

        if (arguments.size > parameters.size)
            throw BadArgumentsToFunctionCallException(call)

        arguments.forEach(::generate)

        for (i in arguments.size until parameters.size) {
            val defaultParameter = parameters[i].defaultValue()

            if (defaultParameter.isEmpty)
                throw BadArgumentsToFunctionCallException(call)

            generate(defaultParameter.get())
        }

        val owner = call.owner().getOrElse { ClassType(scope.className()) }
        val methodDescriptor = getFunctionDescriptor(call)
        val ownerDescriptor = owner.internalName()

        mv.visitMethodInsn(INVOKESTATIC, ownerDescriptor, functionName, methodDescriptor, false)
    }

    fun generate(parameter: FunctionParameter) {
        val type = parameter.type()
        val index = scope.getLocalVariableIndex(parameter.name())

        mv.visitVarInsn(type.loadVariableOpcode(), index)
    }

    fun generate(addition: Addition) {
        if (addition.type() == BuiltInType.STRING()) {
            generateStringAppend(addition)

            return
        }

        generate(addition.leftExpression())
        generate(addition.rightExpression())

        mv.visitInsn(addition.type().addOpcode())
    }

    fun generate(subtraction: Subtraction) {
        generate(subtraction.leftExpression())
        generate(subtraction.rightExpression())

        mv.visitInsn(subtraction.type().subtractOpcode())
    }

    fun generate(multiplication: Multiplication) {
        generate(multiplication.leftExpression())
        generate(multiplication.rightExpression())

        mv.visitInsn(multiplication.type().multiplyOpcode())
    }

    fun generate(division: Division) {
        generate(division.leftExpression())
        generate(division.rightExpression())

        mv.visitInsn(division.type().divideOpcode())
    }

    fun generate(ifExpression: IfExpression) {
        val condition = ifExpression.condition()

        generate(condition)

        val trueLabel = Label()
        val endLabel = Label()

        mv.visitJumpInsn(IFEQ, trueLabel)
        generate(ifExpression.trueExpression())
        mv.visitJumpInsn(GOTO, endLabel)
        mv.visitLabel(trueLabel)
        generate(ifExpression.falseExpression())
        mv.visitLabel(endLabel)
    }

    fun generate(conditional: ConditionalExpression) {
        val left = conditional.left()
        val right = conditional.right()
        val opcode = conditional.opcode()

        generate(left)
        generate(right)

        val endLabel = Label()
        val trueLabel = Label()

        mv.visitJumpInsn(opcode.opcode(), trueLabel)
        mv.visitInsn(ICONST_0)
        mv.visitJumpInsn(GOTO, endLabel)
        mv.visitLabel(trueLabel)
        mv.visitInsn(ICONST_1)
        mv.visitLabel(endLabel)
    }

//    private fun generate(emptyExpression: EmptyExpression) {}         Not necessary.

    private fun generateStringAppend(addition: Addition) {
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder")
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)

        val leftExpression = addition.leftExpression()

        generate(leftExpression)

        val leftExprDescriptor = leftExpression.type().descriptor()
        var descriptor = "($leftExprDescriptor)Ljava/lang/StringBuilder;"
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false)
        val rightExpression = addition.rightExpression()

        generate(rightExpression)

        val rightExprDescriptor = rightExpression.type().descriptor()

        descriptor = "($rightExprDescriptor)Ljava/lang/StringBuilder;"
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false)
        mv.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/lang/StringBuilder",
            "toString",
            "()Ljava/lang/String;",
            false
        )
    }

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