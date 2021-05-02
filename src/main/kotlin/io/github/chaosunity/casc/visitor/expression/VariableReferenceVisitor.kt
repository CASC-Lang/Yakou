package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.VariableReference
import io.github.chaosunity.casc.parsing.scope.Scope

class VariableReferenceVisitor(private val scope: Scope) : CASCBaseVisitor<VariableReference>() {
    override fun visitVarRef(ctx: CASCParser.VarRefContext): VariableReference {
        val name = ctx.text
        val localVariable = scope.getLocalVariable(name)

        return VariableReference(localVariable.type, name)
    }

    override fun visitVarReference(ctx: CASCParser.VarReferenceContext): VariableReference {
        val name = ctx.text
        val localVariable = scope.getLocalVariable(name)

        return VariableReference(localVariable.type, name)
    }
}