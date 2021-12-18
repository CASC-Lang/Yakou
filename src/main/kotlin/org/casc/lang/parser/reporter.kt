package org.casc.lang.parser

import org.casc.lang.ast.Token
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report

internal fun MutableSet<Report>.reportExpectedFnDeclaration(token: Token?) =
    this.add(
        Error(
            token!!.pos,
            "Expected function declaration"
        )
    )

