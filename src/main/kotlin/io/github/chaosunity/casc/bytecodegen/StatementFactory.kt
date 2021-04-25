package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.parsing.LogicalOp
import io.github.chaosunity.casc.parsing.expression.ConditionalExpression
import io.github.chaosunity.casc.parsing.expression.Expression
import io.github.chaosunity.casc.parsing.expression.VarReference
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.*
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.ClassType
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class StatementFactory(private val mv: MethodVisitor, private val scope: Scope) {
    private val ef = ExpressionFactory(mv, scope)

    fun generate(statement: Statement) {
        when (statement) {
            is PrintStatement -> generate(statement)
            is PrintlnStatement -> generate(statement)
            is VariableDeclaration -> generate(statement)
            is ReturnStatement -> generate(statement)
            is IfStatement -> generate(statement)
            is BlockStatement -> generate(statement)
            is AssignmentStatement -> generate(statement)
            is RangedForStatement -> generate(statement)
            is Expression -> ef.generate(statement)
        }
    }

    fun generate(printStatement: PrintStatement) =
        generatePrintStreamCall(printStatement.expression(), "print")

    fun generate(printlnStatement: PrintlnStatement) =
        generatePrintStreamCall(printlnStatement.expression(), "println")

    fun generate(declaration: VariableDeclaration) {
        val expression = declaration.expression()

        ef.generate(expression)

        generate(AssignmentStatement(declaration))
    }

    fun generate(returnStatement: ReturnStatement) {
        val expression = returnStatement.expression()
        val type = expression.type()

        ef.generate(expression)

        if (type == BuiltInType.VOID()) {
            mv.visitInsn(RETURN)
        } else if (type == BuiltInType.INT()) {
            mv.visitInsn(IRETURN)
        }
    }

    fun generate(ifStatement: IfStatement) {
        val condition = ifStatement.condition()

        ef.generate(condition)

        val trueLabel = Label()
        val endLabel = Label()

        mv.visitJumpInsn(IFEQ, trueLabel)
        generate(ifStatement.trueStatement())
        mv.visitJumpInsn(GOTO, endLabel)
        mv.visitLabel(trueLabel)
        generate(ifStatement.falseStatement())
        mv.visitLabel(endLabel)
    }

    fun generate(blockStatement: BlockStatement) {
        val innerScope = Scope(blockStatement.scope())
        val statements = blockStatement.statements()
        val sf = StatementFactory(mv, innerScope)

        statements.forEach(sf::generate)
    }

    fun generate(forStatement: RangedForStatement) {
        val innerScope = forStatement.scope()
        val sf = StatementFactory(mv, innerScope)
        val ef = ExpressionFactory(mv, innerScope)
        val iterator = forStatement.iteratorVariableStatement()

        val trueLabel = Label()
        val endLabel = Label()

        val iteratorVariableName = forStatement.iteratorVariableName()
        val rightExpression = forStatement.endExpression()
        val leftExpression = VarReference(forStatement.type(), iteratorVariableName, false)
        val conditionalExpression = ConditionalExpression(
            leftExpression,
            rightExpression,
            when (forStatement.forType()) {
                ForType.TO() -> LogicalOp.LESS_EQ()
                ForType.UNTIL() -> LogicalOp.LESS()
                else -> LogicalOp.LESS()
            }
        )

        sf.generate(iterator)
        ef.generate(conditionalExpression)
        mv.visitJumpInsn(IFEQ, endLabel)
        mv.visitLabel(trueLabel)
        sf.generate(forStatement.statement())
        mv.visitIincInsn(innerScope.getLocalVariableIndex(iteratorVariableName), 1)
        ef.generate(conditionalExpression)
        mv.visitJumpInsn(IFNE, trueLabel)
        mv.visitLabel(endLabel)
    }

    fun generate(assignment: AssignmentStatement) {
        val variableName = assignment.variableName()
        val type = assignment.expression().type()
        val index = scope.getLocalVariableIndex(variableName)

        if (type == BuiltInType.INT() || type == BuiltInType.BOOLEAN()) {
            mv.visitVarInsn(ISTORE, index)
        } else if (type == BuiltInType.STRING()) {
            mv.visitVarInsn(ASTORE, index)
        }
    }

    private fun generatePrintStreamCall(expression: Expression, actualFunctionName: String) {
        val ef = ExpressionFactory(mv, scope)

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        ef.generate(expression)

        val type = expression.type()
        val descriptor = "(${type.descriptor()})V"
        val owner = ClassType("java.io.PrintStream")
        val fieldDescriptor = owner.internalName()

        mv.visitMethodInsn(INVOKEVIRTUAL, fieldDescriptor, actualFunctionName, descriptor, false)
    }
}