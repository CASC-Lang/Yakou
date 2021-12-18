package org.casc.lang.checker

import org.casc.lang.ast.*
import org.casc.lang.ast.Function
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report
import org.casc.lang.table.*

class Checker {
    companion object {
        val globalScope: Scope = Scope()
    }

    private var reports: MutableSet<Report> = mutableSetOf()

    fun check(files: List<File>): Pair<List<Report>, List<File>> {
        val checkedFiles = files.map(::checkFile)

        return reports.toList() to checkedFiles
    }

    private fun checkFile(file: File): File {
        file.clazz = checkClass(file.clazz)

        return file
    }

    private fun checkClass(clazz: Class): Class {
        val classScope = Scope(globalScope)

        clazz.functions = clazz.functions.map {
            checkFunction(it, Scope(classScope))
        }

        return clazz
    }

    private fun checkFunction(function: Function, scope: Scope): Function {
        // Validate types first then register it to scope
        // Check if parameter has duplicate names
        val duplicateParameters = function.parameters
            .groupingBy { it.name }
            .eachCount()
            .filter { it.value > 1 }
        var validationPass = true

        if (duplicateParameters.isNotEmpty()) {
            duplicateParameters.forEach { (parameter, _) ->
                reports += Error(
                    parameter!!.pos,
                    "Parameter ${parameter.literal} is already declared in function ${function.name!!.literal}"
                )
            }

            validationPass = false
        } else {
            function.parameterTypes = function.parameters.map {
                val type = checkParameter(it, scope)

                if (type == null) {
                    reports.reportUnknownTypeSymbol(it.type!!)

                    return@map null
                }

                type
            }
        }

        function.returnType =
            if (function.returnTypeReference == null) PrimitiveType.Unit
            else {
                val type = checkType(function.returnTypeReference, scope)

                if (type == null) {
                    reports.reportUnknownTypeSymbol(function.returnTypeReference)
                    validationPass = false

                    null
                } else type
            }

        function.statements.forEach {
            checkStatement(it, scope, function.returnType)
        }

        if (validationPass) {
            scope.registerFunctionSignature(function)
        }

        return function
    }

    private fun checkParameter(parameter: Parameter, scope: Scope): Type? {
        val type = checkType(parameter.type, scope)

        scope.registerVariable(false, parameter.name!!.literal, type)

        return type
    }

    private fun checkType(reference: Reference?, scope: Scope): Type? =
        scope.findType(reference)

    private fun checkStatement(statement: Statement, scope: Scope, returnType: Type? = null) {
        when (statement) {
            is VariableDeclaration -> {
                val expressionType = checkExpression(statement.expression, scope)

                if (!scope.registerVariable(statement.mutKeyword != null, statement.name!!.literal, expressionType)) {
                    reports += Error(
                        statement.position!!,
                        "Variable ${statement.name.literal} is already declared in same context"
                    )
                } else {
                    statement.index = scope.findVariableIndex(statement.name.literal)
                }
            }
            is ExpressionStatement -> checkExpression(statement.expression, scope)
        }
    }

    private fun checkExpression(expression: Expression?, scope: Scope): Type? {
        return when (expression) {
            is IntegerLiteral -> {
                expression.type = when {
                    expression.isI8() -> PrimitiveType.I8
                    expression.isI16() -> PrimitiveType.I16
                    expression.isI32() -> PrimitiveType.I32
                    expression.isI64() -> PrimitiveType.I64
                    else -> null
                }

                expression.type
            }
            is FloatLiteral -> {
                expression.type
            }
            is AssignmentExpression -> {
                checkExpression(expression.expression, scope)

                val variable = scope.findVariable(expression.identifier!!.literal)

                if (variable == null) {
                    reports += Error(
                        expression.identifier.pos,
                        "Variable ${expression.identifier.literal} does not exist in this context"
                    )
                } else {
                    if (!variable.mutable) {
                        reports += Error(
                            expression.identifier.pos,
                            "Variable ${expression.identifier.literal} is not mutable",
                            "Declare variable ${expression.identifier.literal} with `mut` keyword"
                        )
                    }

                    expression.isFieldAssignment = false
                    expression.index = scope.findVariableIndex(expression.identifier.literal)
                }

                if (!TypeUtil.canCast(expression.type, variable?.type)) {
                    reports += Error(
                        expression.identifier.pos,
                        "Type mismatch.\n       Expected: ${variable?.type?.typeName ?: "<Unknown>"}\n       Found: ${expression.type?.typeName ?: "<Unknown>"}"
                    )
                } else {
                    expression.castTo = variable?.type
                }

                expression.type
            }
            is IdentifierExpression -> {
                val variable = scope.findVariable(expression.name!!.literal)

                if (variable == null) {
                    reports += Error(
                        expression.name.pos,
                        "Variable ${expression.name.pos} does not exist in current context"
                    )
                } else {
                    expression.type = variable.type
                    expression.index = scope.findVariableIndex(expression.name.literal)
                }

                variable?.type
            }
            is UnaryExpression -> {
                checkExpression(expression.expression, scope)

                expression.type
            }
            is BinaryExpression -> {
                checkExpression(expression.left, scope)
                checkExpression(expression.right, scope)

                expression.promote()

                expression.type
            }
            null -> null
        }
    }
}