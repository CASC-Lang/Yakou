package org.casclang.casc.bytecode.expression

import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes
import org.casclang.casc.parsing.node.expression.Value
import org.casclang.casc.util.TypeResolver

class ValueFactory(private val mv: MethodVisitor) {
    fun generate(value: Value) {
        val type = value.type
        val stringValue = value.value
        val actualValue = TypeResolver.getValueByString(stringValue, type)

        if (actualValue == null) {
            mv.visitInsn(Opcodes.ACONST_NULL)
        } else {
            mv.visitLdcInsn(actualValue)
        }
    }
}