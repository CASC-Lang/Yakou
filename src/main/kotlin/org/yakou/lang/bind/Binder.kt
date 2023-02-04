package org.yakou.lang.bind

import org.objectweb.asm.Opcodes
import org.yakou.lang.ast.Argument
import org.yakou.lang.ast.Block
import org.yakou.lang.ast.Class
import org.yakou.lang.ast.ClassItem
import org.yakou.lang.ast.Const
import org.yakou.lang.ast.Expression
import org.yakou.lang.ast.ExpressionStatement
import org.yakou.lang.ast.Field
import org.yakou.lang.ast.For
import org.yakou.lang.ast.Func
import org.yakou.lang.ast.FunctionBody
import org.yakou.lang.ast.GenericDeclarationParameters
import org.yakou.lang.ast.Impl
import org.yakou.lang.ast.ImplItem
import org.yakou.lang.ast.Item
import org.yakou.lang.ast.Keyword
import org.yakou.lang.ast.Package
import org.yakou.lang.ast.Path
import org.yakou.lang.ast.PrimaryConstructor
import org.yakou.lang.ast.Return
import org.yakou.lang.ast.Statement
import org.yakou.lang.ast.StaticField
import org.yakou.lang.ast.Token
import org.yakou.lang.ast.Type
import org.yakou.lang.ast.VariableDeclaration
import org.yakou.lang.ast.YkFile
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.compilation.UnitReporter

class Binder(private val compilationUnit: CompilationUnit) : BinderReporter, UnitReporter by compilationUnit {
    companion object {
        val PACKAGE_LEVEL_FUNCTION_ADDITIONAL_FLAGS: IntArray = intArrayOf(Opcodes.ACC_STATIC)
    }

    private val table = compilationUnit.session.table

    private var currentPackagePath: Path.SimplePath = Path.SimplePath(listOf())
    private var currentClassPath: Path.SimplePath = Path.SimplePath(listOf(), "$")
    private var currentFunctionInstance: ClassMember.Fn? = null
    private var currentScope: Scope = Scope(table)
    private var isStaticInnerScope: Boolean = false
    private var topLevel: Boolean = true

    fun bind() {
        bindYkFile(compilationUnit.ykFile!!)
    }

    private fun bindYkFile(ykFile: YkFile) {
        for (item in ykFile.items) {
            bindItemDeclaration(item)
        }
    }

    private fun bindItemDeclaration(item: Item) {
        when (item) {
            is Package -> bindPackageDeclaration(item)
            is Const -> bindConstDeclaration(item)
            is StaticField -> bindStaticFieldDeclaration(item)
            is Class -> bindClassDeclaration(item)
            is Func -> bindFunctionDeclaration(item)
            is Impl -> bindImplDeclaration(item)
        }
    }

    private fun bindPackageDeclaration(packageItem: Package) {
        val previousPackagePath = currentPackagePath
        currentPackagePath = currentPackagePath.append(packageItem.identifier)

        table.registerPackageClass(currentPackagePath.toString())

        if (packageItem.items != null) {
            for (innerItem in packageItem.items) {
                bindItemDeclaration(innerItem)
            }
        }

        currentPackagePath = previousPackagePath
    }

    private fun bindConstDeclaration(const: Const) {
        const.typeInfo = bindType(const.explicitType)

        val field = ClassMember.Field.fromConst(
            table,
            currentPackagePath,
            currentClassPath,
            const,
            Opcodes.ACC_STATIC
        )

        if (!table.registerClassMember(field)) {
            // Failed to register function
            reportConstAlreadyDefined(field, const)
        } else {
            const.fieldInstance = field
        }
    }

    private fun bindStaticFieldDeclaration(staticField: StaticField) {
        staticField.typeInfo = bindType(staticField.explicitType)

        val field = ClassMember.Field.fromField(
            table,
            currentPackagePath,
            currentClassPath,
            staticField
        )

        if (!table.registerClassMember(field)) {
            // Failed to register function
            reportStaticFieldAlreadyDefined(field, staticField)
        } else {
            staticField.fieldInstance = field
        }
    }

