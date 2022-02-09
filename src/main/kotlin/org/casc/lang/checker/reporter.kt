package org.casc.lang.checker

import org.casc.lang.ast.Position
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report
import org.casc.lang.table.Reference
import org.casc.lang.table.Type

internal fun MutableSet<Report>.reportUnknownTypeSymbol(reference: Reference) =
    add(
        Error(
            reference.position,
            "Unknown type symbol ${reference.asCascStyle()}"
        )
    )

internal fun MutableSet<Report>.reportTypeMismatch(pos: Position?, expected: Type?, found: Type?) =
    add(
        Error(
            pos,
            "Type mismatch.\n       Expected: ${expected?.typeName ?: "<Unknown>"}\n       Found: ${found?.typeName ?: "<Unknown>"}"
        )
    )
