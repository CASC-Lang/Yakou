package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.node.statement.IfStatement
import jdk.internal.org.objectweb.asm.Label
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.GOTO
import jdk.internal.org.objectweb.asm.Opcodes.IFNE

class IfFactory(private val sf: StatementFactory, private val ef: ExpressionFactory, private val mv: MethodVisitor) {
    fun generate(ifStatement: IfStatement) {
        val condition = ifStatement.condition

        condition.accept(ef)

        val trueLabel = Label()
        val endLabel = Label()

        mv.visitJumpInsn(IFNE, trueLabel)

        val falseStatement = ifStatement.falseStatement

        falseStatement?.accept(sf)

        mv.visitJumpInsn(GOTO, endLabel)
        mv.visitLabel(trueLabel)
        ifStatement.trueStatement.accept(sf)
        mv.visitLabel(endLabel)
    }
}