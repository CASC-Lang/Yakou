package org.yakou.lang.bind

import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Attribute
import org.objectweb.asm.Opcodes
import org.yakou.lang.ast.*
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.util.SpanHelper
import org.yakou.lang.util.colorize

class Binder(private val compilationUnit: CompilationUnit) {
    companion object {
        val PACKAGE_LEVEL_FUNCTION_ADDITIONAL_FLAGS: IntArray = intArrayOf(Opcodes.ACC_STATIC)
    }

    private val table = compilationUnit.session.table

    private var currentPackagePath: Path.SimplePath = Path.SimplePath(listOf())
    private var currentClassPath: Path.SimplePath = Path.SimplePath(listOf())
    private var currentFunctionInstance: ClassMember.Fn? = null
    private var currentScope: Scope? = null
    private var topLevel: Boolean = true

    fun bind() {
        bindYkFile(compilationUnit.ykFile!!)
    }

    private fun bindYkFile(ykFile: YkFile) {
        for (item in ykFile.items)
            bindItemDeclaration(item)
    }

    private fun bindItemDeclaration(item: Item) {
        when (item) {
            is Item.Package -> {
                val previousPackagePath = currentPackagePath
                currentPackagePath = currentPackagePath.append(item.identifier)

                table.registerPackageClass(currentPackagePath.toString())

                if (item.items != null)
                    for (innerItem in item.items)
                        bindItemDeclaration(innerItem)

                currentPackagePath = previousPackagePath
            }
            is Item.Const -> bindConstDeclaration(item)
            is Item.StaticField -> bindStaticFieldDeclaration(item)
            is Item.Class -> bindClassDeclaration(item)
            is Item.Function -> bindFunctionDeclaration(item)
        }
    }


    private fun bindConstDeclaration(const: Item.Const) {
        const.typeInfo = bindType(const.type)

        val field = ClassMember.Field.fromConst(
            currentPackagePath,
            currentClassPath,
            const,
            Opcodes.ACC_STATIC
        )

        if (!table.registerClassMember(field)) {
            // Failed to register function
            reportConstAlreadyDefined(field, const)
        } else const.fieldInstance = field
    }

    private fun bindStaticFieldDeclaration(staticField: Item.StaticField) {
        staticField.typeInfo = bindType(staticField.explicitType)

        val field = ClassMember.Field.fromField(
            currentPackagePath,
            currentClassPath,
            staticField
        )

        if (!table.registerClassMember(field)) {
            // Failed to register function
            reportStaticFieldAlreadyDefined(field, staticField)
        } else staticField.fieldInstance = field
    }

    private fun bindClassDeclaration(clazz: Item.Class) {
        val previousClassPath = currentClassPath
        currentClassPath = currentClassPath.append(clazz.identifier)

        if (!table.registerClassType(
                clazz.modifiers.sum(),
                currentPackagePath.toString(),
                currentClassPath.toString()
            )
        ) {
            // Failed to register class type
            reportClassAlreadyDefined(clazz)
            return
        }

        if (clazz.classItems != null)
            for (classItem in clazz.classItems)
                bindClassItemDeclaration(classItem)

        currentClassPath = previousClassPath
    }

    private fun bindClassItemDeclaration(classItem: ClassItem) {
        when (classItem) {
            is ClassItem.Field -> bindFieldDeclaration(classItem)
        }
    }

    private fun bindFieldDeclaration(field: ClassItem.Field) {
        if (field.expression != null)
            bindExpression(field.expression!!)

        field.typeInfo = bindType(field.explicitType)

        val fieldInstance = ClassMember.Field.fromField(
            currentPackagePath,
            currentClassPath,
            field
        )

        if (!table.registerClassMember(fieldInstance)) {
            // Failed to register function
            reportFieldAlreadyDefined(fieldInstance, field)
        } else field.fieldInstance = fieldInstance
    }

    private fun bindFunctionDeclaration(function: Item.Function) {
        for (parameter in function.parameters) {
            val typeInfo = bindType(parameter.type)

            parameter.typeInfo = typeInfo
        }

        function.returnTypeInfo =
            if (function.returnType != null) bindType(function.returnType)
            else TypeInfo.Primitive.UNIT_TYPE_INFO

        val fn = ClassMember.Fn.fromFunction(
            currentPackagePath,
            currentClassPath,
            function,
            *(if (topLevel) PACKAGE_LEVEL_FUNCTION_ADDITIONAL_FLAGS else intArrayOf())
        )

        if (!table.registerClassMember(fn)) {
            // Failed to register function
            reportFunctionAlreadyDefined(fn, function)
        } else function.functionInstance = fn
    }


    fun bindSecondary() {
        bindYkFilePost(compilationUnit.ykFile!!)
    }

    private fun bindYkFilePost(ykFile: YkFile) {
        for (item in ykFile.items)
            bindItem(item)
    }

