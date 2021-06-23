package org.casclang.casc.visitor.expression

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.FieldReference
import org.casclang.casc.parsing.node.expression.LocalVariableReference
import org.casclang.casc.parsing.node.expression.Reference
import org.casclang.casc.parsing.scope.Scope

class VariableReferenceVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<Reference<*>>() {
    override fun visitVarRef(ctx: CASCParser.VarRefContext): Reference<*> =
        getReference(ctx.text)

    override fun visitVarReference(ctx: CASCParser.VarReferenceContext): Reference<*> =
        getReference(ctx.text)

    private fun getReference(name: String): Reference<*> =
        if (scope.isFieldExists(name)) FieldReference(scope.getField(name)!!)
        else LocalVariableReference(scope.getLocalVariable(name)!!)
}