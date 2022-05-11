package org.casc.lang.parser

import org.casc.lang.ast.Position
import org.casc.lang.ast.Token
import org.casc.lang.ast.TokenType
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report

internal fun MutableList<Report>.reportUnexpectedToken(expected: TokenType, got: Token, hint: String? = null) =
    this.add(
        Error(
            got.pos,
            "Unexpected token ${got.type}, expected token $expected",
            hint
        )
    )

internal fun MutableList<Report>.reportUnexpectedToken(expected: TokenType, pos: Position?, hint: String? = null) =
    this.add(
        Error(
            pos,
            "Expected token $expected, but got nothing",
            hint
        )
    )

internal fun MutableList<Report>.reportUnexpectedToken(got: Token, hint: String? = null) =
    this.add(
        Error(
            got.pos,
            "Unexpected token ${got.type}",
            hint
        )
    )