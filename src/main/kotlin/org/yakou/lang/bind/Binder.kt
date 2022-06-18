package org.yakou.lang.bind

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.ast.Item
import org.yakou.lang.ast.Path
import org.yakou.lang.ast.Type
import org.yakou.lang.ast.YkFile
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.util.SpanHelper

class Binder(private val compilationUnit: CompilationUnit) {
    private val table = compilationUnit.session.table

    var currentPackagePath: Path.SimplePath = Path.SimplePath(listOf())
    var currentClassPath: Path.SimplePath = Path.SimplePath(listOf())

    fun bind() {
        bindYkFile(compilationUnit.ykFile!!)
    }

    private fun bindYkFile(ykFile: YkFile) {
        for (item in ykFile.items)
            bindItemDeclaration(item)
    }

    private fun bindItemDeclaration(item: Item) {
        when (item) {
            is Item.Function -> bindFunctionDeclaration(item)
            is Item.Package -> {
                val previousPackagePath = currentPackagePath
                currentPackagePath = currentPackagePath.append(item.identifier)

                if (item.items != null)
                    for (innerItem in item.items)
                        bindItemDeclaration(innerItem)

                currentPackagePath = previousPackagePath
            }
        }
    }

    private fun bindFunctionDeclaration(function: Item.Function) {
        for (parameter in function.parameters) {
            val typeInfo = findType(parameter.type)

            parameter.typeInfo = typeInfo
        }

        function.returnTypeInfo =
            if (function.returnType != null) findType(function.returnType)
            else TypeInfo.Primitive.UNIT_TYPE_INFO

        val fn = Fn.fromFunction(currentPackagePath, currentClassPath, function)

        if (!table.registerFunction(fn)) {
            // Failed to register function
            reportFunctionAlreadyDefined(fn, function)
        } else function.functionInstance = fn
    }

    private fun reportFunctionAlreadyDefined(fn: Fn, function: Item.Function) {
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

    private fun findType(type: Type): TypeInfo {
        val typeInfo = table.findType(type)

        return if (typeInfo == null) {
            // Unknown type
            val span = type.span
            val typeName = Table.standardizeType(type)
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