    private fun bindItem(item: Item) {
        when (item) {
            is Item.Package -> {
                val previousPackagePath = currentPackagePath
                currentPackagePath = currentPackagePath.append(item.identifier)

                if (item.items != null)
                    for (innerItem in item.items)
                        bindItem(innerItem)

                currentPackagePath = previousPackagePath
            }
            is Item.Const -> bindConst(item)
            is Item.StaticField -> bindStaticField(item)
            is Item.Class -> bindClass(item)
            is Item.Function -> bindFunction(item)
        }
    }

    private fun bindConst(const: Item.Const) {
        bindExpression(const.expression)
    }

    private fun bindStaticField(staticField: Item.StaticField) {
        bindExpression(staticField.expression)
    }

    private fun bindClass(clazz: Item.Class) {
        val previousClassPath = currentClassPath
        currentClassPath = currentClassPath.append(clazz.identifier)

        if (clazz.classItems != null)
            for (clazzItem in clazz.classItems)
                bindClassItem(clazzItem)

        currentClassPath = previousClassPath
    }

    private fun bindClassItem(classItem: ClassItem) {
        when (classItem) {
            is ClassItem.Field -> bindField(classItem)
        }
    }

    private fun bindField(field: ClassItem.Field) {
        if (field.expression != null)
            bindExpression(field.expression!!)
    }

    private fun bindFunction(function: Item.Function) {
        currentFunctionInstance = function.functionInstance

        // Initialize scope
        val functionScope = Scope(table)

        // Add function parameters as variables
        if (function.self != null) {
            functionScope.addVariable(function.self, function.functionInstance.ownerTypeInfo)
        }

        for (parameter in function.parameters)
            functionScope.addValueParameter(parameter.name, parameter.typeInfo)

        // Bind scope
        currentScope = functionScope

        when (function.body) {
            is FunctionBody.BlockExpression -> {
                for (statement in function.body.statements)
                    bindStatement(statement)
            }
            is FunctionBody.SingleExpression -> bindExpression(function.body.expression)
            null -> return
        }

        checkVariableUnused(currentScope!!)

        // Unbind scope
        currentScope = null
        currentFunctionInstance = null
    }

    private fun bindStatement(statement: Statement) {
        when (statement) {
            is Statement.VariableDeclaration -> bindVariableDeclaration(statement)
            is Statement.Return -> bindReturn(statement)
            is Statement.ExpressionStatement -> bindExpression(statement.expression)
        }
    }

    private fun bindVariableDeclaration(variableDeclaration: Statement.VariableDeclaration) {
        bindExpression(variableDeclaration.expression)

        // TODO: Check expression type can be cast into specified type

        if (variableDeclaration.ignore) {
            // Ignore variable declaration (discard lhs expression result)
            return
        }

        if (currentScope != null) {
            val variable = currentScope!!.addVariable(variableDeclaration.name, variableDeclaration.expression.finalType)

            if (variable != null) {
                variableDeclaration.variableInstance = variable
            } else {
                val originalVariable = currentScope!!.getVariable(variableDeclaration.name.literal)!!

                reportVariableAlreadyDeclared(originalVariable, variableDeclaration.name.span)
            }
        }
    }

    private fun bindReturn(`return`: Statement.Return) {
        bindExpression(`return`.expression)

        // TODO: Check expression type can be cast into function's return type
    }

    private fun bindExpression(expression: Expression) {
        when (expression) {
            is Expression.BinaryExpression -> bindBinaryExpression(expression)
            is Expression.Identifier -> bindIdentifier(expression)
            is Expression.NumberLiteral -> bindNumberLiteral(expression)
            is Expression.Empty -> {}
            Expression.Undefined -> TODO("UNREACHABLE")
        }
    }

