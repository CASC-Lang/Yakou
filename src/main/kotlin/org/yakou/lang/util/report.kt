package org.yakou.lang.util

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.api.AbstractPreference
import org.yakou.lang.compilation.CompilationUnit

fun colorize(literal: String, compilationUnit: CompilationUnit, vararg attributes: Attribute): String =
    colorize(literal, compilationUnit.preference, *attributes)

fun colorize(literal: String, preference: AbstractPreference, vararg attributes: Attribute): String =
    if (preference.enableColor) {
        Ansi.colorize(literal, *attributes)
    } else {
        literal
    }
