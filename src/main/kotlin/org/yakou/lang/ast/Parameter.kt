package org.yakou.lang.ast

import org.yakou.lang.bind.TypeInfo

data class Parameter(val name: Token, val colon: Token, val type: Type) {
    lateinit var typeInfo: TypeInfo
}