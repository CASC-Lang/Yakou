package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.parsing.`class`.Function
import io.github.chaosunity.casc.parsing.expression.EmptyExpression
import io.github.chaosunity.casc.parsing.statement.BlockStatement
import io.github.chaosunity.casc.parsing.statement.ReturnStatement
import io.github.chaosunity.casc.util.DescriptorFactory
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*

class MethodFactory(private val cw: ClassWriter) {
    fun generate(function: Function) {
        val functionName = if (function.name().equals("主函式")) "main" else function.name()
        val descriptor = DescriptorFactory.getMethodDescriptor(function)
        val block = function.rootStatement() as BlockStatement
        val access = ACC_PUBLIC + ACC_STATIC
        val mv = cw.visitMethod(access, functionName, descriptor, null, null)

        mv.visitCode()

        val scope = block.scope()

        run {
            val sf = StatementFactory(mv, scope)

            sf.generate(block)

            appendReturnIfAbsence(function, block, sf)
            mv.visitMaxs(-1, -1)
        }

        mv.visitEnd()
    }

    private fun appendReturnIfAbsence(
        function: Function,
        block: BlockStatement,
        statementFactory: StatementFactory
    ) {
        val lastStatement = block.statements().last()

        if (lastStatement !is ReturnStatement) {
            val emptyExpression = EmptyExpression(function.returnType())
            val returnExpression = ReturnStatement(emptyExpression)

            statementFactory.generate(returnExpression)
        }
    }
}