    private fun bindBinaryExpression(binaryExpression: Expression.BinaryExpression) {
        bindExpression(binaryExpression.leftExpression)
        bindExpression(binaryExpression.rightExpression)

        when (binaryExpression.operation) {
            Expression.BinaryExpression.BinaryOperation.Addition,
            Expression.BinaryExpression.BinaryOperation.Subtraction,
            Expression.BinaryExpression.BinaryOperation.Multiplication,
            Expression.BinaryExpression.BinaryOperation.Division,
            Expression.BinaryExpression.BinaryOperation.Modulo-> {
                val leftType = binaryExpression.leftExpression.finalType
                val rightType = binaryExpression.rightExpression.finalType
                val promotedType = leftType promote rightType

                if (promotedType == null) {
                    val coloredOperator = colorize(
                        binaryExpression.operator.joinToString(transform = Token::literal),
                        compilationUnit,
                        Attribute.CYAN_TEXT()
                    )
                    val coloredLeftTypeLiteral = colorize(leftType.toString(), compilationUnit, Attribute.CYAN_TEXT())
                    val coloredRightTypeLiteral = colorize(rightType.toString(), compilationUnit, Attribute.CYAN_TEXT())
                    val operatorSpan = binaryExpression.operator.map(Token::span).reduce(Span::expand)

                    compilationUnit.reportBuilder
                        .error(
                            SpanHelper.expandView(binaryExpression.span, compilationUnit.maxLineCount),
                            "Unable to apply `$coloredOperator` on `$coloredLeftTypeLiteral` and `$coloredRightTypeLiteral`"
                        )
                        .label(
                            binaryExpression.leftExpression.span,
                            "Left expression has type `$coloredLeftTypeLiteral`"
                        )
                        .color(Attribute.CYAN_TEXT())
                        .build()
                        .label(operatorSpan, "Inapplicable operator `$coloredOperator`")
                        .color(Attribute.RED_TEXT())
                        .build()
                        .label(
                            binaryExpression.rightExpression.span,
                            "Right expression has type `$coloredRightTypeLiteral`"
                        )
                        .color(Attribute.CYAN_TEXT())
                        .build().build()

                    return
                }

                binaryExpression.originalType = promotedType
                binaryExpression.finalType = promotedType
            }
            Expression.BinaryExpression.BinaryOperation.UnsignedRightShift,
            Expression.BinaryExpression.BinaryOperation.RightShift,
            Expression.BinaryExpression.BinaryOperation.LeftShift -> {
                val leftType = binaryExpression.leftExpression.finalType.asPrimitive()
                val rightType = binaryExpression.rightExpression.finalType.asPrimitive()

                if (leftType == null || !PrimitiveType.isIntegerType(leftType.type)) {
                    val coloredOperator = colorize(
                        binaryExpression.operator.joinToString(separator = "", transform = Token::literal),
                        compilationUnit,
                        Attribute.CYAN_TEXT()
                    )
                    val coloredLeftTypeLiteral = colorize(
                        binaryExpression.leftExpression.finalType.toString(),
                        compilationUnit,
                        Attribute.CYAN_TEXT()
                    )

                    compilationUnit.reportBuilder
                        .error(
                            SpanHelper.expandView(binaryExpression.span, compilationUnit.maxLineCount),
                            "Unable to shift type `$coloredLeftTypeLiteral` with `$coloredOperator`"
                        )
                        .label(
                            binaryExpression.leftExpression.span,
                            "Expression has non-integer type `$coloredLeftTypeLiteral`"
                        )
                        .color(Attribute.CYAN_TEXT())
                        .build()
                        .label(
                            binaryExpression.operator.map(Token::span).reduce(Span::expand),
                            "Inapplicable operator `$coloredOperator`"
                        )
                        .color(Attribute.RED_TEXT())
                        .build().build()

                    return
                }

                if (rightType == null || rightType.type != PrimitiveType.I32) {
                    val coloredOperator = colorize(
                        binaryExpression.operator.joinToString(separator = "", transform = Token::literal),
                        compilationUnit,
                        Attribute.CYAN_TEXT()
                    )
                    val coloredRightTypeLiteral = colorize(
                        binaryExpression.rightExpression.finalType.toString(),
                        compilationUnit,
                        Attribute.CYAN_TEXT()
                    )

                    compilationUnit.reportBuilder
                        .error(
                            SpanHelper.expandView(binaryExpression.span, compilationUnit.maxLineCount),
                            "Unable to shift type `$coloredRightTypeLiteral` with `$coloredOperator`"
                        )
                        .label(
                            binaryExpression.rightExpression.span,
                            "Expression has non-i32 type `$coloredRightTypeLiteral`"
                        )
                        .color(Attribute.CYAN_TEXT())
                        .build()
                        .label(
                            binaryExpression.operator.map(Token::span).reduce(Span::expand),
                            "Inapplicable operator `$coloredOperator`"
                        )
                        .color(Attribute.RED_TEXT())
                        .build().build()

                    return
                }

                binaryExpression.originalType = leftType
                binaryExpression.finalType = leftType
            }
        }
    }

    private fun bindIdentifier(identifier: Expression.Identifier) {
        val resolver = SymbolResolver(currentScope!!)
        val resolvedSymbol = resolver.resolveIdentifier(currentPackagePath, currentClassPath, identifier.identifier.literal)

        if (resolvedSymbol != null) {
            identifier.symbolInstance = resolvedSymbol
            identifier.originalType = resolvedSymbol.typeInfo
            identifier.finalType = resolvedSymbol.typeInfo

            if (resolvedSymbol is Variable) {
                resolvedSymbol.markUsed()
                resolvedSymbol.reference()
            }
        } else {
            TODO("Report error")
        }
    }

