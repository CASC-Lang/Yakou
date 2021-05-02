package io.github.chaosunity.casc.visitor.statement

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.statement.Block
import io.github.chaosunity.casc.parsing.scope.Scope

class BlockVisitor(private val scope: Scope) : CASCBaseVisitor<Block>() {
    override fun visitBlock(ctx: CASCParser.BlockContext): Block {
        val innerScope = Scope(scope)
        val sv = StatementVisitor(innerScope)

        return Block(innerScope, ctx.findStatement().map { it.accept(sv) })
    }
}