package org.casclang.casc.bytecode

import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.Opcodes.*
import org.casclang.casc.bytecode.statement.StatementFactory
import org.casclang.casc.parsing.Constructor
import org.casclang.casc.parsing.Function
import org.casclang.casc.parsing.Implementation
import org.casclang.casc.parsing.node.expression.EmptyExpression
import org.casclang.casc.parsing.node.statement.Block
import org.casclang.casc.parsing.node.statement.ReturnStatement
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.util.DescriptorFactory

class FunctionFactory(private val cw: ClassWriter, private val implementations: List<Implementation>) {
    fun generate(function: Function<*>) {
        if (function is Constructor) {
            generate(function)
            return
        }

        val name = function.name
        val descriptor = DescriptorFactory.getMethodDescriptorWithParameter(function)
        val block = function.rootStatement as Block
        val scope = block.scope
        val access = function.accessModifier.accessOpcode + if (function.static) ACC_STATIC else 0
        val mv = cw.visitMethod(access, name, descriptor, null, null)

        mv.visitCode()

        val sf = StatementFactory(mv, scope)

        block.accept(sf)
        if (function.signature.returnType == BuiltInType.VOID)
            appendReturnIfAbsence(function, block, sf)
        mv.visitMaxs(-1, -1)

        mv.visitEnd()
    }

    fun generate(constructor: Constructor) {
        val block = constructor.rootStatement as Block
        val scope = block.scope
        val access = constructor.accessModifier.accessOpcode
        val description = DescriptorFactory.getMethodDescriptorWithParameter(constructor)
        val mv = cw.visitMethod(access, "<init>", description, null, null)

        mv.visitCode()

        val sf = StatementFactory(mv, scope)

        if (constructor.isPrimary) {
            val superClassConstructorCall = implementations.find { it.superClassCtor != null }!!.superClassCtor!!

            mv.visitVarInsn(ALOAD, 0)
            mv.visitMethodInsn(
                INVOKESPECIAL,
                scope.superClassInternalName,
                "<init>",
                DescriptorFactory.getMethodDescriptorWithArgument(superClassConstructorCall.arguments, BuiltInType.VOID),
                false
            )
        }

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