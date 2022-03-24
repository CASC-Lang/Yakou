package org.casc.lang.checker

import org.casc.lang.ast.*
import org.casc.lang.ast.Function
import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report
import org.casc.lang.compilation.Warning
import org.casc.lang.table.*
import org.casc.lang.utils.getOrElse
import org.casc.lang.utils.pforEach
import org.casc.lang.utils.pmap
import org.casc.lang.utils.pmapNotNull
import java.lang.reflect.Modifier
import java.io.File as JFile

class Checker(private val preference: AbstractPreference) {
    companion object {
        private val ignoreUnusedExpressions =
            listOf(
                AssignmentExpression::class.java,
                FunctionCallExpression::class.java,
                ConstructorCallExpression::class.java
            )
    }

    private lateinit var topScope: Scope
    private var reports: MutableSet<Report> = mutableSetOf()

    fun checkDeclaration(file: File): Triple<List<Report>, File, Scope> {
        val (checkedClass, scope) = checkClassDeclaration(file.clazz, file.path)

        file.clazz = checkedClass

        return Triple(reports.toList(), file, scope)
    }

    private fun checkClassDeclaration(clazz: Class, filePath: String): Pair<Class, Scope> {
        topScope = Scope(preference, companion = false, mutable = false, Accessor.Pub, clazz.getReference())

        val classScope = Scope(
            topScope,
            false, // TODO: Allow (nested) class has `comp`
            clazz.mutKeyword != null,
            clazz.accessor,
            clazz.packageReference
        )

        if (clazz.packageReference != null) {
            val packagePath = clazz.packageReference.fullQualifiedPath.replace('.', '/')

            if (!JFile(filePath).parentFile.toPath().endsWith(packagePath)) {
                reports += Error(
                    clazz.packageReference.pos,
                    "Package path mismatch",
                    "Try rename parent folders' name or rename package name"
                )
            }
        }

        checkIdentifierIsKeyword(clazz.name)

        if (clazz.parentClassReference != null) {
            val parentClassType = findType(clazz.parentClassReference, classScope)

            classScope.parentClassPath = parentClassType?.getReference()

            if (parentClassType != null) {
                if (parentClassType !is ClassType) {
                    reports += Error(
                        clazz.parentClassReference.pos,
                        "Cannot inherit from non-class type ${parentClassType.asCASCStyle()}"
                    )
                } else if (!parentClassType.mutable) {
                    reports += Error(
                        clazz.parentClassReference.pos,
                        "Cannot inherit from final class ${parentClassType.asCASCStyle()}",
                        "Add `mut` to class ${parentClassType.asCASCStyle()}"
                    )
                } else if (parentClassType.type(preference)?.let { Modifier.isFinal(it.modifiers) } == true) {
                    reports += Error(
                        clazz.parentClassReference.pos,
                        "Cannot inherit from final class ${parentClassType.asCASCStyle()}",
                        "Add `mut` to class ${parentClassType.asCASCStyle()}"
                    )
                }
            } else {
                reports.reportUnknownTypeSymbol(clazz.parentClassReference)
            }
        }

        clazz.fields.pforEach {
            checkField(it, classScope)
        }
        clazz.constructors = clazz.constructors.pmap {
            checkConstructor(it, classScope)
        }
        clazz.functions = clazz.functions.pmap {
            checkFunction(it, classScope)
        }

        return clazz to classScope
    }

    fun check(file: File, classScope: Scope): Pair<List<Report>, File> {
        val checkedFile = checkFile(file, classScope)

        return reports.toList() to checkedFile
    }

    private fun checkIdentifierIsKeyword(identifierToken: Token?, isVariable: Boolean = false) {
        if (isVariable && (identifierToken?.literal == "self" || identifierToken?.literal == "super"))
            return

        if (Token.keywords.contains(identifierToken?.literal)) {
            reports += Error(
                identifierToken?.pos,
                "Cannot use ${identifierToken?.literal} as identifier since it's a keyword"
            )
        }
    }

