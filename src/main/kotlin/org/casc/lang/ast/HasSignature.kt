package org.casc.lang.ast

import org.casc.lang.table.FunctionSignature

sealed interface HasSignature {
    fun asSignature(): FunctionSignature
}