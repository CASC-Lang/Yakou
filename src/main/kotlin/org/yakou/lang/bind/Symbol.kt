package org.yakou.lang.bind

abstract class Symbol {
    abstract val static: Boolean
    abstract val mutable: Boolean
    abstract val typeInfo: TypeInfo
}
