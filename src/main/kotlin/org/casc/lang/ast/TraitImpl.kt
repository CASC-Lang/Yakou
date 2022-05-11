package org.casc.lang.ast

import org.casc.lang.table.Reference

data class TraitImpl(val implKeyword: Token, val implementedTraitReference: Reference, val functions: List<Function>)
