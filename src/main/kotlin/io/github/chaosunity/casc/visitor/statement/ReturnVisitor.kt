package io.github.chaosunity.casc.visitor.statement

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.EmptyExpression
import io.github.chaosunity.casc.parsing.node.statement.ReturnStatement
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor

class ReturnVisitor(private val ev: ExpressionVisitor) : CASCBaseVisitor<ReturnStatement>() {
    override fun visitReturnVoid(ctx: CASCParser.ReturnVoidContext): ReturnStatement =
        ReturnStatement(EmptyExpression(BuiltInType.VOID))

    override fun visitReturnWithValue(ctx: CASCParser.ReturnWithValueContext): ReturnStatement =
        ReturnStatement(ctx.findExpression()!!.accept(ev))
}