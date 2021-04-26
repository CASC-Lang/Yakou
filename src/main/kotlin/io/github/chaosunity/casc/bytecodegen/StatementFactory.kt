package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.parsing.LogicalOp
import io.github.chaosunity.casc.parsing.expression.*
import io.github.chaosunity.casc.parsing.expression.math.ArithmeticExpression.*
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.*
import io.github.chaosunity.casc.parsing.type.ClassType
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*


class StatementFactory(private val mv: MethodVisitor, private val scope: Scope) {
    private val ef = ExpressionFactory(mv, scope)

    fun generate(statement: Statement) {
        when (statement) {
            is Print -> generate(statement)
            is Println -> generate(statement)
            is VariableDeclaration -> generate(statement)
            is ReturnStatement -> generate(statement)
            is IfStatement -> generate(statement)
            is Block -> generate(statement)
            is Assignment -> generate(statement)
            is RangedFor -> generate(statement)
            is SuperCall -> generate(statement)
            is ConstructorCall -> generate(statement)
            is FunctionParameter -> generate(statement)
            is ConditionalExpression -> generate(statement)
            is Addition -> generate(statement)
            is Subtraction -> generate(statement)
            is Multiplication -> generate(statement)
            is Division -> generate(statement)
            is Value -> generate(statement)
            is VarReference -> generate(statement)
            is EmptyExpression -> generate(statement)
            is Expression -> ef.generate(statement)
        }
    }

    fun generate(print: Print) =
        generatePrintStreamCall(print.expression(), "print")

    fun generate(printlnStatement: Println) =
        generatePrintStreamCall(printlnStatement.expression(), "println")

    fun generate(declaration: VariableDeclaration) {
        val expression = declaration.expression()

        ef.generate(expression)

        generate(Assignment(declaration))
    }

    fun generate(returnStatement: ReturnStatement) {
        val expression = returnStatement.expression()
        val type = expression.type()

        ef.generate(expression)

        mv.visitInsn(type.returnOpcode())
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
        ifStatement.falseStatement().exists { generate(it) }
        mv.visitLabel(endLabel)
    }

    fun generate(block: Block) {
        val innerScope = Scope(block.scope())
        val statements = block.statements()
        val sf = StatementFactory(mv, innerScope)

        statements.forEach(sf::generate)
    }

    fun generate(forStatement: RangedFor) {
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

    fun generate(assignment: Assignment) {
        val variableName = assignment.variableName()
        val type = assignment.expression().type()
        val index = scope.getLocalVariableIndex(variableName)

        mv.visitVarInsn(type.storeVariableOpcode(), index)
    }

    fun generate(superCall: SuperCall) =
        ef.generate(superCall)

    fun generate(constructorCall: ConstructorCall) =
        ef.generate(constructorCall)

    fun generate(addition: Addition) =
        ef.generate(addition)

    fun generate(subtraction: Subtraction) =
        ef.generate(subtraction)

    fun generate(multiplication: Multiplication) =
        ef.generate(multiplication)

    fun generate(division: Division) =
        ef.generate(division)

    fun generate(functionParameter: FunctionParameter) =
        ef.generate(functionParameter)

    fun generate(conditionalExpression: ConditionalExpression) =
        ef.generate(conditionalExpression)

    fun generate(value: Value) =
        ef.generate(value)

    fun generate(varReference: VarReference) =
        ef.generate(varReference)

    fun generate(emptyExpression: EmptyExpression) =
        ef.generate(emptyExpression)

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