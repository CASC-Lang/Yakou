package io.github.chaosunity.casc.visitor.expression

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.Expression
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.visitor.expression.function.CallVisitor

class ExpressionVisitor(scope: Scope) : CASCBaseVisitor<Expression<*>>() {
    private val av = ArithmeticVisitor(this)
    private val vrv = VariableReferenceVisitor(this, scope)
    private val iv = IndexVisitor(this)
    private val adv = ArrayDeclarationVisitor(this)
    private val aiv = ArrayInitializationVisitor(this)
    private val vv = ValueVisitor()
    private val cv = CallVisitor(this, scope)
    private val cev = ConditionalVisitor(this)
    private val iev = IfExpressionVisitor(this)
    private val nev = NegativeVisitor(this)
    private val wev = WrappedVisitor(this)

    override fun visitVarRef(ctx: CASCParser.VarRefContext): Expression<*> =
        vrv.visitVarRef(ctx)

    override fun visitVarReference(ctx: CASCParser.VarReferenceContext): Expression<*> =
        vrv.visitVarReference(ctx)

    override fun visitIndexEpxression(ctx: CASCParser.IndexEpxressionContext): Expression<*> =
        iv.visitIndexEpxression(ctx)

    override fun visitFieldCall(ctx: CASCParser.FieldCallContext): Expression<*> =
        cv.visitFieldCall(ctx)

    override fun visitFunctionCall(ctx: CASCParser.FunctionCallContext): Expression<*> =
        cv.visitFunctionCall(ctx)

    override fun visitConstructorCall(ctx: CASCParser.ConstructorCallContext): Expression<*> =
        cv.visitConstructorCall(ctx)

    override fun visitSuperCall(ctx: CASCParser.SuperCallContext): Expression<*> =
        cv.visitSuperCall(ctx)

    override fun visitArrayDeclaration(ctx: CASCParser.ArrayDeclarationContext): Expression<*> =
        adv.visitArrayDeclaration(ctx)

    override fun visitArrayInitialization(ctx: CASCParser.ArrayInitializationContext): Expression<*> =
        aiv.visitArrayInitialization(ctx)

    override fun visitValue(ctx: CASCParser.ValueContext): Expression<*> =
        vv.visitValue(ctx)

    override fun visitAdd(ctx: CASCParser.AddContext): Expression<*> =
        av.visitAdd(ctx)

    override fun visitSubtract(ctx: CASCParser.SubtractContext): Expression<*> =
        av.visitSubtract(ctx)

    override fun visitMultiply(ctx: CASCParser.MultiplyContext): Expression<*> =
        av.visitMultiply(ctx)

    override fun visitDivide(ctx: CASCParser.DivideContext): Expression<*> =
        av.visitDivide(ctx)

    override fun visitConditionalExpression(ctx: CASCParser.ConditionalExpressionContext): Expression<*> =
        cev.visitConditionalExpression(ctx)

    override fun visitIfExpression(ctx: CASCParser.IfExpressionContext): Expression<*> =
        iev.visitIfExpression(ctx)

    override fun visitNegativeExpression(ctx: CASCParser.NegativeExpressionContext): Expression<*> =
        nev.visitNegativeExpression(ctx)

    override fun visitWrappedExpression(ctx: CASCParser.WrappedExpressionContext): Expression<*> =
        wev.visitWrappedExpression(ctx)
}