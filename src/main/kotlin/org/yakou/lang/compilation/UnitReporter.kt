package org.yakou.lang.compilation

import chaos.unity.nenggao.FileReportBuilder
import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.api.AbstractPreference

interface UnitReporter {
    fun preference(): AbstractPreference
    fun reporter(): FileReportBuilder
    fun maxLineCount(): Int

    fun colorize(literal: String, vararg attributes: Attribute): String =
        if (preference().enableColor) {
            Ansi.colorize(literal, *attributes)
        } else {
            literal
        }

    fun adjustSpan(span: Span): Span {
        var startLine = span.startPosition.line
        var endLine = span.endPosition.line

        if (startLine > 1) {
            startLine--
        }

        if (endLine < maxLineCount()) {
            endLine++
        }

        return Span.multipleLine(startLine, span.startPosition.pos + 1, endLine, 0) // Character's actual start position starts from 1 instead of 0
    }
}