    private fun bindClassDeclaration(clazz: Class) {
        val classType = table.registerClassType(
            clazz.modifiers.sum(),
            currentPackagePath.toString(),
            currentClassPath.append(clazz.identifier).toString(),
            clazz.genericDeclarationParameters?.parameters ?: listOf()
        )

        bindScope(clazz.identifier, classType, staticInnerScope = true) {
            if (clazz.genericDeclarationParameters != null) {
                for (parameter in clazz.genericDeclarationParameters.parameters) {
                    bindGenericParameterDeclaration(parameter)
                }
            }

            if (classType == null) {
                // Failed to register class type
                reportClassAlreadyDefined(currentPackagePath, currentClassPath, clazz)
                return@bindScope
            } else {
                clazz.classTypeInfo = classType
            }

            if (clazz.primaryConstructor != null) {
                bindPrimaryConstructorDeclaration(clazz.primaryConstructor)
            } else {
                // Generate an empty constructor
                table.registerClassMember(
                    ClassMember.Constructor(
                        Opcodes.ACC_PUBLIC,
                        currentPackagePath.toString(),
                        currentClassPath.toString(),
                        listOf()
                    )
                )
            }

            if (clazz.classItems != null) {
                for (classItem in clazz.classItems) {
                    bindClassItemDeclaration(classItem)
                }
            }
        }
    }

    private fun bindGenericParameterDeclaration(genericDeclarationParameter: GenericDeclarationParameters.GenericDeclarationParameter) {
        currentScope.addTypeVariable(genericDeclarationParameter.genericConstraint)
    }

    private fun bindPrimaryConstructorDeclaration(primaryConstructor: PrimaryConstructor) {
        for (parameter in primaryConstructor.parameters) {
            val typeInfo = bindType(parameter.type)

            parameter.typeInfo = typeInfo
        }

        val constructor = ClassMember.Constructor.fromPrimaryConstructor(
            table,
            currentPackagePath,
            currentClassPath,
            primaryConstructor
        )

        if (!table.registerClassMember(constructor)) {
            // Failed to register constructor
            // In theory this would never have triggered since primary constructor always registered first and auxiliary
            // constructor in `impl` block will register right after this.
            reportConstructorAlreadyDefined(constructor, primaryConstructor.span)
            return
        } else {
            primaryConstructor.constructorInstance = constructor
        }

        // Register field parameters
        for (parameter in primaryConstructor.parameters) {
            if (!parameter.modifiers.isEmpty()) {
                val fieldInstance = ClassMember.Field.fromConstructorParameter(
                    table,
                    currentPackagePath,
                    currentClassPath,
                    parameter
                )

                if (!table.registerClassMember(fieldInstance)) {
                    // Failed to register function
                    reportFieldAlreadyDefined(fieldInstance, parameter.span)
                } else {
                    parameter.fieldInstance = fieldInstance
                }
            }
        }
    }

    private fun bindClassItemDeclaration(classItem: ClassItem) {
        when (classItem) {
            is Field -> bindFieldDeclaration(classItem)
        }
    }

    private fun bindFieldDeclaration(field: Field) {
        field.typeInfo = bindType(field.explicitType)

        val fieldInstance = ClassMember.Field.fromField(
            currentPackagePath,
            currentClassPath,
            field
        )

        if (!table.registerClassMember(fieldInstance)) {
            // Failed to register function
            reportFieldAlreadyDefined(fieldInstance, field.span)
        } else {
            field.fieldInstance = fieldInstance
        }
    }

    private fun bindFunctionDeclaration(function: Func) {
        if (function.genericDeclarationParameters != null) {
            for (genericDeclarationParameter in function.genericDeclarationParameters.parameters) {
                bindGenericParameterDeclaration(genericDeclarationParameter)
            }
        }

        for (parameter in function.parameters) {
            val typeInfo = bindType(parameter.type)

            parameter.typeInfo = typeInfo
        }

        function.returnTypeInfo =
            if (function.returnType != null) {
                bindType(function.returnType)
            } else {
                TypeInfo.Primitive.UNIT_TYPE_INFO
            }

        val fn = ClassMember.Fn.fromFunction(
            table,
            currentPackagePath,
            currentClassPath,
            function,
            *(if (topLevel) PACKAGE_LEVEL_FUNCTION_ADDITIONAL_FLAGS else intArrayOf())
        )

        if (!table.registerClassMember(fn)) {
            // Failed to register function
            reportFunctionAlreadyDefined(fn, function)
        } else {
            function.functionInstance = fn
        }
    }

