package org.casclang.casc.visitor.statement

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.statement.IfStatement
import org.casclang.casc.visitor.expression.ExpressionVisitor

class IfStatementVisitor(private val sv: StatementVisitor, private val ev: ExpressionVisitor) : CASCBaseVisitor<IfStatement>() {
    override fun visitIfStatement(ctx: CASCParser.IfStatementContext): IfStatement {
        val condition = ctx.condition!!.accept(ev)
        val trueStatement = ctx.trueStatement!!.accept(sv)
        val falseStatementCtx = ctx.falseStatement

        return if (falseStatementCtx != null) {
            val falseStatement = falseStatementCtx.accept(sv)

            IfStatement(condition, trueStatement, falseStatement)
        } else IfStatement(condition, trueStatement)
    }
}