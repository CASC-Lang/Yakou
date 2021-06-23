package org.casclang.casc.visitor.statement

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.Value
import org.casclang.casc.parsing.node.statement.EmptyStatement
import org.casclang.casc.parsing.node.statement.IfStatement
import org.casclang.casc.parsing.node.statement.Statement
import org.casclang.casc.util.addError
import org.casclang.casc.visitor.expression.ExpressionVisitor

class IfStatementVisitor(private val sv: StatementVisitor, private val ev: ExpressionVisitor) : CASCBaseVisitor<Statement<*>>() {
    override fun visitIfStatement(ctx: CASCParser.IfStatementContext): Statement<*> {
        val condition = ctx.condition!!.accept(ev)
        val trueStatement = ctx.trueStatement!!.accept(sv)
        val falseStatementCtx = ctx.falseStatement

        if (!condition.isBool()) {
            addError(ctx.condition, "Cannot convert ${condition.type} into bool.")
            return EmptyStatement
        }

        return if (falseStatementCtx != null) {
            val falseStatement = falseStatementCtx.accept(sv)

            if (condition is Value) {
                if (condition.value == "true") trueStatement
                else falseStatement
            } else IfStatement(condition, trueStatement, falseStatement)
        } else {
            if (condition is Value) {
                if (condition.value == "true") trueStatement
                else EmptyStatement
            } else IfStatement(condition, trueStatement)
        }
    }
}