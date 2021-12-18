package org.casc.lang.checker

import org.casc.lang.ast.Parameter
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report
import org.casc.lang.table.Reference

internal fun MutableSet<Report>.reportUnknownTypeSymbol(reference: Reference) =
    this.add(
        Error(
            reference.position(),
            "Unknown type symbol $reference"
        )
    )