    private fun checkAccessible(currentScope: Scope, errorPos: Position?, target: HasAccessor, targetOwnerClass: Type) {
        when (target.accessor) {
            Accessor.Pub -> {}
            Accessor.Prot -> {
                // TODO: Store parent class data in ClassType so we can remove `Type#type()`
                val accessible = targetOwnerClass.type(preference)
                    ?.isAssignableFrom(
                        currentScope.findType(currentScope.classReference)?.type(preference) ?: Any::class.java
                    )
                    .getOrElse()

                if (!accessible) {
                    reports += Error(
                        errorPos,
                        "Unable to access `prot` class member from unrelated hierarchy tree class"
                    )
                }
            }
            Accessor.Intl -> {
                val accessible = targetOwnerClass.isSamePackage(currentScope.findType(currentScope.classReference))

                if (!accessible) {
                    reports += Error(
                        errorPos,
                        "Unable to access `intl` class member from unrelated package"
                    )
                }
            }
            Accessor.Priv -> {
                reports += Error(
                    errorPos,
                    "Unable to access `priv` class member from other class"
                )
            }
        }
    }

    private fun checkFile(file: File, classScope: Scope): File {
        file.clazz = checkClass(file.clazz, classScope)

        return file
    }

    private fun checkClass(clazz: Class, classScope: Scope): Class {
        topScope = Scope(preference, companion = false, mutable = false, Accessor.Pub, clazz.getReference())

        clazz.usages.pmapNotNull {
            it.tokens.forEach(::checkIdentifierIsKeyword)

            val type = TypeUtil.asType(it, preference)

            if (type == null) {
                reports.reportUnknownTypeSymbol(it)

                null
            } else it
        }.forEach(classScope.usages::add)

        clazz.companionBlock.forEach {
            checkStatement(it, Scope(classScope), PrimitiveType.Unit)
        }

        clazz.constructors.pforEach {
            checkConstructorBody(it, Scope(classScope))
        }

        clazz.functions.pforEach {
            checkFunctionBody(it, Scope(classScope, isCompScope = it.selfKeyword == null))

            if (!checkControlFlow(it.statements, it.returnType)) {
                // Not all code path returns value
                reports += Error(
                    it.name?.pos,
                    "Not all code path returns value"
                )
            }
        }

        return clazz
    }

    private fun checkField(field: Field, scope: Scope): Field {
        checkIdentifierIsKeyword(field.name)

        val fieldType = scope.findType(field.typeReference)

        if (fieldType == null) {
            reports.reportUnknownTypeSymbol(field.typeReference!!)
        } else field.type = fieldType

        scope.registerField(field)

        return field
    }

    private fun checkConstructor(constructor: Constructor, scope: Scope): Constructor {
        // Validate types first then register it to scope
        // Check if parameter has duplicate names
        val localScope = Scope(scope)
        val duplicateParameters = constructor.parameters
            .groupingBy {
                checkIdentifierIsKeyword(it.name)

                it.name
            }
            .eachCount()
            .filter { it.value > 1 }
        var validationPass = true

        if (duplicateParameters.isNotEmpty()) {
            duplicateParameters.forEach { (parameter, _) ->
                reports += Error(
                    parameter!!.pos,
                    "Parameter ${parameter.literal} is already declared in constructor new(${
                        constructor.parameters.mapNotNull { it.typeReference?.fullQualifiedPath }.joinToString()
                    })"
                )
            }

