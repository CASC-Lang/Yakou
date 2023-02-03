package org.yakou.lang.lexer

import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.compilation.UnitReporter

internal interface LeverReporter : UnitReporter {
    fun reportInvalidTypeAnnotation(typeAnnotation: String, typeAnnotationSpan: Span) {
        reporter().error(adjustSpan(typeAnnotationSpan), "Unknown type annotation $typeAnnotation")
            .label(typeAnnotationSpan, "Only `i8`, `i16`, `i32`, `i64`, `f32` and `f64` are allowed.")
            .color(Attribute.RED_TEXT())
            .build().build()
    }

    fun reportUnexpectedCharacter(char: String, span: Span) {
        val colorizedCharacter =
            if (preference().enableColor) {
                Ansi.colorize(char, Attribute.CYAN_TEXT())
            } else {
                char
            }

        reporter().error(adjustSpan(span), "Unknown character `$colorizedCharacter`")
            .label(span, "This character is unable to be lexical analyzed")
            .color(Attribute.RED_TEXT())
            .build().build()
    }
}
