package org.casc.lang.ast

import org.casc.lang.table.Reference

data class Parameter(val mutable: Token?, val name: Token?, val colon: Token?, val type: Reference?)
