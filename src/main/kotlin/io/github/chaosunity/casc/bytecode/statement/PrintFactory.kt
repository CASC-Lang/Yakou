package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.node.statement.OutputStatement
import io.github.chaosunity.casc.parsing.node.statement.PrintStatement
import io.github.chaosunity.casc.parsing.node.statement.PrintlnStatement
import io.github.chaosunity.casc.parsing.type.ClassType
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.GETSTATIC
import jdk.internal.org.objectweb.asm.Opcodes.INVOKEVIRTUAL


class PrintFactory(private val ef: ExpressionFactory, private val mv: MethodVisitor) {
    fun generate(printStatement: PrintStatement) =
        preGenerate(printStatement, "print")

    fun generate(printlnStatement: PrintlnStatement) =
        preGenerate(printlnStatement, "println")

    private fun preGenerate(printable: OutputStatement<*>, methodName: String) {
        val expression = printable.expression
        val type = expression.type

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        expression.accept(ef)

        val descriptor = if (type.isBuiltInType()) "(${type.descriptor})V" else "(Ljava/lang/Object;)V"
        val owner = ClassType("java.io.PrintStream")
        val fieldDescriptor = owner.internalName

        mv.visitMethodInsn(INVOKEVIRTUAL, fieldDescriptor, methodName, descriptor, false)
    }
}