package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.FieldReference
import io.github.chaosunity.casc.parsing.node.expression.LocalVariableReference
import io.github.chaosunity.casc.parsing.node.expression.Reference
import io.github.chaosunity.casc.parsing.scope.Scope

class VariableReferenceVisitor(private val ev: ExpressionVisitor, private val scope: Scope) :
    CASCBaseVisitor<Reference<*>>() {
    override fun visitVarRef(ctx: CASCParser.VarRefContext): Reference<*> =
        getReference(ctx.text)

    override fun visitVarReference(ctx: CASCParser.VarReferenceContext): Reference<*> =
        getReference(ctx.text)

    private fun getReference(name: String): Reference<*> =
        if (scope.isFieldExists(name)) FieldReference(scope.getField(name))
        else LocalVariableReference(scope.getLocalVariable(name))
}