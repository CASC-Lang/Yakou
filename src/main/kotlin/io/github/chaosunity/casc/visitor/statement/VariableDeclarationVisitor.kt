package io.github.chaosunity.casc.visitor.statement

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.Reference
import io.github.chaosunity.casc.parsing.node.statement.VariableDeclaration
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.ArrayType
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor

class VariableDeclarationVisitor(private val ev: ExpressionVisitor, private val scope: Scope) :
    CASCBaseVisitor<VariableDeclaration>() {
    override fun visitVariableDeclaration(ctx: CASCParser.VariableDeclarationContext): VariableDeclaration {
        val immutable = ctx.MUT() == null
        val variableName = ctx.findName()!!.text
        val expression = ctx.findExpression()!!.accept(ev)
        val type = expression.type

        scope.addLocalVariable(LocalVariable(variableName, type, immutable))

        return VariableDeclaration(variableName, expression)
    }
}