package org.yakou.lang.ast

data class Parameter(val name: Token, val colon: Token, val typeReference: Type) {
}