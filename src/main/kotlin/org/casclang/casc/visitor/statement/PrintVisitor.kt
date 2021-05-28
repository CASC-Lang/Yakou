package org.casclang.casc.visitor.statement

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.statement.OutputStatement
import org.casclang.casc.parsing.node.statement.PrintStatement
import org.casclang.casc.parsing.node.statement.PrintlnStatement
import org.casclang.casc.visitor.expression.ExpressionVisitor

class PrintVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<OutputStatement<*>>() {
    override fun visitPrintStatement(ctx: CASCParser.PrintStatementContext): OutputStatement<*> =
        PrintStatement(ctx.findExpression()!!.accept(ev))

    override fun visitPrintlnStatement(ctx: CASCParser.PrintlnStatementContext): OutputStatement<*> =
        PrintlnStatement(ctx.findExpression()!!.accept(ev))
}