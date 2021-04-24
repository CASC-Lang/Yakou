package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.parsing.expression.Expression
import io.github.chaosunity.casc.parsing.expression.Value
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.statement.PrintStatement
import io.github.chaosunity.casc.parsing.statement.PrintlnStatement
import io.github.chaosunity.casc.parsing.statement.Statement
import io.github.chaosunity.casc.parsing.statement.VariableDeclaration
import io.github.chaosunity.casc.parsing.type.BuiltInType
import io.github.chaosunity.casc.parsing.type.ClassType
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

class StatementFactory(private val mv: MethodVisitor) {
    private val ef = ExpressionFactory(mv)

    fun generate(statement: Statement, scope: Scope) {
        when (statement) {
            is PrintStatement -> generate(statement, scope)
            is PrintlnStatement -> generate(statement, scope)
            is VariableDeclaration -> generate(statement, scope)
            is Expression -> ef.generate(statement, scope)
        }
    }

    fun generate(printStatement: PrintStatement, scope: Scope) =
        generatePrintStreamCall(printStatement.expression(), scope, "print")

    fun generate(printlnStatement: PrintlnStatement, scope: Scope) =
        generatePrintStreamCall(printlnStatement.expression(), scope, "println")

    fun generate(declaration: VariableDeclaration, scope: Scope) {
        val expression = declaration.expression()
        val variableName = declaration.name()
        val index = scope.getLocalVariableIndex(variableName)

        if (expression is Value) {
            val type = expression.type()
            var stringValue = expression.value()

            if (type == BuiltInType.INT()) {
                mv.visitIntInsn(BIPUSH, stringValue.toInt())
                mv.visitVarInsn(ISTORE, index)
            } else if (type == BuiltInType.STRING()) {
                stringValue = stringValue.removePrefix("\"").removeSuffix("\"")

                mv.visitLdcInsn(stringValue)
                mv.visitVarInsn(ASTORE, index)
            }
        }

        scope.addLocalVariable(LocalVariable(expression.type(), variableName))
    }

    private fun generatePrintStreamCall(expression: Expression, scope: Scope, actualFunctionName: String) {
        val ef = ExpressionFactory(mv)

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        ef.generate(expression, scope)

        val type = expression.type()
        val descriptor = "(${type.descriptor()})V"
        val owner = ClassType("java.io.PrintStream")
        val fieldDescriptor = owner.descriptor()

        mv.visitMethodInsn(INVOKEVIRTUAL, fieldDescriptor, actualFunctionName, descriptor, false)
    }
}