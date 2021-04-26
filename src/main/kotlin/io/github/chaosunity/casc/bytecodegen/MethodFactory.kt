package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.parsing.`class`.Constructor
import io.github.chaosunity.casc.parsing.`class`.Function
import io.github.chaosunity.casc.parsing.expression.EmptyExpression
import io.github.chaosunity.casc.parsing.expression.SuperCall
import io.github.chaosunity.casc.parsing.statement.Block
import io.github.chaosunity.casc.parsing.statement.ReturnStatement
import io.github.chaosunity.casc.util.DescriptorFactory
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*


class MethodFactory(private val cw: ClassWriter) {
    companion object {
        private fun isMainFunction(functionName: String) =
            functionName == "main" || functionName == "主函式"
    }

    fun generate(function: Function) {
        when (function) {
            is Constructor -> generate(function)
            else -> generateFunction(function)
        }
    }

    fun generateFunction(function: Function) {
        val functionName = if (function.name().equals("主函式")) "main" else function.name()
        val isMainFunction = isMainFunction(functionName)
        val descriptor = DescriptorFactory.getMethodDescriptor(function)
        val block = function.rootStatement() as Block
        val scope = block.scope()
        val access = ACC_PUBLIC + if (isMainFunction) ACC_STATIC else 0
        val mv = cw.visitMethod(access, functionName, descriptor, null, null)

        mv.visitCode()

        run {
            val sf = StatementFactory(mv, scope)

            sf.generate(block)

            appendReturnIfAbsence(function, block, sf)
            mv.visitMaxs(-1, -1)
        }

        mv.visitEnd()
    }

    fun generate(constructor: Constructor) {
        val block = constructor.rootStatement() as Block
        val scope = block.scope()
        val access = ACC_PUBLIC
        val description = DescriptorFactory.getMethodDescriptor(constructor)
        val mv = cw.visitMethod(access, "<init>", description, null, null)

        mv.visitCode()

        val sf = StatementFactory(mv, scope)

        sf.generate(SuperCall(listOf()))
        sf.generate(block)
        appendReturnIfAbsence(constructor, block, sf)
        mv.visitMaxs(-1, -1)
        mv.visitEnd()
    }

    private fun appendReturnIfAbsence(
        function: Function,
        block: Block,
        statementFactory: StatementFactory
    ) {
        var isLastStatementReturn = false

        if (block.statements().isNotEmpty()) {
            val lastStatement = block.statements().last()
            isLastStatementReturn = lastStatement is ReturnStatement
        }

        if (!isLastStatementReturn) {
            val emptyExpression = EmptyExpression(function.returnType())
            val returnExpression = ReturnStatement(emptyExpression)

            statementFactory.generate(returnExpression)
        }
    }
}