            validationPass = false
        } else {
            constructor.parameterTypes = constructor.parameters.map {
                val type = findType(it.typeReference, localScope)

                if (type == null) reports.reportUnknownTypeSymbol(it.typeReference!!)
                else localScope.registerVariable(false, it.name!!.literal, type)

                type
            }
        }

        if (constructor.parentReference == null)
            constructor.parentReference = Reference(Any::class.java)

        constructor.ownerType = findType(constructor.ownerReference, localScope)
        constructor.parentType = findType(constructor.parentReference, localScope)

        constructor.parentConstructorArgumentsTypes = constructor.parentConstructorArguments.mapNotNull {
            if (it == null) null
            else {
                val type = checkExpression(it, localScope)

                if (type == null) reports.reportUnknownTypeSymbol(Reference("<Unknown>", "<Unknown>", pos = it.pos))

                type
            }
        }

        if (validationPass)
            scope.registerSignature(constructor)

        return constructor
    }

    // TODO: Track if-else and match branches so compiler can know whether all paths have return value or not
    private fun checkFunction(function: Function, scope: Scope): Function {
        checkIdentifierIsKeyword(function.name)

        // Validate types first then register it to scope
        // Check if parameter has duplicate names
        val duplicateParameters = function.parameters
            .groupingBy {
                checkIdentifierIsKeyword(it.name)

                it.name
            }
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
                val type = findType(it.typeReference, scope)

                if (type == null) {
                    reports.reportUnknownTypeSymbol(it.typeReference!!)

                    return@map null
                }

                type
            }
        }

        function.returnType =
            if (function.returnTypeReference == null) PrimitiveType.Unit
            else {
                val type = findType(function.returnTypeReference, scope)

                if (type == null) {
                    reports.reportUnknownTypeSymbol(function.returnTypeReference)
                    validationPass = false

                    null
                } else type
            }
        function.ownerType = findType(function.ownerReference, scope)

        // Check is overriding parent function
        val parentFunction =
            scope.findSignature(scope.parentClassPath, function.name!!.literal, function.parameterTypes ?: listOf())

        if (parentFunction != null) {
            if (function.ovrdKeyword == null) {
                // Overriding function without `ovrd`
                reports += Error(
                    function.name.pos,
                    "${function.name.literal}(${
                        function.parameterTypes?.mapNotNull { it?.typeName }?.joinToString()
                    }) exists in parent class ${scope.parentClassPath} but doesn't declared with `ovrd`",
                    "Add `ovrd` keyword"
                )
            } else {
                if (parentFunction.companion) {
                    // Overriding a companion function
                    reports += Error(
                        function.ovrdKeyword.pos,
                        "Cannot overriding companion function ${parentFunction.ownerReference.asCASCStyle()}.${parentFunction.name}(${
                            function.parameterTypes?.mapNotNull { it?.typeName }?.joinToString()
                        })",
                        "Remove `ovrd` keyword"
                    )
                }

                if (function.selfKeyword == null) {
                    // Overriding function but itself is companion function (no `self` declared)
                    reports += Error(
                        function.name.pos,
                        "Cannot overriding function with companion function",
                        "Add `self` keyword at the start of parameters"
                    )
                }
            }
        } else if (function.ovrdKeyword != null) {
            // Redundant `ovrd` keyword
            reports += Error(
                function.ovrdKeyword.pos,
                "Redundant `ovrd`, ${function.name.literal}(${
                    function.parameterTypes?.mapNotNull { it?.typeName }?.joinToString()
                }) overrides nothing",
                "Remove this keyword"
            )
        }

        if (validationPass) scope.registerSignature(function)

        return function
    }

    private fun checkConstructorBody(constructor: Constructor, scope: Scope) {
        if (constructor.superKeyword != null) {
            // `super` call
            val superCallSignature = scope.findSignature(
                constructor.parentReference,
                "<init>",
                constructor.parentConstructorArgumentsTypes
            )

            if (superCallSignature == null) {
                // No super call match
                reports += Error(
                    constructor.newKeyword?.pos,
                    "Cannot find matched super call `super`(${
                        constructor.parentConstructorArgumentsTypes.mapNotNull { it?.typeName }.joinToString()
                    })"
                )
            } else constructor.parentConstructorSignature = superCallSignature
        } else if (constructor.selfKeyword != null) {
            // `this` call
            val thisCallSignature = scope.findSignature(
                constructor.ownerReference,
                "<init>",
                constructor.parentConstructorArgumentsTypes
            )

            if (thisCallSignature == null) {
                // No super call match
                reports += Error(
                    constructor.newKeyword?.pos,
                    "Cannot find matched super call `this`(${
                        constructor.parentConstructorArgumentsTypes.mapNotNull { it?.typeName }.joinToString()
                    })"
                )
            } else constructor.parentConstructorSignature = thisCallSignature
        } else {
            if (scope.parentClassPath != null) {
                // Requires `super` call
                reports += Error(
                    constructor.newKeyword?.pos,
                    "Class ${scope.classReference} extends class ${scope.parentClassPath} but doesn't `super` any parent class' constructor",
                    "Add `super` call after constructor declaration"
                )
            } else constructor.parentConstructorSignature = FunctionSignature(
                Reference(Any::class.java),
                companion = true,
                mutable = false,
                Accessor.Pub,
                "<init>",
                listOf(),
                PrimitiveType.Unit
            )
        }

        constructor.parameters.forEachIndexed { i, parameter ->
            scope.registerVariable(false, parameter.name!!.literal, constructor.parameterTypes[i])
        }

        constructor.statements.forEach {
            checkStatement(it, scope, PrimitiveType.Unit)
        }
    }

    private fun checkFunctionBody(function: Function, scope: Scope) {
        function.parameters.forEachIndexed { i, parameter ->
            scope.registerVariable(false, parameter.name!!.literal, function.parameterTypes?.get(i))
        }

        function.statements.forEach {
            checkStatement(it, scope, function.returnType ?: PrimitiveType.Unit)
        }
    }

    private fun findType(reference: Reference?, scope: Scope): Type? =
        scope.findType(reference)

    private fun checkStatement(
        statement: Statement?,
        scope: Scope,
        returnType: Type,
        retainLastExpression: Boolean = false,
        useSameScope: Boolean = false
    ) {
        when (statement) {
            is VariableDeclaration -> {
                val name = statement.name!!.literal

                if (name == "self" || name == "super") {
                    reports += Error(
                        statement.name.pos,
                        "Cannot declare `$name` as local variable",
                        "Rename this variable"
                    )
                } else checkIdentifierIsKeyword(statement.name)

                val expressionType = checkExpression(statement.expression, scope)

                if (!scope.registerVariable(statement.mutKeyword != null, name, expressionType)) {
                    reports += Error(
                        statement.pos!!,
                        "Variable $name is already declared in same context"
                    )
                } else {
                    if (expressionType == PrimitiveType.Unit) {
                        reports += Error(
                            statement.expression!!.pos,
                            "Could not store void type into variable"
                        )
                    }

                    statement.index = scope.findVariableIndex(name)
                }
            }
            is IfStatement -> {
                val conditionType = checkExpression(statement.condition, scope)

                if (scope.canCast(conditionType, PrimitiveType.Bool)) {
                    statement.condition?.castTo = PrimitiveType.Bool
                } else {
                    reports.reportTypeMismatch(
                        statement.condition?.pos,
                        PrimitiveType.Bool,
                        conditionType
                    )
                }

                checkStatement(statement.trueStatement!!, scope, returnType, retainLastExpression)

                if (statement.elseStatement != null) {
                    checkStatement(statement.elseStatement, scope, returnType, retainLastExpression)
                }
            }
            is JForStatement -> {
                val innerScope = Scope(scope, isLoopScope = true)

                checkStatement(statement.initStatement, innerScope, returnType, useSameScope = true)

                if (statement.condition != null) {
                    val conditionType = checkExpression(statement.condition, innerScope)

                    if (scope.canCast(conditionType, PrimitiveType.Bool)) {
                        statement.condition.castTo = PrimitiveType.Bool
                    } else {
                        reports.reportTypeMismatch(
                            statement.condition.pos,
                            PrimitiveType.Bool,
                            conditionType
                        )
                    }
                }

                checkExpression(statement.postExpression, innerScope)

                checkStatement(statement.statement, innerScope, returnType, useSameScope = true)
            }
            is BlockStatement -> {
                statement.statements.forEachIndexed { i, it ->
                    checkStatement(
                        it!!,
                        if (useSameScope) scope else Scope(scope),
                        returnType,
                        if (i == statement.statements.size - 1) retainLastExpression else false
                    )
                }
            }
            is ExpressionStatement -> {
                if (statement.expression != null && !retainLastExpression && !ignoreUnusedExpressions.contains(
                        statement.expression::class.java
                    )
                ) {
                    if (statement.expression is UnaryExpression &&
                        (statement.expression.operator?.type == TokenType.DoublePlus || statement.expression.operator?.type == TokenType.DoubleMinus)
                    ) {
                        checkExpression(statement.expression, scope)
                    } else reports += Error(
                        statement.pos,
                        "Unused expression",
                        "Consider remove this line"
                    )
                } else {
                    checkExpression(statement.expression, scope)

                    if (retainLastExpression && statement.expression is InvokeCall) {
                        statement.expression.retainValue = true
                    }
                }
            }
            is ReturnStatement -> {
                val expressionType = checkExpression(statement.expression, scope)

                if (!scope.canCast(expressionType, returnType)) {
                    reports.reportTypeMismatch(statement.pos!!, returnType, expressionType)
                } else statement.returnType = returnType
            }
            else -> {}
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
            is CharLiteral -> {
                expression.type = PrimitiveType.Char

                expression.type
            }
            is StrLiteral -> {
                expression.type = PrimitiveType.Str

                expression.type
            }
            is BoolLiteral -> {
                expression.type = PrimitiveType.Bool

                expression.type
            }
            is NullLiteral -> {
                expression.type = PrimitiveType.Null

                expression.type
            }
            is AssignmentExpression -> {
                val leftType = checkExpression(expression.leftExpression, scope)
                val rightType = checkExpression(expression.rightExpression, scope)

                if (expression.leftExpression is IdentifierCallExpression) {
                    val name = expression.leftExpression.name!!.literal

                    fun checkFieldAssignment(field: ClassField?) {
                        if (field == null) {
                            reports += Error(
                                expression.leftExpression.pos,
                                "Unknown identifier $name"
                            )
                        } else {
                            if (!field.mutable) {
                                reports += Error(
                                    expression.leftExpression.pos,
                                    "Field $name is not mutable",
                                    "Declare field $name with `mut` keyword"
                                )
                            }

                            if (!field.companion && field.ownerReference != scope.classReference && field.ownerReference != scope.parentClassPath) {
                                reports += Error(
                                    expression.leftExpression.pos,
                                    "Cannot access non-companion field $name from other context"
                                )
                            }

                            expression.leftExpression.isAssignedBy = true
                        }
                    }

                    if (name == "self" || name == "super") {
                        // Attempt to override whole owner / parent class
                        reports += Error(
                            expression.leftExpression.pos,
                            "Cannot assign current class or parent class while executing its function",
                            "Remove this line"
                        )
                    } else if (expression.leftExpression.ownerReference != null) {
                        // Field assignment
                        checkFieldAssignment(scope.findField(expression.leftExpression.ownerReference, name))
                    } else {
                        // Current class field / local variable assignment
                        val variable = scope.findVariable(name)

                        if (variable == null) {
                            // Lookup local field
                            checkFieldAssignment(scope.findField(scope.classReference, name))
                        } else {
                            if (!variable.mutable) {
                                reports += Error(
                                    expression.leftExpression.pos,
                                    "Variable $name is not mutable",
                                    "Declare variable $name with `mut` keyword"
                                )
                            }

                            if (rightType == PrimitiveType.Unit) {
                                reports += Error(
                                    expression.rightExpression?.pos,
                                    "Could not store void type into variable"
                                )
                            }

                            if (rightType == PrimitiveType.Null) {
                                variable.type = PrimitiveType.Null
                            }

                            if (scope.scopeDepth > variable.declaredScopeDepth &&
                                expression.rightExpression is BinaryExpression &&
                                expression.rightExpression.isConcatExpression
                            ) {
                                reports += Warning(
                                    expression.pos,
                                    "Potential unnecessary performance cost while concatenating string through `+` and assign to variable in parent context in loop",
                                    "Consider use `java::lang::StringBuilder`"
                                )
                            }

                            expression.leftExpression.isAssignedBy = true
                        }
                    }
                } else if (expression.leftExpression is IndexExpression) {
                    expression.leftExpression.isAssignedBy = expression.rightExpression !is IndexExpression
                } else {
                    reports += Error(
                        expression.leftExpression?.pos,
                        "Cannot assign value to non-variable reference",
                        "Change this into variable"
                    )
                }

                if (!scope.canCast(rightType, expression.leftExpression?.type)) {
                    reports.reportTypeMismatch(
                        expression.rightExpression?.pos,
                        expression.leftExpression?.type,
                        rightType
                    )
                } else {
                    expression.rightExpression?.castTo = leftType
                    expression.type = leftType
                }

                expression.type
            }
            is IdentifierCallExpression -> {
                val ownerReference = expression.ownerReference

                checkIdentifierIsKeyword(expression.name, isVariable = true)

                fun checkCompanionAccessibility(field: ClassField) {
                    if (!field.companion && scope.isCompScope) {
                        reports += Error(
                            expression.name?.pos,
                            "Cannot access non-companion field ${expression.name?.literal} from companion context"
                        )
                    }
                }

                if (ownerReference != null) {
                    // Appointed class field
                    val field = scope.findField(ownerReference, expression.name!!.literal)

                    if (field == null) {
                        reports += Error(
                            expression.pos,
                            "Field ${expression.name.literal} does not exist in class ${ownerReference.fullQualifiedPath}"
                        )
                    } else {
                        checkCompanionAccessibility(field)
                        if (ownerReference != scope.classReference)
                            checkAccessible(scope, expression.name.pos, field, field.type)

                        expression.type = field.type
                        expression.isCompField = field.companion
                        expression.ownerReference = field.ownerReference
                    }
                } else if (expression.previousExpression != null) {
                    // Chain calling
                    val previousType = checkExpression(expression.previousExpression, scope)
                    val field = scope.findField(previousType?.getReference(), expression.name!!.literal)

                    if (field == null) {
                        reports += Error(
                            expression.pos,
                            "Field ${expression.name.literal} does not exist in class ${previousType?.typeName}"
                        )
                    } else {
                        checkCompanionAccessibility(field)
                        if (field.ownerReference != scope.classReference)
                            checkAccessible(scope, expression.name.pos, field, field.type)

                        expression.type = field.type
                        expression.isCompField = field.companion
                        expression.ownerReference = field.ownerReference
                    }
                } else if (expression.name?.literal == "self" || expression.name?.literal == "super") {
                    expression.type = when (expression.name.literal) {
                        "self" -> scope.findType(scope.classReference)
                        "super" -> scope.findType(scope.parentClassPath)
                        else -> null
                    }

                    expression.index = 0
                } else {
                    // Check identifier is class name or not
                    val classType = scope.findType(Reference(expression.name!!.literal))

                    if (classType != null) {
                        // Class companion member call

                        expression.type = classType
                        expression.isClassName = true
                    } else {
                        // Local variable / current class field
                        val variable = scope.findVariable(expression.name.literal)

                        if (variable == null) {
                            // Lookup for current class' field
                            val field = scope.findField(null, expression.name.literal)

                            if (field != null) {
                                checkCompanionAccessibility(field)

                                expression.type = field.type
                                expression.isCompField = field.companion
                                expression.ownerReference = field.ownerReference
                            } else {
                                reports += Error(
                                    expression.name.pos,
                                    "Unknown identifier ${expression.name.literal}"
                                )
                            }
                        } else {
                            expression.type = variable.type
                            expression.index = scope.findVariableIndex(expression.name.literal)
                        }
                    }
                }

                expression.type
            }
            is FunctionCallExpression -> {
                // TODO: Support auto promotion parameter checking
                val previousExpression = expression.previousExpression
                val argumentTypes = expression.arguments.map {
                    checkExpression(it, scope)
                }

                val previousType = checkExpression(previousExpression, scope)

                if (previousExpression is IdentifierCallExpression && previousExpression.name?.literal == "super")
                    expression.superCall = true

                // Check function call expression's context, e.g companion context
                val ownerReference = expression.ownerReference ?: previousType?.getReference() ?: scope.classReference
                val functionSignature =
                    scope.findSignature(
                        ownerReference,
                        expression.name!!.literal,
                        argumentTypes
                    )

                if (functionSignature == null) {
                    // No function matched
                    reports += Error(
                        expression.pos!!,
                        "Function $ownerReference#${expression.name.literal}(${
                            argumentTypes.mapNotNull { it?.typeName }.joinToString()
                        }) does not exist in current context"
                    )
                } else {
                    if (ownerReference != scope.classReference)
                        checkAccessible(
                            scope,
                            expression.name.pos,
                            functionSignature,
                            findType(functionSignature.ownerReference, scope)!!
                        )

                    if (functionSignature.ownerReference == expression.ownerReference) {
                        // Function's owner class is same as current class
                        if (previousExpression == null) {
                            if (expression.inCompanionContext && !functionSignature.companion) {
                                reports += Error(
                                    expression.pos!!,
                                    "Function $ownerReference#${expression.name.literal}(${
                                        argumentTypes.mapNotNull { it?.typeName }.joinToString()
                                    }) exists in non-companion context but it's called from companion context",
                                    "Consider move its declaration into companion context"
                                )
                            }
                        }
                    } else {
                        // Function's owner class is outside this context
                        if (previousExpression == null) {
                            if (!functionSignature.companion) {
                                reports += Error(
                                    expression.pos!!,
                                    "Function $ownerReference#${expression.name.literal}(${
                                        argumentTypes.mapNotNull { it?.typeName }.joinToString()
                                    }) exists in non-companion context but is called from other context",
                                    "Consider move its declaration into companion context"
                                )
                            }
                        } else if (scope.isChildType(
                                functionSignature.ownerReference,
                                previousType?.getReference() ?: scope.classReference
                            )
                        ) expression.superCall = true
                    }

                    expression.type = functionSignature.returnType
                    expression.referenceFunctionSignature = functionSignature
                }

                expression.type
            }
            is ConstructorCallExpression -> {
                val argumentTypes = expression.arguments.map {
                    checkExpression(it, scope)
                }

                // Check owner type has matched signature
                val signature = scope.findSignature(
                    expression.constructorOwnerReference,
                    "<init>",
                    argumentTypes
                )

                if (signature == null) {
                    // No match
                    reports += Error(
                        expression.pos,
                        "Constructor ${expression.constructorOwnerReference?.asCASCStyle()}#new(${
                            argumentTypes.mapNotNull { it?.typeName }.joinToString()
                        }) does not exist"
                    )
                } else {
                    if (expression.constructorOwnerReference != scope.classReference)
                        checkAccessible(scope, expression.pos, signature, findType(signature.ownerReference, scope)!!)

                    expression.type = signature.returnType
                    expression.referenceFunctionSignature = signature
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

                if (!scope.canCast(indexExpressionType, PrimitiveType.I32)) {
                    reports.reportTypeMismatch(
                        expression.indexExpression?.pos,
                        PrimitiveType.I32,
                        indexExpressionType
                    )
                } else expression.indexExpression?.castTo = PrimitiveType.I32

                expression.type
            }
            is UnaryExpression -> {
                when (val type = checkExpression(expression.expression, scope)) {
                    is PrimitiveType -> when (expression.operator?.type) {
                        TokenType.Plus, TokenType.Minus, TokenType.DoublePlus, TokenType.DoubleMinus -> {
                            if (!type.isNumericType()) {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-numeric type",
                                    "Remove this operator"
                                )
                            } else expression.type = type
                        }
                        TokenType.Tilde -> {
                            if ((PrimitiveType.promotionTable[type] ?: 2) > 2) {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-integer type",
                                    "Remove this operator"
                                )
                            }
                        }
                        TokenType.Bang -> {
                            if (type != PrimitiveType.Bool) {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-bool type",
                                    "Remove this operator"
                                )
                            } else expression.type = type
                        }
                        else -> {} // Should not be here
                    }
                    else -> {
                        reports += Error(
                            expression.operator?.pos,
                            "Could not apply unary operator on object type",
                            "Remove this operator"
                        )
                    }
                }

                expression.type
            }
            is BinaryExpression -> {
                val operator = expression.operator?.type
                val leftType = checkExpression(expression.left, scope)
                val rightType = checkExpression(expression.right, scope)

                if (operator != TokenType.EqualEqual && operator != TokenType.BangEqual && (leftType !is PrimitiveType || rightType !is PrimitiveType)) {
                    reports += Error(
                        expression.operator?.pos,
                        "Could not apply binary operator on object type",
                        "Remove this operator"
                    )
                } else {
                    when (operator) {
                        TokenType.Plus, TokenType.Minus, TokenType.Star, TokenType.Slash, TokenType.Percentage -> {
                            if (operator == TokenType.Plus && (leftType == PrimitiveType.Str || rightType == PrimitiveType.Str)) {
                                expression.type = PrimitiveType.Str
                                expression.isConcatExpression = true
                            } else if (leftType is PrimitiveType && rightType is PrimitiveType && leftType.isNumericType() && rightType.isNumericType()) {
                                expression.promote()
                            } else {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-numeric type",
                                    "Remove this operator"
                                )
                            }
                        }
                        TokenType.Greater, TokenType.GreaterEqual, TokenType.Lesser, TokenType.LesserEqual -> {
                            if (leftType is PrimitiveType && rightType is PrimitiveType && leftType.isNumericType() && rightType.isNumericType()) {
                                expression.promote()
                                expression.type = PrimitiveType.Bool
                            } else {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-numeric type",
                                    "Remove this operator"
                                )
                            }
                        }
                        TokenType.DoublePipe, TokenType.DoubleAmpersand -> {
                            if (leftType == PrimitiveType.Bool && rightType == PrimitiveType.Bool) {
                                expression.type = PrimitiveType.Bool
                            } else {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-bool type",
                                    "Remove this operator"
                                )
                            }
                        }
                        TokenType.Pipe, TokenType.Hat, TokenType.Ampersand,
                        TokenType.DoubleGreater, TokenType.TripleGreater, TokenType.DoubleLesser -> {
                            if ((PrimitiveType.promotionTable[leftType] ?: 2) <= 2 &&
                                (PrimitiveType.promotionTable[rightType] ?: 2) <= 2
                            ) {
                                expression.promote()
                            } else {
                                reports += Error(
                                    expression.operator.pos,
                                    "Could not apply ${expression.operator.literal} on non-integer type",
                                    "Remove this operator"
                                )
                            }
                        }
                        TokenType.EqualEqual, TokenType.BangEqual -> {
                            if (leftType == PrimitiveType.Null) {
                                if (PrimitiveType.isJvmPrimitive(rightType)) {
                                    reports += Error(
                                        expression.right?.pos,
                                        "Primitive types are always not null",
                                        "Only primitive wrapper classes could be determined to be null"
                                    )
                                }
                            } else if (rightType == PrimitiveType.Null) {
                                if (PrimitiveType.isJvmPrimitive(leftType)) {
                                    reports += Error(
                                        expression.left?.pos,
                                        "Primitive types are always not null",
                                        "Only primitive wrapper classes could be determined to be null"
                                    )
                                }
                            }

                            expression.type = PrimitiveType.Bool
                        }
                        else -> {}
                    }
                }

                expression.type
            }
            is ParenthesizedExpression -> {
                expression.type = checkExpression(expression.expression, scope)
                expression.type
            }
            is ArrayInitialization -> {
                if (expression.inferTypeReference != null) {
                    when (val inferType = findType(expression.inferTypeReference, scope)) {
                        null -> reports.reportUnknownTypeSymbol(expression.inferTypeReference)
                        !is ArrayType -> {
                            reports += Error(
                                expression.inferTypeReference.pos,
                                "Inferred type must be array type",
                                "Consider add [] after type name"
                            )
                        }
                        else -> checkArrayType(
                            expression,
                            scope,
                            inferType.baseType
                        )
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
                expression.type = findType(expression.baseTypeReference, scope)

                expression.dimensionExpressions.forEach {
                    // Dimension expression's type must be able to cast into i32
                    val type = checkExpression(it, scope)

                    if (!scope.canCast(type, PrimitiveType.I32)) {
                        reports.reportTypeMismatch(
                            it?.pos,
                            PrimitiveType.I32,
                            type
                        )
                    } else it?.castTo = PrimitiveType.I32
                }

                expression.type
            }
            is IfExpression -> {
                val conditionType = checkExpression(expression.condition, scope)

                if (scope.canCast(conditionType, PrimitiveType.Bool)) {
                    expression.condition?.castTo = PrimitiveType.Bool
                } else {
                    reports.reportTypeMismatch(
                        expression.condition?.pos,
                        PrimitiveType.Bool,
                        conditionType
                    )
                }

                checkStatement(expression.trueStatement!!, scope, PrimitiveType.Unit, true)

                if (expression.elseStatement != null) {
                    checkStatement(expression.elseStatement, scope, PrimitiveType.Unit, true)

                    // Find the most common type for all branches
                    val type = checkBranchCommonType(expression, scope)

                    if (type == null) {
                        reports += Error(
                            expression.pos,
                            "Unable to infer the most common type of all branches' return value"
                        )
                    } else expression.type = type
                } else {
                    reports += Error(
                        expression.pos,
                        "If expression must have else clause"
                    )
                }

                expression.type
            }
            null -> null
        }
    }

    private fun checkBranchCommonType(
        expression: IfExpression,
        scope: Scope
    ): Type? {
        fun getReturnValueType(statement: Statement?): Type? =
            when (statement) {
                null -> null
                is IfStatement -> {
                    val trueType = getReturnValueType(statement.trueStatement)
                    val elseType = getReturnValueType(statement.elseStatement)

                    TypeUtil.getCommonType(trueType, elseType, preference)
                }
                is BlockStatement -> getReturnValueType(statement.statements.lastOrNull())
                is ExpressionStatement -> statement.expression?.type
                else -> null
            }

        val type = getReturnValueType(expression.trueStatement)
        val elseType = getReturnValueType(expression.elseStatement)

        return TypeUtil.getCommonType(type, elseType, preference)
    }

    private fun checkArrayType(
        expression: ArrayInitialization,
        scope: Scope,
        forcedFinalType: Type? = null
    ) {
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
                                if (!scope.canCast(latestFoundationType, currentFoundationType)) {
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
                                        val currentPrimitiveType =
                                            PrimitiveType.fromClass(currentFoundationType.type(preference))

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
                        } else if (type is PrimitiveType && !scope.canCast(latestInferredType, type)) {
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

    /**
     * checkControlFlow does not check every return's type, which is already done by previous process
     */
    private fun checkControlFlow(statements: List<Statement?>, returnType: Type?): Boolean {
        val lastStatement = statements.lastOrNull()

        return if (returnType == PrimitiveType.Unit) true
        else when (lastStatement) {
            is ReturnStatement -> true
            is IfStatement -> {
                if (lastStatement.elseStatement == null) false
                else checkControlFlow(listOf(lastStatement.trueStatement), returnType) &&
                        checkControlFlow(listOf(lastStatement.elseStatement), returnType)
            }
            is BlockStatement -> checkControlFlow(lastStatement.statements, returnType)
            else -> false
        }
    }
}