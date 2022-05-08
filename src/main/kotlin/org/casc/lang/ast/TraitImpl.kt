package org.casc.lang.ast

data class TraitImpl(val implKeyword: Token, val functions: List<Function>)
