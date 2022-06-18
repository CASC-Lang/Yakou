package org.yakou.lang.bind

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.ast.Item
import org.yakou.lang.ast.Type
import org.yakou.lang.ast.YkFile
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.util.SpanHelper

class Binder(private val compilationUnit: CompilationUnit) {
    private val table = compilationUnit.session.table

    fun bind() {
        bindYkFile(compilationUnit.ykFile!!)
    }

    private fun bindYkFile(ykFile: YkFile) {
        for (item in ykFile.items)
            bindItem(item)
    }

    private fun bindItem(item: Item) {
        when (item) {
            is Item.Function -> bindFunction(item)
            is Item.Package -> {
                if (item.items != null)
                    for (innerItem in item.items)
                        bindItem(innerItem)
            }
        }
    }

    private fun bindFunction(function: Item.Function) {
        for (parameter in function.parameters) {
            val typeInfo = findtype(parameter.type)

            parameter.typeInfo = typeInfo
        }

        function.returnTypeInfo =
            if (function.returnType != null) findtype(function.returnType)
            else TypeInfo.Primitive.UNIT_TYPE_INFO
    }

    private fun findtype(type: Type): TypeInfo {
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