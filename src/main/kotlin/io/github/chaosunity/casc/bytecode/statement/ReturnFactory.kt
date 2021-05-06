package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.node.statement.ReturnStatement
import jdk.internal.org.objectweb.asm.MethodVisitor

class ReturnFactory(private val ef: ExpressionFactory, private val mv: MethodVisitor) {
    fun generate(returnStatement: ReturnStatement) {
        val expression = returnStatement.expression
        val type = expression.type

        expression.accept(ef)
        mv.visitInsn(type.returnOpcode)
    }
}