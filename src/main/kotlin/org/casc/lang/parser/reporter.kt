package org.casc.lang.parser

import org.casc.lang.ast.Token
import org.casc.lang.ast.TokenType
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report

internal fun MutableList<Report>.reportUnexpectedToken(expected: TokenType, got: Token) =
    this.add(
        Error(
            got.pos,
            "Unexpected token ${got.type}, expected token $expected"
        )
    )

internal fun MutableList<Report>.reportUnexpectedToken(got: Token) =
    this.add(
        Error(
            got.pos,
            "Unexpected token ${got.type}"
        )
    )