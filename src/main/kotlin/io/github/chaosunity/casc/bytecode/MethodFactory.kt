package io.github.chaosunity.casc.bytecode

import io.github.chaosunity.casc.bytecode.statement.StatementFactory
import io.github.chaosunity.casc.parsing.Constructor
import io.github.chaosunity.casc.parsing.Function
import io.github.chaosunity.casc.parsing.node.expression.EmptyExpression
import io.github.chaosunity.casc.parsing.node.expression.SuperCall
import io.github.chaosunity.casc.parsing.node.statement.Block
import io.github.chaosunity.casc.parsing.node.statement.ReturnStatement
import io.github.chaosunity.casc.util.DescriptorFactory
import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.Opcodes.*

class MethodFactory(private val cw: ClassWriter) {
    fun generate(function: Function<*>) {
        if (function is Constructor) {
            generate(function)
            return
        }

        val name = function.name
        val isMain = name == "main" || name == "主函式"
        val descriptor = DescriptorFactory.getMethodDescriptor(function)
        val block = function.rootStatement as Block
        val scope = block.scope
        val access = ACC_PUBLIC + if (isMain) ACC_STATIC else 0
        val mv = cw.visitMethod(access, name, descriptor, null, null)

        mv.visitCode()

        val sf = StatementFactory(mv, scope)

        block.accept(sf)
        appendReturnIfAbsence(function, block, sf)
        mv.visitMaxs(-1, -1)

        mv.visitEnd()
    }

    fun generate(constructor: Constructor) {
        val block = constructor.rootStatement as Block
        val scope = block.scope
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
        function: Function<*>,
        block: Block,
        statementFactory: StatementFactory
    ) {
        var isLastStatementReturn = false

        if (block.statements.isNotEmpty()) {
            val lastStatement = block.statements.last()
            isLastStatementReturn = lastStatement is ReturnStatement
        }

        if (!isLastStatementReturn) {
            val emptyExpression = EmptyExpression(function.returnType)
            val returnExpression = ReturnStatement(emptyExpression)

            statementFactory.generate(returnExpression)
        }
    }
}