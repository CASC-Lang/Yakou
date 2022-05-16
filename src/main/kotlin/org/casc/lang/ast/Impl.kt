package org.casc.lang.ast

import org.casc.lang.table.Reference

data class Impl(
    val implKeyword: Token,
    val parentClassReference: Reference?,
    var companionBlock: CompanionBlock?,
    var constructors: List<Constructor>,
    var functions: List<Function>
)