    private fun bindImplDeclaration(impl: Impl) {
        val classType = currentPackagePath.append(currentClassPath.append(impl.identifier)).toString()
        val classTypeInfo = table.findType(classType)

        if (classTypeInfo == null) {
            reportUnresolvedType(classType, impl.identifier.span)
        } else {
            bindScope(impl.identifier, classTypeInfo, staticInnerScope = true) {
                if (impl.genericDeclarationParameters != null) {
                    for (genericDeclarationParameter in impl.genericDeclarationParameters.parameters) {
                        bindGenericParameterDeclaration(genericDeclarationParameter)
                    }
                }

                if (impl.genericParameters != null) {
                    for (genericParameter in impl.genericParameters.genericParameters) {
                        bindType(genericParameter)
                    }
                }

                if (impl.implItems != null) {
                    for (item in impl.implItems) {
                        bindImplItemDeclaration(item)
                    }
                }

                impl.ownerClass = classTypeInfo
            }
        }
    }

    private fun bindImplItemDeclaration(item: ImplItem) {
        when (item) {
            is Class -> bindClassDeclaration(item)
            is Func -> bindFunctionDeclaration(item)
            is Impl -> bindImplDeclaration(item)
            is StaticField -> bindStaticFieldDeclaration(item)
            is Const -> bindConstDeclaration(item)
        }
    }

    fun bindSecondary() {
        bindYkFilePost(compilationUnit.ykFile!!)
    }

    private fun bindYkFilePost(ykFile: YkFile) {
        for (item in ykFile.items) {
            bindItem(item)
        }
    }

    private fun bindItem(item: Item) {
        when (item) {
            is Package -> bindPackage(item)
            is Const -> bindConst(item)
            is StaticField -> bindStaticField(item)
            is Class -> bindClass(item)
            is Func -> bindFunction(item, true)
            is Impl -> bindImpl(item)
        }
    }

    private fun bindPackage(packageItem: Package) {
        val previousPackagePath = currentPackagePath
        currentPackagePath = currentPackagePath.append(packageItem.identifier)

        if (packageItem.items != null) {
            for (innerItem in packageItem.items) {
                bindItem(innerItem)
            }
        }

        currentPackagePath = previousPackagePath
    }

    private fun bindConst(const: Const) {
        bindExpression(const.expression)
    }

    private fun bindStaticField(staticField: StaticField) {
        bindExpression(staticField.expression)
    }

    private fun bindClass(clazz: Class) {
        bindScope(clazz.identifier, clazz.classTypeInfo) {
            if (clazz.genericDeclarationParameters != null) {
                for (parameter in clazz.genericDeclarationParameters.parameters) {
                    bindGenericDeclarationParameter(parameter)
                }
            }

            if (clazz.primaryConstructor != null) {
                bindPrimaryConstructor(clazz.primaryConstructor)
            }

            if (clazz.superClassConstructorCall != null) {
                val superClassType = bindSuperClassConstructorCall(clazz.superClassConstructorCall)

                if (superClassType != null) {
                    table.registerSuperClassType(
                        currentPackagePath.toString(),
                        currentClassPath.toString(),
                        superClassType
                    )
                }
            }

            if (clazz.classItems != null) {
                for (clazzItem in clazz.classItems)
                    bindClassItem(clazzItem)
            }
        }
    }

    private fun bindGenericDeclarationParameter(genericDeclarationParameter: GenericDeclarationParameters.GenericDeclarationParameter) {
        if (genericDeclarationParameter is GenericDeclarationParameters.ConstraintGenericDeclarationParameter) {
            currentScope.addTypeVariable(genericDeclarationParameter.genericConstraint)

            for (bound in genericDeclarationParameter.constraints) {
                val boundType = bindType(bound)

                if (boundType !is TypeInfo.TypeInfoVariable) {
                    reportUnableToSetGenericBound(genericDeclarationParameter)
                } else {
                    genericDeclarationParameter.genericConstraint.bounds += boundType

                    currentScope.setConstraint(
                        genericDeclarationParameter.genericConstraint.genericParameterName,
                        boundType
                    )
                }
            }
        }
    }

