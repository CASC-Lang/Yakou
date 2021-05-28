package org.casclang.casc.bytecode.expression

import org.casclang.casc.parsing.node.expression.ArrayInitialization
import org.casclang.casc.parsing.node.expression.Expression
import org.casclang.casc.parsing.type.ArrayType
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.Type
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class ArrayInitializationFactory(private val mv: MethodVisitor, private val ef: ExpressionFactory) {
    fun generate(init: ArrayInitialization) {
        val arrayLength = init.expressions.size
        val type = init.type
        val nextType = if (type.dimension == 1) type.baseType
        else ArrayType(type.baseType, type.dimension - 1)

        mv.visitLdcInsn(arrayLength)

        generateArray(init.type.dimension, nextType, init.expressions)
    }

    private fun generateArray(depth: Int, type: Type, expressions: List<Expression<*>>) {
        if (depth == 1) {
            if (type.isBuiltInType()) {
                mv.visitIntInsn(NEWARRAY, (type as BuiltInType).typeOpcode)
            } else {
                mv.visitTypeInsn(ANEWARRAY, type.internalName)
            }
        } else {
            mv.visitTypeInsn(ANEWARRAY, type.descriptor)
        }

        mv.visitInsn(DUP)

        expressions.forEachIndexed { i, it ->
            mv.visitLdcInsn(i)
            it.accept(ef)
            mv.visitInsn(type.arrayStoreOpcode)
            if (i != expressions.size - 1) mv.visitInsn(DUP)
        }
    }
}