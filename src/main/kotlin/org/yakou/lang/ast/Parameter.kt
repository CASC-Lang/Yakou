package org.yakou.lang.ast

import org.yakou.lang.bind.TypeInfo

open class Parameter(val name: Token, val colon: Token, val type: Type) {
    lateinit var typeInfo: TypeInfo
}