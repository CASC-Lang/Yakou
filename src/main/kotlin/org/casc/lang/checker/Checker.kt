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
        clazz.functions.forEachIndexed { i, it ->
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
                val type = checkType(it.type, scope)

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

        if (validationPass) scope.registerFunctionSignature(function)

        return function
    }

    // Called right after function signature checking is complete
    private fun checkFunctionBody(function: Function, scope: Scope) {
        function.statements.forEach {
            function.parameters.forEachIndexed { i, parameter ->
                scope.registerVariable(false, parameter.name!!.literal, function.parameterTypes?.get(i))
            }

            checkStatement(it, scope, function.returnType)
        }
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
                val expressionType = checkExpression(expression.expression, scope)

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

                    if (expressionType == PrimitiveType.Unit) {
                        reports += Error(
                            expression.expression?.pos,
                            "Could not store void type into variable"
                        )
                    }

                    expression.isFieldAssignment = false
                    expression.index = scope.findVariableIndex(expression.identifier.literal)
                }

                if (!TypeUtil.canCast(expressionType, variable?.type)) {
                    reports.reportTypeMismatch(expression.identifier.pos, variable?.type, expression.type)
                } else {
                    expression.expression?.castTo = variable?.type
                    expression.type = variable?.type
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
                // TODO: Support auto promotion parameter checking
                val argumentTypes = expression.arguments.map {
                    checkExpression(it, scope)
                }

                val previousType = checkExpression(expression.previousExpression, scope)

                // Check function call expression's context, e.g companion context
                val functionSignature =
                    scope.findFunction(
                        expression.ownerReference?.path ?: previousType?.typeName,
                        expression.name!!.literal,
                        argumentTypes
                    )

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
            is IndexExpression -> {
                val previousExpressionType = checkExpression(expression.previousExpression, scope)
                val indexExpressionType = checkExpression(expression.indexExpression, scope)

                if (previousExpressionType !is ArrayType) {
                    reports += Error(
                        expression.previousExpression?.pos,
                        "Could not index non-array type"
                    )
                } else expression.type = previousExpressionType.baseType

                if (!TypeUtil.canCast(indexExpressionType, PrimitiveType.I32)) {
                    reports.reportTypeMismatch(
                        expression.indexExpression?.pos,
                        PrimitiveType.I32,
                        indexExpressionType
                    )
                } else expression.indexExpression?.castTo = PrimitiveType.I32

                expression.type
            }
            is UnaryExpression -> {
                val type = checkExpression(expression.expression, scope)

                if (type !is PrimitiveType || !type.isNumericType()) {
                    reports += Error(
                        expression.operator?.pos,
                        "Could not apply unary operator on object types",
                        "Remove this operator"
                    )
                } else expression.type = type

                expression.type
            }
            is BinaryExpression -> {
                val leftType = checkExpression(expression.left, scope)
                val rightType = checkExpression(expression.right, scope)

                if ((leftType !is PrimitiveType || !leftType.isNumericType()) || (rightType !is PrimitiveType || !rightType.isNumericType())) {
                    reports += Error(
                        expression.operator?.pos,
                        "Could not apply binary operator on object types",
                        "Remove this operator"
                    )
                } else expression.promote()

                expression.type
            }
            is ArrayInitialization -> {
                if (expression.inferTypeReference != null) {
                    when (val inferType = checkType(expression.inferTypeReference, scope)) {
                        null -> reports.reportUnknownTypeSymbol(expression.inferTypeReference)
                        !is ArrayType -> {
                            reports += Error(
                                expression.inferTypeReference.position,
                                "Inferred type must be array type",
                                "Consider add [] after type name"
                            )
                        }
                        else -> checkArrayType(expression, scope, inferType.baseType, expression.inferTypeReference.position)
                    }
                } else {
                    if (expression.elements.isEmpty()) {
                        reports += Error(
                            expression.pos,
                            "Could not infer type from an empty array",
                            "Consider adding at least one element or declare its type"
                        )
                    } else {
                        checkArrayType(expression, scope)
                    }
                }

                expression.type
            }
            is ArrayDeclaration -> {
                expression.type = checkType(expression.baseTypeReference, scope)

                expression.dimensionExpressions.forEach {
                    // Dimension expression's type must be able to cast into i32
                    val type = checkExpression(it, scope)

                    if (!TypeUtil.canCast(type, PrimitiveType.I32)) {
                        reports.reportTypeMismatch(
                            it?.pos,
                            PrimitiveType.I32,
                            type
                        )
                    } else it?.castTo = PrimitiveType.I32
                }

                expression.type
            }
            null -> null
        }
    }

    private fun checkArrayType(expression: ArrayInitialization, scope: Scope, forcedFinalType: Type? = null, inferTypePos: Position? = null) {
        // TODO: Support Object's promotion?
        val forceFinalType = forcedFinalType != null
        val expressionTypes = mutableListOf<Type?>()

        expression.elements.forEach {
            expressionTypes += checkExpression(it, scope)
        }

        val firstInferredType = if (forceFinalType) forcedFinalType else expressionTypes.first()
        var latestInferredType = firstInferredType

        if (firstInferredType is ArrayType && expressionTypes.any { it !is ArrayType }) {
            // Must be non-castable type relationship since any other types (including array itself)
            // cannot automatically cast into array.
            expressionTypes.forEachIndexed { i, type ->
                reports.reportTypeMismatch(
                    expression.elements[i]?.pos,
                    firstInferredType,
                    type
                )
            }
        } else {
            when (firstInferredType) {
                is ArrayType -> {
                    // Checks all types' dimension is same as first element's dimension
                    val firstTypeDimension = firstInferredType.getDimension()

                    expressionTypes.forEachIndexed { i, type ->
                        // Check their dimensions first
                        val dimension = (type as ArrayType).getDimension() // Already checked

                        if (firstTypeDimension != dimension) {
                            reports += Error(
                                expression.elements[i]?.pos,
                                "Dimension mismatch, requires $firstTypeDimension-dimension array but got $dimension-array"
                            )
                        } else {
                            // Then tries to infer their final type
                            // TODO: Support Object's promotion here
                            val latestFoundationType =
                                (latestInferredType as ArrayType).getFoundationType()
                            val currentFoundationType = type.getFoundationType()

                            if (latestFoundationType !is PrimitiveType && currentFoundationType !is PrimitiveType) {
                                // TODO: Support Object's Promotion here
                            } else {
                                if (!TypeUtil.canCast(latestFoundationType, currentFoundationType)) {
                                    if (!forceFinalType && latestFoundationType is PrimitiveType && currentFoundationType is PrimitiveType) {
                                        if (!latestFoundationType.isNumericType() || !currentFoundationType.isNumericType()) {
                                            reports.reportTypeMismatch(
                                                expression.elements[i]?.pos,
                                                latestFoundationType,
                                                expressionTypes[i]
                                            )
                                        }
                                        // If both are numeric types, it would be fine since current type is able to promote into latest inferred type
                                    } else {
                                        reports.reportTypeMismatch(
                                            expression.elements[i]?.pos,
                                            latestFoundationType,
                                            expressionTypes[i]
                                        )
                                    }
                                    // Boxed type cannot be promoted, and it's checked in canCast
                                } else {
                                    if (i == expressionTypes.lastIndex &&
                                        currentFoundationType is ClassType
                                    ) {
                                        // Covert boxed type into primitive type
                                        val currentPrimitiveType = PrimitiveType.fromClass(currentFoundationType.type())

                                        if (currentPrimitiveType != null) {
                                            if (!forceFinalType) {
                                                type.setFoundationType(currentPrimitiveType)
                                                latestInferredType = type
                                            } else {
                                                type.setFoundationType(forcedFinalType!!)
                                            }
                                        }
                                    } else if (!forceFinalType) latestInferredType = type
                                }
                            }
                        }
                    }
                }
                is PrimitiveType -> {
                    expressionTypes.forEachIndexed { i, type ->
                        if (type is ArrayType) {
                            reports += Error(
                                expression.elements[i]?.pos,
                                "Dimension mismatch, requires 1-dimension array but got ${type.getDimension()}-array"
                            )
                        } else if (type is PrimitiveType && !TypeUtil.canCast(latestInferredType, type)) {
                            if (!type.isNumericType() || !type.isNumericType()) {
                                reports.reportTypeMismatch(
                                    expression.elements[i]?.pos,
                                    expressionTypes[i],
                                    latestInferredType
                                )
                            }
                            // If both are numeric types, it would be fine since current type is able to promote into latest inferred type
                        } else latestInferredType = type
                    }
                }
                else -> {
                    // TODO: Support Object's Promotion
                }
            }
        }

        if (latestInferredType != null)
            expression.type = ArrayType(latestInferredType!!)

        val latestFoundationType =
            if (latestInferredType is ArrayType) (latestInferredType as ArrayType).getFoundationType()
            else latestInferredType

        if (latestFoundationType != null) {
            expression.elements.forEach {
                traverseArrayTree(it, { expr ->
                    expr.castTo = latestFoundationType
                }, { expr ->
                    val currentNodeType = (expr.type as ArrayType)

                    currentNodeType.baseType = ArrayType.fromDimension(
                        latestFoundationType,
                        currentNodeType.getDimension() - 1
                    )
                    currentNodeType.setFoundationType(latestFoundationType)
                })
            }
        }
    }

    // Used to traverse through array structure and do specific job, e.g. assigning final cast type
    private fun traverseArrayTree(
        expression: Expression?,
        lastNodeAction: (Expression) -> Unit,
        nodeAction: (Expression) -> Unit
    ) {
        if (expression != null) {
            if (expression.type is ArrayType) {
                val expressions = expression.getExpressions()

                if (expressions != null && expressions.isNotEmpty()) {
                    nodeAction(expression)

                    expressions.forEach {
                        traverseArrayTree(it, lastNodeAction, nodeAction)
                    }
                } else lastNodeAction(expression)
            } else lastNodeAction(expression)
        }
    }
}