    private fun bindPrimaryConstructor(primaryConstructor: PrimaryConstructor) {
        if (primaryConstructor.self != null) {
            currentScope.addVariable(
                primaryConstructor.self,
                primaryConstructor.self,
                primaryConstructor.constructorInstance.ownerTypeInfo
            )
        }

        for (parameter in primaryConstructor.parameters)
            currentScope.addValueParameter(
                parameter.name,
                parameter.typeInfo,
                selfSkipped = primaryConstructor.self == null
            )
    }

    private fun bindSuperClassConstructorCall(superClassConstructorCall: Class.SuperClassConstructorCall): TypeInfo.Class? {
        for (argument in superClassConstructorCall.arguments)
            bindExpression(argument)

        return when (
            val superType = SymbolResolver(currentScope).resolveType(
                currentPackagePath,
                currentClassPath,
                superClassConstructorCall.superClassType
            )
        ) {
            is TypeInfo.Array, is TypeInfo.Primitive -> {
                reportIllegalNonClassInheritance(superClassConstructorCall)
                null
            }

            is TypeInfo.GenericConstraint -> {
                reportIllegalGenericInheritance(superClassConstructorCall)
                null
            }

            is TypeInfo.PackageClass -> {
                reportIllegalPackageInheritance(superClassConstructorCall)
                null
            }

            is TypeInfo.Class -> {
                val superConstructor =
                    table.findConstructor(superType.standardTypePath, superClassConstructorCall.argumentTypeInfos)

                if (superConstructor == null) {
                    reportUnresolvedSymbol(
                        "self(${superClassConstructorCall.arguments.joinToString { it.finalType.toString() }}) -> ${superClassConstructorCall.superClassType.standardizeType()}",
                        superClassConstructorCall.span
                    )
                } else {
                    superClassConstructorCall.constructorInstance = superConstructor
                }

                superClassConstructorCall.superClassTypeInfo = superType

                superType
            }

            null -> {
                reportUnresolvedType(
                    superClassConstructorCall.superClassType.standardizeType(),
                    superClassConstructorCall.superClassType.span
                )

                null
            }
        }
    }

    private fun bindClassItem(classItem: ClassItem) {
        when (classItem) {
            is Field -> bindField(classItem)
        }
    }

    private fun bindField(field: Field) {
        if (field.expression != null) {
            bindExpression(field.expression!!)
        }
    }

    private fun bindFunction(function: Func, static: Boolean) {
        currentFunctionInstance = function.functionInstance

        // Initialize scope
        val functionScope = Scope(currentScope, isStaticScope = static)

        // Add function parameters as variables
        if (function.self != null) {
            functionScope.addVariable(function.self, function.self, function.functionInstance.ownerTypeInfo)
        }

        for (parameter in function.parameters)
            functionScope.addValueParameter(parameter.name, parameter.typeInfo)

        // Bind scope
        val previousScope = currentScope
        currentScope = functionScope

        if (function.genericDeclarationParameters != null) {
            for (parameter in function.genericDeclarationParameters.parameters) {
                bindGenericDeclarationParameter(parameter)
            }
        }

        when (function.body) {
            is FunctionBody.BlockExpression -> {
                for (statement in function.body.statements)
                    bindStatement(statement)
            }

            is FunctionBody.SingleExpression -> bindExpression(function.body.expression)
            null -> return
        }

        checkVariableUnused(currentScope)

        // Unbind scope
        currentScope = previousScope
        currentFunctionInstance = null
    }

    private fun bindImpl(impl: Impl) {
        bindScope(impl.identifier, impl.ownerClass, staticInnerScope = true) {
            if (impl.genericDeclarationParameters != null) {
                for (parameter in impl.genericDeclarationParameters.parameters) {
                    bindGenericDeclarationParameter(parameter)
                }
            }

            if (impl.implItems != null) {
                for (item in impl.implItems) {
                    bindImplItem(item)
                }
            }
        }
    }

    private fun bindImplItem(item: ImplItem) {
        when (item) {
            is Class -> bindClass(item)
            is Func -> bindFunction(item, item.self == null)
            is Impl -> bindImpl(item)
            is StaticField -> bindStaticField(item)
            is Const -> bindConst(item)
        }
    }

    private fun bindStatement(statement: Statement) {
        when (statement) {
            is VariableDeclaration -> bindVariableDeclaration(statement)
            is For -> bindFor(statement)
            is Block -> bindBlock(statement)
            is Return -> bindReturn(statement)
            is ExpressionStatement -> bindExpression(statement.expression)
        }
    }

