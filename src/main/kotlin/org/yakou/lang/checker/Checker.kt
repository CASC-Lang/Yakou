package org.yakou.lang.checker

import com.diogonunes.jcolor.Attribute
import org.yakou.lang.ast.Item
import org.yakou.lang.ast.Modifier
import org.yakou.lang.ast.YkFile
import org.yakou.lang.bind.TypeInfo
import org.yakou.lang.compilation.CompilationUnit
import org.yakou.lang.util.SpanHelper
import org.yakou.lang.util.colorize

class Checker(private val compilationUnit: CompilationUnit) {
    fun check() {
        checkYkFile(compilationUnit.ykFile!!)
    }

    fun checkYkFile(ykFile: YkFile) {
        for (item in ykFile.items)
            checkItem(item)
    }

    private fun checkItem(item: Item) {
        when (item) {
            is Item.Package -> {
                if (item.items != null)
                    for (innerItem in item.items)
                        checkItem(innerItem)
            }
            is Item.Const -> checkConst(item)
            is Item.Function -> checkFunction(item)
        }
    }

    private fun checkConst(const: Item.Const) {
        TODO()
    }

    private fun checkFunction(function: Item.Function) {
        // Check top-level function has illegal modifiers
        if (function.functionInstance.ownerTypeInfo is TypeInfo.PackageClass && function.modifiers.hasModifier(Modifier.Mut)) {
            val mutLiteral = colorize("mut", compilationUnit, Attribute.RED_TEXT())
            val span = function.modifiers[Modifier.Mut]!!

            compilationUnit.reportBuilder
                .error(SpanHelper.expandView(span, compilationUnit.maxLineCount), "Illegal modifier `$mutLiteral`")
                .label(span, "Top-level function cannot be mutable")
                .color(Attribute.RED_TEXT())
                .build().build()
        }
    }
}