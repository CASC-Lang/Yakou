package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.Expression
import io.github.chaosunity.casc.parsing.scope.Scope

class ExpressionVisitor(scope: Scope) : CASCBaseVisitor<Expression<*>>() {
    private val av = ArithmeticVisitor(this)
    private val vrv = VariableReferenceVisitor(scope)
    private val vv = ValueVisitor()
    private val cev = ConditionalExpressionVisitor(this)
    private val nev = NegativeExpressionVisitor(this)
    private val wev = WrappedExpressionVisitor(this)

    override fun visitVarRef(ctx: CASCParser.VarRefContext): Expression<*> =
        vrv.visitVarRef(ctx)

    override fun visitVarReference(ctx: CASCParser.VarReferenceContext): Expression<*> =
        vrv.visitVarReference(ctx)
}