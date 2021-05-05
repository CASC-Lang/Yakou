package io.github.chaosunity.casc.bytecode.expression

import io.github.chaosunity.casc.parsing.node.expression.Parameter
import io.github.chaosunity.casc.parsing.scope.Scope
import jdk.internal.org.objectweb.asm.MethodVisitor

class ParameterFactory(private val mv: MethodVisitor, private val scope: Scope) {
    fun generate(parameter: Parameter) {
        val type = parameter.type
        val index = scope.getIndexOfLocalVariable(parameter.name)

        mv.visitVarInsn(type.loadVariableOpcode, index)
    }
}