    private fun bindVariableDeclaration(variableDeclaration: VariableDeclaration) {
        bindExpression(variableDeclaration.expression)

        // TODO: Check expression type can be cast into specified type

        if (variableDeclaration.ignore) {
            // Ignore variable declaration (discard lhs expression result)
            return
        }

        val variable = currentScope.addVariable(
            variableDeclaration.mut,
            variableDeclaration.name,
            variableDeclaration.expression.finalType
        )

        if (variable != null) {
            variableDeclaration.variableInstance = variable
        } else {
            val originalVariable = currentScope.getVariable(variableDeclaration.name.literal)!!

            reportVariableAlreadyDeclared(originalVariable, variableDeclaration.name.span)
        }
    }

    private fun bindFor(`for`: For) {
        bindExpression(`for`.conditionExpression)
        bindBlock(`for`.block)
    }

    private fun bindBlock(block: Block) {
        val outerScope = currentScope
        currentScope = Scope(outerScope)

        for (statement in block.statements) {
            bindStatement(statement)
        }

        currentScope = outerScope
    }

    private fun bindReturn(`return`: Return) {
        bindExpression(`return`.expression)

        // TODO: Check expression type can be cast into function's return type
    }

    private fun bindExpression(expression: Expression) {
        when (expression) {
            is Expression.BinaryExpression -> bindBinaryExpression(expression)
            is Expression.As -> bindAs(expression)
            is Expression.Identifier -> bindIdentifier(expression)
            is Expression.Parenthesized -> bindParenthesized(expression)
            is Expression.ConstructorCall -> bindConstructorCall(expression)
            is Expression.BoolLiteral -> bindBoolLiteral(expression)
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
            Expression.BinaryExpression.BinaryOperation.Modulo -> {
                val leftType = binaryExpression.leftExpression.finalType
                val rightType = binaryExpression.rightExpression.finalType
                val promotedType = leftType promote rightType

                if (promotedType == null) {
                    reportInapplicableOperator(binaryExpression, leftType, rightType, null)

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

                if (leftType == null || !leftType.type.isIntegerType()) {
                    reportNonIntegerShiftOperation(binaryExpression)

                    return
                }

                if (rightType == null || rightType.type != PrimitiveType.I32) {
                    reportNonI32ShiftOperation(binaryExpression)

                    return
                }

                binaryExpression.originalType = leftType
                binaryExpression.finalType = leftType
            }

            Expression.BinaryExpression.BinaryOperation.LogicalAnd,
            Expression.BinaryExpression.BinaryOperation.LogicalOr -> {
                val leftType = binaryExpression.leftExpression.finalType
                val rightType = binaryExpression.rightExpression.finalType

                if (leftType.asPrimitive()?.type != PrimitiveType.Bool ||
                    rightType.asPrimitive()?.type != PrimitiveType.Bool
                ) {
                    reportInapplicableOperator(binaryExpression, leftType, rightType)

                    return
                }

                binaryExpression.originalType = TypeInfo.Primitive.BOOL_TYPE_INFO
                binaryExpression.finalType = TypeInfo.Primitive.BOOL_TYPE_INFO
            }

            Expression.BinaryExpression.BinaryOperation.Equal,
            Expression.BinaryExpression.BinaryOperation.NotEqual,
            Expression.BinaryExpression.BinaryOperation.ExactEqual,
            Expression.BinaryExpression.BinaryOperation.ExactNotEqual -> {
                binaryExpression.originalType = TypeInfo.Primitive.BOOL_TYPE_INFO
                binaryExpression.finalType = TypeInfo.Primitive.BOOL_TYPE_INFO
            }

            Expression.BinaryExpression.BinaryOperation.Greater,
            Expression.BinaryExpression.BinaryOperation.GreaterEqual,
            Expression.BinaryExpression.BinaryOperation.Lesser,
            Expression.BinaryExpression.BinaryOperation.LesserEqual -> {
                val leftType = binaryExpression.leftExpression.finalType
                val rightType = binaryExpression.rightExpression.finalType
                val promotedType = leftType promote rightType

                if (promotedType == null) {
                    reportInapplicableOperator(binaryExpression, leftType, rightType, null)

                    return
                }

                binaryExpression.originalType = TypeInfo.Primitive.BOOL_TYPE_INFO
                binaryExpression.finalType = TypeInfo.Primitive.BOOL_TYPE_INFO
            }
        }
    }

    private fun bindIdentifier(identifier: Expression.Identifier) {
        val resolver = SymbolResolver(currentScope)
        val resolvedSymbol =
            resolver.resolveIdentifier(currentPackagePath, currentClassPath, identifier.identifier.literal)

        if (resolvedSymbol != null) {
            identifier.symbolInstance = resolvedSymbol
            identifier.originalType = resolvedSymbol.typeInfo
            identifier.finalType = resolvedSymbol.typeInfo

            if (resolvedSymbol is Variable) {
                resolvedSymbol.markUsed()
                resolvedSymbol.reference()
            }
        } else {
            reportUnresolvedIdentifier(identifier.identifier)
        }
    }

    private fun bindAs(`as`: Expression.As) {
        bindExpression(`as`.expression)

        `as`.originalType = `as`.expression.finalType
        `as`.finalType = bindType(`as`.type)
    }

    private fun bindParenthesized(parenthesized: Expression.Parenthesized) {
        bindExpression(parenthesized.expression)

        parenthesized.originalType = parenthesized.expression.finalType
        parenthesized.finalType = parenthesized.originalType
    }

    private fun bindConstructorCall(constructorCall: Expression.ConstructorCall) {
        constructorCall.referenceClassTypeInfo = bindType(constructorCall.classType)

        for (expression in constructorCall.arguments) {
            bindExpression(expression)
        }
        
        val constructor = table.findConstructor(constructorCall.referenceClassTypeInfo.toString(), constructorCall.arguments.map(Argument::finalType))
        
        if (constructor == null) {
            reportUnresolvableConstructorCall(constructorCall.span)
        } else {
            constructorCall.referenceConstructor = constructor
            
            constructorCall.originalType = constructorCall.referenceClassTypeInfo
            constructorCall.finalType = constructorCall.referenceClassTypeInfo
        }
    }

    private fun bindBoolLiteral(boolLiteral: Expression.BoolLiteral) {
        boolLiteral.value = boolLiteral.boolKeyword?.isKeyword(Keyword.TRUE) ?: false
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

        if (numberLiteral.specifiedType != null) {
            val specifiedType = bindType(numberLiteral.specifiedType) as TypeInfo.Primitive

            numberLiteral.specifiedTypeInfo = specifiedType
            numberLiteral.originalType = specifiedType
            numberLiteral.finalType = specifiedType
        } else {
            numberLiteral.originalType =
                if (numberLiteral.dot == null && numberLiteral.floatPart == null) {
                    TypeInfo.Primitive(PrimitiveType.I32)
                } else {
                    TypeInfo.Primitive(PrimitiveType.F64)
                }
            numberLiteral.finalType = when {
                numberLiteral.dot == null && numberLiteral.floatPart == null -> TypeInfo.Primitive(PrimitiveType.I32)
                else -> TypeInfo.Primitive(PrimitiveType.F64)
            }
        }
    }

    private fun bindType(type: Type): TypeInfo {
        val resolver = TypeResolver(currentScope, table)
        val typeInfo = resolver.resolveType(type)

        return if (typeInfo == null) {
            // Unknown type
            val span = type.span
            val typeName = type.standardizeType()

            reportUnresolvedType(typeName, span)

            TypeInfo.Primitive.UNIT_TYPE_INFO
        } else {
            typeInfo
        }
    }

    private inline fun bindScope(
        classPath: Token,
        ownerClassTypeInfo: TypeInfo.Class? = null,
        staticInnerScope: Boolean = false,
        crossinline functor: () -> Unit
    ) {
        val previousClassPath = currentClassPath
        val previousScope = currentScope
        val previousStaticInnerScopeFlag = isStaticInnerScope

        currentClassPath = currentClassPath.append(classPath)
        currentScope = Scope(currentScope, ownerClassTypeInfo, isStaticInnerScope = isStaticInnerScope)
        isStaticInnerScope = staticInnerScope

        functor()

        currentClassPath = previousClassPath
        currentScope = previousScope
        isStaticInnerScope = previousStaticInnerScopeFlag
    }
}
