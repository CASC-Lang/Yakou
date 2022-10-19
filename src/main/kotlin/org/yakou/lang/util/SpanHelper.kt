package org.yakou.lang.util

import chaos.unity.nenggao.Position
import chaos.unity.nenggao.Span

object SpanHelper {
    /**
     * expand span's line to [Span.startPosition]#[Position.line] - 1 and Span's [Span.endPosition]#[Position.line] + 1
     * for diagnostic view usage. This function also set [Span.startPosition]#[Position.pos] and [Span.endPosition]#[Position.pos]
     * to 0.
     */
    fun expandView(span: Span, maxLineNumber: Int): Span {
        var startLine = span.startPosition.line
        var endLine = span.endPosition.line

        if (startLine > 1) {
            startLine--
        }

        if (endLine < maxLineNumber) {
            endLine++
        }

        return Span.multipleLine(startLine, span.startPosition.pos + 1, endLine, 0) // Character's actual start position starts from 1 instead of 0
    }
}
