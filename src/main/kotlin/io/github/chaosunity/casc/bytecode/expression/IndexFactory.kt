package io.github.chaosunity.casc.bytecode.expression

import io.github.chaosunity.casc.parsing.node.expression.Index
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*
import javax.lang.model.type.ArrayType

class IndexFactory(private val mv: MethodVisitor, private val ef: ExpressionFactory) {
    fun generate(index: Index, load: Boolean) {
        index.expression.accept(ef)

        index.indexExpression.accept(ef)

        if (!load) return
        if (index.type is ArrayType) {
            mv.visitInsn(AALOAD)
        } else {
            mv.visitInsn(index.type.arrayLoadOpcode)
        }
    }
}