package org.casclang.casc.visitor.statement

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.EmptyExpression
import org.casclang.casc.parsing.node.statement.ReturnStatement
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.visitor.expression.ExpressionVisitor

class ReturnVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<ReturnStatement>() {
    override fun visitReturnVoid(ctx: CASCParser.ReturnVoidContext): ReturnStatement =
        ReturnStatement(EmptyExpression(BuiltInType.VOID))

    override fun visitReturnWithValue(ctx: CASCParser.ReturnWithValueContext): ReturnStatement =
        ReturnStatement(ctx.findExpression()!!.accept(ev))
}