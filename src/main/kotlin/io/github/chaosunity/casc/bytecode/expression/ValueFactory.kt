package io.github.chaosunity.casc.bytecode.expression

import io.github.chaosunity.casc.parsing.node.expression.Value
import io.github.chaosunity.casc.util.TypeResolver
import jdk.internal.org.objectweb.asm.MethodVisitor

class ValueFactory(private val mv: MethodVisitor) {
    fun generate(value: Value) {
        val type = value.type
        val stringValue = value.value
        val actualValue = TypeResolver.getValueByString(stringValue, type)

        mv.visitLdcInsn(actualValue)
    }
}