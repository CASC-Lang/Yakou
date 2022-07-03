package org.yakou.lang.bind

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.objectweb.asm.Opcodes
import org.yakou.lang.ast.*
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.util.SpanHelper

class Binder(private val compilationUnit: CompilationUnit) {
    companion object {
        val PACKAGE_LEVEL_FUNCTION_ADDITIONAL_FLAGS: IntArray = intArrayOf(Opcodes.ACC_STATIC)
    }

    private val table = compilationUnit.session.table

    private var currentPackagePath: Path.SimplePath = Path.SimplePath(listOf())
    private var currentClassPath: Path.SimplePath = Path.SimplePath(listOf())
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
                if (item.items != null)
                    for (innerItem in item.items)
                        bindItem(innerItem)
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
        if (clazz.classItems != null)
            for (clazzItem in clazz.classItems)
                bindClassItem(clazzItem)
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
        when (function.body) {
            is FunctionBody.BlockExpression -> {
                for (statement in function.body.statements)
                    bindStatement(statement)
            }
            is FunctionBody.SingleExpression -> bindExpression(function.body.expression)
            null -> return
        }
    }

    private fun bindStatement(statement: Statement) {

    }

    private fun bindExpression(expression: Expression) {
        when (expression) {
            is Expression.NumberLiteral -> bindNumberLiteral(expression)
            is Expression.BinaryExpression -> bindBinaryExpression(expression)
            Expression.Undefined -> TODO("UNREACHABLE")
        }
    }

    private fun bindBinaryExpression(binaryExpression: Expression.BinaryExpression) {
        bindExpression(binaryExpression.leftExpression)
        bindExpression(binaryExpression.rightExpression)

        when (binaryExpression.operator.type) {
            TokenType.Plus, TokenType.Minus, TokenType.Star, TokenType.Slash -> {
                val leftType = binaryExpression.leftExpression.finalType
                val rightType = binaryExpression.rightExpression.finalType
                val promotedType = leftType promote rightType

                if (promotedType == null) {
                    val coloredOperator =
                        if (compilationUnit.preference.enableColor) Ansi.colorize(
                            binaryExpression.operator.literal,
                            Attribute.CYAN_TEXT()
                        )
                        else binaryExpression.operator.literal
                    val coloredLeftTypeLiteral =
                        if (compilationUnit.preference.enableColor) Ansi.colorize(
                            leftType.toString(),
                            Attribute.CYAN_TEXT()
                        )
                        else leftType.toString()
                    val coloredRightTypeLiteral =
                        if (compilationUnit.preference.enableColor) Ansi.colorize(
                            rightType.toString(),
                            Attribute.CYAN_TEXT()
                        )
                        else rightType.toString()

                    compilationUnit.reportBuilder
                        .error(
                            SpanHelper.expandView(binaryExpression.span, compilationUnit.maxLineCount),
                            "Unable to apply `$coloredOperator` on `$coloredLeftTypeLiteral` and `$coloredRightTypeLiteral`"
                        )
                        .label(binaryExpression.leftExpression.span, "Left expression has type `$coloredLeftTypeLiteral`")
                        .color(Attribute.CYAN_TEXT())
                        .build()
                        .label(binaryExpression.operator.span, "Inapplicable operator `$coloredOperator`")
                        .color(Attribute.RED_TEXT())
                        .build()
                        .label(binaryExpression.rightExpression.span, "Right expression has type `$coloredRightTypeLiteral`")
                        .color(Attribute.CYAN_TEXT())
                        .build()
                        .build()
                } else {
                    binaryExpression.originalType = promotedType
                    binaryExpression.finalType = promotedType
                }
            }
            else -> {}
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
        val coloredConstLiteral =
            if (compilationUnit.preference.enableColor) Ansi.colorize(field.constToString(), Attribute.CYAN_TEXT())
            else field.constToString()

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
        val coloredStaticFieldLiteral =
            if (compilationUnit.preference.enableColor) Ansi.colorize(
                field.staticFieldToString(),
                Attribute.CYAN_TEXT()
            )
            else field.staticFieldToString()

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
        val coloredPackageLiteral =
            if (compilationUnit.preference.enableColor) Ansi.colorize(
                currentPackagePath.toString(),
                Attribute.CYAN_TEXT()
            )
            else currentPackagePath.toString()
        val coloredClassLiteral =
            if (compilationUnit.preference.enableColor) Ansi.colorize(
                currentClassPath.toString(),
                Attribute.CYAN_TEXT()
            )
            else currentClassPath.toString()

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
            if (compilationUnit.preference.enableColor) Ansi.colorize(
                fieldInstance.staticFieldToString(),
                Attribute.CYAN_TEXT()
            )
            else fieldInstance.staticFieldToString()

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
        val coloredFnLiteral =
            if (compilationUnit.preference.enableColor) Ansi.colorize(fn.toString(), Attribute.CYAN_TEXT())
            else fn.toString()

        compilationUnit.reportBuilder
            .error(
                SpanHelper.expandView(span, compilationUnit.maxLineCount),
                "Function $coloredFnLiteral is already defined at `${fn.qualifiedOwnerPath}`"
            )
            .label(span, "Already defined")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    private fun bindType(type: Type): TypeInfo {
        val typeInfo = table.findType(type)

        return if (typeInfo == null) {
            // Unknown type
            val span = type.span
            val typeName = type.standardizeType()
            val colorizedTypeName =
                if (compilationUnit.preference.enableColor) Ansi.colorize(typeName, Attribute.RED_TEXT())
                else typeName

            compilationUnit.reportBuilder
                .error(SpanHelper.expandView(span, compilationUnit.maxLineCount), "Unknown type $colorizedTypeName")
                .label(span, "Unresolvable type here")
                .color(Attribute.RED_TEXT())
                .build().build()

            TypeInfo.Primitive.UNIT_TYPE_INFO
        } else typeInfo
    }
}