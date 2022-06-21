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
        bindExpression(const.expression)

        const.typeInfo = bindType(const.type)

        val field = ClassMember.Field.fromConst(
            currentPackagePath,
            currentClassPath,
            const,
            Opcodes.ACC_STATIC
        )

        if (!table.registerClassMember(field, topLevel)) {
            // Failed to register function
            reportConstAlreadyDefined(field, const)
        } else const.fieldInstance = field
    }

    private fun bindStaticFieldDeclaration(staticField: Item.StaticField) {
        bindExpression(staticField.expression)

        staticField.typeInfo = staticField.type?.let(::bindType) ?: staticField.expression.finalType

        val field = ClassMember.Field.fromField(
            currentPackagePath,
            currentClassPath,
            staticField,
            Opcodes.ACC_STATIC
        )

        if (!table.registerClassMember(field, topLevel)) {
            // Failed to register function
            reportConstAlreadyDefined(field, staticField)
        } else staticField.fieldInstance = field
    }

    private fun bindClassDeclaration(clazz: Item.Class) {

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

        if (!table.registerClassMember(fn, topLevel)) {
            // Failed to register function
            reportFunctionAlreadyDefined(fn, function)
        } else function.functionInstance = fn
    }

    private fun bindStatement(statement: Statement) {
        TODO()
    }

    private fun bindExpression(expression: Expression) {
        when (expression) {
            is Expression.NumberLiteral -> bindNumberLiteral(expression)
            Expression.Undefined -> TODO("UNREACHABLE")
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

    private fun reportConstAlreadyDefined(field: ClassMember.Field, staticField: Item.StaticField) {
        val span = staticField.span
        val coloredStaticFieldLiteral =
            if (compilationUnit.preference.enableColor) Ansi.colorize(field.staticFieldToString(), Attribute.CYAN_TEXT())
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