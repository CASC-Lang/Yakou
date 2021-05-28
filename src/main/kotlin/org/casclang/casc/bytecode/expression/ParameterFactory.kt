package org.casclang.casc.bytecode.expression

import org.casclang.casc.parsing.node.expression.Parameter
import org.casclang.casc.parsing.scope.Scope
import jdk.internal.org.objectweb.asm.MethodVisitor

class ParameterFactory(private val mv: MethodVisitor, private val scope: Scope) {
    fun generate(parameter: Parameter) {
        val type = parameter.type
        val index = scope.getLocalVariableIndex(parameter.name)

        mv.visitVarInsn(type.loadVariableOpcode, index)
    }
}