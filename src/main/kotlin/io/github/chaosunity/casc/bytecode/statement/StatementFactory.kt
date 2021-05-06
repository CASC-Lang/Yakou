package io.github.chaosunity.casc.bytecode.statement

import io.github.chaosunity.casc.bytecode.expression.ExpressionFactory
import io.github.chaosunity.casc.parsing.node.expression.*
import io.github.chaosunity.casc.parsing.node.expression.ArithmeticExpression.*
import io.github.chaosunity.casc.parsing.node.statement.*
import io.github.chaosunity.casc.parsing.scope.Scope
import jdk.internal.org.objectweb.asm.MethodVisitor


class StatementFactory(private val mv: MethodVisitor, private val scope: Scope) {
    private val ef = ExpressionFactory(mv, scope)
    private val vdf = VariableDeclarationFactory(this, ef)
    private val rf = ReturnFactory(ef, mv)
    private val pf = PrintFactory(ef, mv)
    private val iff = IfFactory(this, ef, mv)
    private val ff = ForFactory(mv)
    private val bf = BlockFactory(mv)
    private val af = AssignmentFactory(mv, scope)

    fun generate(expression: Statement<*>) =
        when (expression) {
            is Assignment -> generate(expression)
            is Block -> generate(expression)
            is IfStatement -> generate(expression)
            is PrintlnStatement -> generate(expression)
            is PrintStatement -> generate(expression)
            is RangedForStatement -> generate(expression)
            is ReturnStatement -> generate(expression)
            is VariableDeclaration -> generate(expression)
            else -> ef.generate(expression as Expression<*>)
        }

    fun generate(print: PrintStatement) =
        pf.generate(print)


    fun generate(println: PrintlnStatement) =
        pf.generate(println)

    fun generate(variableDeclaration: VariableDeclaration) =
        vdf.generate(variableDeclaration)


    fun generate(functionCall: FunctionCall) =
        ef.generate(functionCall)


    fun generate(returnStatement: ReturnStatement) =
        rf.generate(returnStatement)


    fun generate(ifStatement: IfStatement) =
        iff.generate(ifStatement)


    fun generate(block: Block) =
        bf.generate(block)


    fun generate(rangedFor: RangedForStatement) =
        ff.generate(rangedFor)


    fun generate(assignment: Assignment) =
        af.generate(assignment)
}