    private fun bindNumberLiteral(numberLiteral: Expression.NumberLiteral) {
        var value = .0

        numberLiteral.integerPart?.let {
            value += it.literal.toInt()
        }

        numberLiteral.floatPart?.let {
            value += it.literal.toInt()
        }

        numberLiteral.value = value
        numberLiteral.originalType =
            if (numberLiteral.dot == null && numberLiteral.floatPart == null) TypeInfo.Primitive(PrimitiveType.I32)
            else TypeInfo.Primitive(PrimitiveType.F64)
        numberLiteral.finalType = when {
            numberLiteral.specifiedType != null -> bindType(numberLiteral.specifiedType)
            numberLiteral.dot == null && numberLiteral.floatPart == null -> TypeInfo.Primitive(PrimitiveType.I32)
            else -> TypeInfo.Primitive(PrimitiveType.F64)
        }

        numberLiteral.specifiedTypeInfo = numberLiteral.specifiedType?.let {
            bindType(it) as TypeInfo.Primitive
        }
    }

    private fun reportConstAlreadyDefined(field: ClassMember.Field, const: Item.Const) {
        val span = const.span
        val coloredConstLiteral = colorize(field.constToString(), compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(span, compilationUnit.maxLineCount),
                "Constant $coloredConstLiteral is already defined at `${field.qualifiedOwnerPath}`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportStaticFieldAlreadyDefined(field: ClassMember.Field, staticField: Item.StaticField) {
        val span = staticField.span
        val coloredStaticFieldLiteral = colorize(field.staticFieldToString(), compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(span, compilationUnit.maxLineCount),
                "Static field $coloredStaticFieldLiteral is already defined at `${field.qualifiedOwnerPath}`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportClassAlreadyDefined(clazz: Item.Class) {
        val span = clazz.span
        val coloredPackageLiteral = colorize(currentPackagePath.toString(), compilationUnit, Attribute.CYAN_TEXT())
        val coloredClassLiteral = colorize(currentClassPath.toString(), compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(span, compilationUnit.maxLineCount),
                "Class $coloredClassLiteral is already defined at `$coloredPackageLiteral`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportFieldAlreadyDefined(fieldInstance: ClassMember.Field, field: ClassItem.Field) {
        val span = field.span
        val coloredStaticFieldLiteral =
            colorize(fieldInstance.staticFieldToString(), compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(span, compilationUnit.maxLineCount),
                "Static field $coloredStaticFieldLiteral is already defined at `${fieldInstance.qualifiedOwnerPath}`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun reportFunctionAlreadyDefined(fn: ClassMember.Fn, function: Item.Function) {
        val span = function.fn.span.expand(function.span)
        val coloredFnLiteral = colorize(fn.toString(), compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(span, compilationUnit.maxLineCount),
                "Function $coloredFnLiteral is already defined at `${fn.qualifiedOwnerPath}`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun checkVariableUnused(scope: Scope) {
        for (variable in scope.variables) {
            if (!variable.isUsed) {
                reportVariableUnused(variable)
            }
        }
    }

    private fun reportVariableUnused(variable: Variable) {
        val variableType = if (variable is Variable.ValueParameter) "Value-parameter" else "Variable"
        val coloredVariableName= colorize(variable.name, compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .warning(
                SpanHelper.expandView(variable.nameToken.span, compilationUnit.maxLineCount),
                "$variableType `$coloredVariableName` is never used"
            )
            .label(variable.nameToken.span, "Unused ${variableType.lowercase()} here")
            .color(Attribute.YELLOW_TEXT())
            .build().build()
    }

    private fun reportVariableAlreadyDeclared(originalVariable: Variable, duplicatedSpan: Span) {
        val originalSpan = originalVariable.nameToken.span
        val coloredVariableName = colorize(originalVariable.name, compilationUnit, Attribute.CYAN_TEXT())

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(originalSpan.expand(duplicatedSpan), compilationUnit.maxLineCount),
                "Variable `$coloredVariableName` is already declared"
            )
            .label(originalSpan, "Variable `$coloredVariableName` was declared here first")
            .color(Attribute.CYAN_TEXT())
            .build()
            .label(duplicatedSpan, "Variable redeclared here")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun bindType(type: Type): TypeInfo {
        val typeInfo = table.findType(type)

        return if (typeInfo == null) {
            // Unknown type
            val span = type.span
            val typeName = type.standardizeType()
            val colorizedTypeName = colorize(typeName, compilationUnit, Attribute.RED_TEXT())

            compilationUnit.reportBuilder
                .error(SpanHelper.expandView(span, compilationUnit.maxLineCount), "Unknown type $colorizedTypeName")
                .label(span, "Unresolvable type here")
                .color(Attribute.RED_TEXT())
                .build().build()

            TypeInfo.Primitive.UNIT_TYPE_INFO
        } else typeInfo
    }
}