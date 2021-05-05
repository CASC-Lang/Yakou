package io.github.chaosunity.casc.bytecode.expression

import io.github.chaosunity.casc.parsing.node.expression.*
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.util.DescriptorFactory
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class CallFactory(private val ef: ExpressionFactory, private val scope: Scope, private val mv: MethodVisitor) {
    fun generate(constructor: ConstructorCall) {
        val ownerDescriptor = scope.classInternalName

        mv.visitTypeInsn(NEW, ownerDescriptor)
        mv.visitInsn(DUP)

        val methodCallSignature = scope.getMethodCallSignature(constructor.identifier, constructor.arguments)
        val methodDescriptor = DescriptorFactory.getMethodDescriptor(methodCallSignature)

        generateArguments(constructor)
        mv.visitMethodInsn(INVOKESPECIAL, ownerDescriptor, "<init>", methodDescriptor, false)
    }

    fun generate(superCall: SuperCall) {
        mv.visitVarInsn(ALOAD, 0)
        generateArguments(superCall)

        val ownerDescriptor = scope.superClassInternalName

        mv.visitMethodInsn(INVOKESPECIAL, ownerDescriptor, "<init>", "()V", false)
    }

    fun generate(function: FunctionCall) {
        function.owner.accept(ef)
        generateArguments(function)

        val functionName = function.identifier
        val methodDescriptor = DescriptorFactory.getMethodDescriptor(function.signature)
        val ownerDescriptor = function.type.internalName

        mv.visitMethodInsn(INVOKEVIRTUAL, ownerDescriptor, functionName, methodDescriptor, false)
    }

    fun generateArguments(call: Call<*>) {
        val signature = scope.getMethodCallSignature(call.identifier, call.arguments)
        val parameters = signature.parameters
        var arguments = call.arguments

        if (arguments.size > parameters.size) throw RuntimeException("Argument size mismatch to parameter size.\nArguments: $arguments\nParameter: $parameters")

        arguments = getSortedArguments(arguments, parameters)
        arguments.forEach { it.accept(ef) }
        generateDefaultParameter(call, parameters, arguments)
    }

    private fun getSortedArguments(arguments: List<Argument>, parameters: List<Parameter>): List<Argument> =
        arguments.sortedWith { o1, o2 ->
            if (o1.argumentName == null) 0 else getIndexOfParameter(
                o1,
                parameters
            ) - getIndexOfParameter(o2, parameters)
        }

    private fun getIndexOfParameter(argument: Argument, parameters: List<Parameter>): Int {
        val parameterName = argument.argumentName

        return parameters.filter { it.name == parameterName }
            .map(parameters::indexOf)
            .firstOrNull() ?: throw RuntimeException("Parameter '$parameterName' does not exist.")
    }

    private fun generateDefaultParameter(call: Call<*>, parameters: List<Parameter>, arguments: List<Argument>) {
        for (i in arguments.size until parameters.size) {
            val defaultParameter =
                parameters[i].defaultValue
                    ?: throw RuntimeException("Bad arguments match to parameters.\nDetail: $call")
            defaultParameter.accept(ef)
        }
    }
}