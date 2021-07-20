package org.casclang.casc.bytecode.expression

import org.casclang.casc.parsing.node.expression.*
import org.casclang.casc.parsing.node.expression.ArithmeticExpression.*
import org.casclang.casc.parsing.scope.Scope
import jdk.internal.org.objectweb.asm.MethodVisitor


class ExpressionFactory(mv: MethodVisitor, scope: Scope) {
    private val rf = ReferenceFactory(mv, this, scope)
    private val `if` = IndexFactory(mv, this)
    private val adf = ArrayDeclarationFactory(mv, this)
    private val aif = ArrayInitializationFactory(mv, this)
    private val vf = ValueFactory(mv)
    private val pf = ParameterFactory(mv, scope)
    private val iff = IfFactory(this, mv)
    private val conditionalFactory = ConditionalFactory(this, mv)
    private val callFactory = CallFactory(this, scope, mv)
    private val af = ArithmeticFactory(this, mv)
    private val nf = NegativeFactory(mv, this)

    fun generate(expression: Expression<*>): Unit =
        when (expression) {
            is FieldReference -> generate(expression)
            is LocalVariableReference -> generate(expression)
            is Parameter -> generate(expression)
            is Index -> generate(expression)
            is ArrayDeclaration -> generate(expression)
            is ArrayInitialization -> generate(expression)
            is Value -> generate(expression)
            is FieldCall -> generate(expression)
            is LengthCall -> generate(expression)
            is ConstructorCall -> generate(expression)
            is SelfCall -> generate(expression)
            is FunctionCall -> generate(expression)
            is Addition -> generate(expression)
            is Subtraction -> generate(expression)
            is Multiplication -> generate(expression)
            is Division -> generate(expression)
            is Conditional -> generate(expression)
            is IfExpression -> generate(expression)
            is Wrapped -> generate(expression.expression)
            is Negative -> generate(expression)
            is EmptyExpression -> {
            }
            else -> throw RuntimeException("Invalid syntax feature.\nDetail: $expression")
        }

    fun generate(reference: FieldReference) =
        rf.generate(reference)

    fun generate(reference: LocalVariableReference) =
        rf.generate(reference)

    fun generate(parameter: Parameter) =
        pf.generate(parameter)

    fun generate(index: Index, load: Boolean = true) =
        `if`.generate(index, load)

    fun generate(declaration: ArrayDeclaration) =
        adf.generate(declaration)

    fun generate(initialization: ArrayInitialization) =
        aif.generate(initialization)

    fun generate(value: Value) =
        vf.generate(value)

    fun generate(call: FieldCall) =
        callFactory.generate(call)

    fun generate(call: LengthCall) =
        callFactory.generate(call)

    fun generate(call: ConstructorCall) =
        callFactory.generate(call)

    fun generate(call: SelfCall) =
        callFactory.generate(call)

    fun generate(call: FunctionCall) =
        callFactory.generate(call)

    fun generate(expression: Addition) =
        af.generate(expression)

    fun generate(expression: Subtraction) =
        af.generate(expression)

    fun generate(expression: Multiplication) =
        af.generate(expression)

    fun generate(expression: Division) =
        af.generate(expression)

    fun generate(negative: Negative) =
        nf.generate(negative)

    fun generate(conditional: Conditional) =
        conditionalFactory.generate(conditional)

    fun generate(ifExpression: IfExpression) =
        iff.generate(ifExpression)
}