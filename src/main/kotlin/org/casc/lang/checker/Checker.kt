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
            checkFunction(it, classScope)
        }
        clazz.functions.forEach {
            checkFunctionBody(it, Scope(classScope))
        }

        return clazz
    }

    // TODO: Track if-else and match branches so compiler can know whether all paths have return value or not
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

        if (validationPass) {
            scope.registerFunctionSignature(function)
        }

        return function
    }

    // Called right after function signature checking is complete
    private fun checkFunctionBody(function: Function, scope: Scope) {
        function.statements.forEach {
            checkStatement(it, scope, function.returnType)
        }
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
                    if (expressionType == PrimitiveType.Unit) {
                        reports += Error(
                            statement.expression!!.pos,
                            "Could not store void type into variable"
                        )
                    }

                    statement.index = scope.findVariableIndex(statement.name.literal)
                }
            }
            is ExpressionStatement -> {
                if (statement.expression !is AssignmentExpression && statement.expression !is FunctionCallExpression) {
                    reports += Error(
                        statement.position,
                        "Unused expression",
                        "Consider remove this line"
                    )
                } else {
                    checkExpression(statement.expression, scope)
                }
            }
            is ReturnStatement -> {
                val expressionType = checkExpression(statement.expression, scope)

                if (!TypeUtil.canCast(expressionType, returnType)) {
                    reports.reportTypeMismatch(statement.position!!, returnType, expressionType)
                } else statement.returnType = returnType
            }
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
                    else -> null // Should not be null
                }

                expression.type
            }
            is FloatLiteral -> {
                expression.type = when {
                    expression.literal?.literal?.endsWith('D') == true -> PrimitiveType.F64
                    else -> PrimitiveType.F32
                }

                expression.type
            }
            is AssignmentExpression -> {
                expression.type = checkExpression(expression.expression, scope)

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

                    if (expression.expression?.type == PrimitiveType.Unit) {
                        reports += Error(
                            expression.expression.pos,
                            "Could not store void type into variable"
                        )
                    }

                    expression.isFieldAssignment = false
                    expression.index = scope.findVariableIndex(expression.identifier.literal)
                }

                if (!TypeUtil.canCast(expression.type, variable?.type)) {
                    reports.reportTypeMismatch(expression.identifier.pos, variable?.type, expression.type)
                } else {
                    expression.castTo = variable?.type
                }

                expression.type
            }
            is IdentifierCallExpression -> {
                if (expression.ownerReference != null) {
                    // Appointed class field
                    val field = scope.findField(expression.ownerReference.path, expression.name!!.literal)

                    if (field == null) {
                        reports += Error(
                            expression.pos,
                            "Field ${expression.name.literal} does not exist in class ${expression.ownerReference.path}"
                        )
                    } else {
                        if (!field.companion) {
                            reports += Error(
                                expression.pos,
                                "Field ${expression.name.literal} exists in non-companion context but is called from other context"
                            )
                        }
                        // TODO: Check accessor

                        expression.type = field.type
                    }

                    field?.type
                } else if (expression.previousExpression != null) {
                    // Chain calling
                    val previousType = checkExpression(expression.previousExpression, scope)
                    val field = scope.findField(previousType?.typeName, expression.name!!.literal)

                    if (field == null) {
                        reports += Error(
                            expression.pos,
                            "Field ${expression.name.literal} does not exist in class ${previousType?.typeName}"
                        )
                    } else {
                        expression.type = field.type
                    }

                    expression.type
                } else {
                    // Local variable / current class field
                    val variable = scope.findVariable(expression.name!!.literal)

                    if (variable == null) {
                        reports += Error(
                            expression.pos,
                            "Variable ${expression.name} does not exist in current context"
                        )
                    } else {
                        expression.type = variable.type
                        expression.index = scope.findVariableIndex(expression.name.literal)
                    }

                    variable?.type
                }
            }
            is FunctionCallExpression -> {
                val argumentTypes = expression.arguments.map {
                    checkExpression(it, scope)
                }

                val previousType = checkExpression(expression.previousExpression, scope)

                // Check function call expression's context, e.g companion context
                val functionSignature =
                    scope.findFunction(expression.ownerReference?.path ?: previousType?.typeName, expression.name!!.literal, argumentTypes)

                if (functionSignature == null) {
                    // No function matched
                    reports += Error(
                        expression.pos!!,
                        "Function ${expression.name.literal} does not exist in current context"
                    )
                } else {
                    if (functionSignature.ownerReference == expression.ownerReference) {
                        // Function's owner class is same as current class
                        if (expression.previousExpression == null) {
                            if (expression.inCompanionContext && !functionSignature.companion) {
                                reports += Error(
                                    expression.pos!!,
                                    "Function ${expression.name.literal} exists in non-companion context but it's called from companion context",
                                    "Consider move its declaration into companion context"
                                )
                            }
                        }
                        // TODO: Check accessor for chain calling
                    } else {
                        // Function's owner class is outside this context
                        if (expression.previousExpression == null) {
                            if (!functionSignature.companion) {
                                reports += Error(
                                    expression.pos!!,
                                    "Function ${expression.name.literal} exists in non-companion context but is called from other context",
                                    "Consider move its declaration into companion context"
                                )
                            }
                            // TODO: Check accessor
                        }
                        // TODO: Check accessor for chain calling
                    }

                    expression.type = functionSignature.returnType
                    expression.referenceFunctionSignature = functionSignature
                }

                expression.type
            }
            is UnaryExpression -> {
                expression.type = checkExpression(expression.expression, scope)

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