package org.casclang.casc.bytecode.statement

import jdk.internal.org.objectweb.asm.MethodVisitor
import org.casclang.casc.bytecode.expression.ExpressionFactory
import org.casclang.casc.parsing.node.expression.Expression
import org.casclang.casc.parsing.node.expression.FunctionCall
import org.casclang.casc.parsing.node.statement.*
import org.casclang.casc.parsing.scope.Scope

class StatementFactory(mv: MethodVisitor, scope: Scope) {
    private val ef = ExpressionFactory(mv, scope)
    private val vdf = VariableDeclarationFactory(this, ef, scope)
    private val rf = ReturnFactory(ef, mv)
    private val pf = PrintFactory(ef, mv)
    private val iff = IfFactory(this, ef, mv)
    private val ff = ForFactory(mv)
    private val bf = BlockFactory(mv)
    private val af = AssignmentFactory(mv, ef, scope)

    fun generate(expression: Statement<*>) =
        when (expression) {
            is Assignment -> generate(expression)
            is Block -> generate(expression)
            is IfStatement -> generate(expression)
            is PrintlnStatement -> generate(expression)
            is PrintStatement -> generate(expression)
            is InfiniteForStatement -> generate(expression)
            is ForLoopStatement -> generate(expression)
            is RangedForStatement -> generate(expression)
            is ReturnStatement -> generate(expression)
            is VariableDeclaration -> generate(expression)
            is EmptyStatement -> {
            }
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

    fun generate(infiniteFor: InfiniteForStatement) =
        ff.generate(infiniteFor)

    fun generate(forLoop: ForLoopStatement) =
        ff.generate(forLoop)

    fun generate(rangedFor: RangedForStatement) =
        ff.generate(rangedFor)

    fun generate(assignment: Assignment) =
        af.generate(assignment)
}