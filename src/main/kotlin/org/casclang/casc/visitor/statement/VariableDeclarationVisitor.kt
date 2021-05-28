package org.casclang.casc.visitor.statement

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.statement.VariableDeclaration
import org.casclang.casc.parsing.scope.LocalVariable
import org.casclang.casc.parsing.scope.Scope
import org.casclang.casc.visitor.expression.ExpressionVisitor

class VariableDeclarationVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<VariableDeclaration>() {
    override fun visitVariableDeclaration(ctx: CASCParser.VariableDeclarationContext): VariableDeclaration {
        val immutable = ctx.MUT() == null
        val variableName = ctx.findName()!!.text
        val expression = ctx.findExpression()!!.accept(ev)
        val type = expression.type

        scope.addLocalVariable(LocalVariable(variableName, type, immutable))

        return VariableDeclaration(variableName, expression)
    }
}