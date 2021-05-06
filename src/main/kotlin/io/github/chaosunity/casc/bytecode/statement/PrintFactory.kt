package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.node.statement.OutputStatement
import io.github.chaosunity.casc.parsing.node.statement.PrintStatement
import io.github.chaosunity.casc.parsing.node.statement.PrintlnStatement
import io.github.chaosunity.casc.parsing.type.ClassType
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*


class PrintFactory(private val ef: ExpressionFactory, private val mv: MethodVisitor) {
    fun generate(printStatement: PrintStatement) {
        val (fieldDescriptor, descriptor) = preGenerate(printStatement)

        mv.visitMethodInsn(INVOKEVIRTUAL, fieldDescriptor, "print", descriptor, false)
    }

    fun generate(printlnStatement: PrintlnStatement) {
        val (fieldDescriptor, descriptor) = preGenerate(printlnStatement)

        mv.visitMethodInsn(INVOKEVIRTUAL, fieldDescriptor, "println", descriptor, false)
    }

    private fun preGenerate(printable: OutputStatement<*>): Pair<String, String> {
        val expression = printable.expression

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        expression.accept(ef)

        val descriptor = "(${expression.type.descriptor})V"
        val owner = ClassType("java.io.PrintStream")
        val fieldDescriptor = owner.internalName

        return fieldDescriptor to descriptor
    }
}