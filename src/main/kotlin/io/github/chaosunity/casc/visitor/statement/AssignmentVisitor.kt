package io.github.chaosunity.casc.visitor.statement

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.statement.Assignment
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor

class AssignmentVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<Assignment>() {
    override fun visitAssignment(ctx: CASCParser.AssignmentContext): Assignment =
        Assignment(ctx.findName()!!.text, ctx.findExpression()!!.accept(ev))
}