package org.casclang.casc.bytecode.statement

import jdk.internal.org.objectweb.asm.MethodVisitor
import org.casclang.casc.parsing.node.statement.Block

class BlockFactory(private val mv: MethodVisitor) {
    fun generate(block: Block) {
        val scope = block.scope
        val statements = block.statements
        val sf = StatementFactory(mv, scope)

        statements.forEach { it.accept(sf) }
    }
}