package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.parsing.node.statement.Block
import jdk.internal.org.objectweb.asm.MethodVisitor

class BlockFactory(private val mv: MethodVisitor) {
    fun generate(block: Block) {
        val scope = block.scope
        val statements = block.statements
        val sf = StatementFactory(mv, scope)

        statements.forEach { it.accept(sf) }
    }
}