package org.casclang.casc.bytecode.statement

import jdk.internal.org.objectweb.asm.MethodVisitor
import org.casclang.casc.bytecode.expression.ExpressionFactory
import org.casclang.casc.parsing.node.statement.ReturnStatement

class ReturnFactory(private val ef: ExpressionFactory, private val mv: MethodVisitor) {
    fun generate(returnStatement: ReturnStatement) {
        val expression = returnStatement.expression
        val type = expression.type

        expression.accept(ef)
        mv.visitInsn(type.returnOpcode)
    }
}