package org.casclang.casc.visitor.statement

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.statement.Block
import org.casclang.casc.parsing.scope.Scope

class BlockVisitor(private val scope: Scope) : CASCBaseVisitor<Block>() {
    override fun visitBlock(ctx: CASCParser.BlockContext): Block {
        val innerScope = Scope(scope)
        val sv = StatementVisitor(innerScope)

        return Block(innerScope, ctx.findStatement().map { it.accept(sv) })
    }
}